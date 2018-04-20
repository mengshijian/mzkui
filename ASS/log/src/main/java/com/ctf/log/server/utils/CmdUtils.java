package com.ctf.log.server.utils;

import com.ctf.log.server.component.SpringContextHandler;
import com.ctf.log.server.netty.MTService;
import com.ctf.log4droid.mt_body.Server2Term;
import com.ctf.log4droid.mt_body.ServerRsp;
import com.ctf.log4droid.mt_body.Term2Server;
import com.ctf.log4droid.mt_body.TermReq;
import com.ctf.log.server.oss.CCM;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/****************** 通讯机制概述 ***************************
 * cmd_queue(本方主动发送的命令的缓存队列)
 * 通讯机制是一问一答。在上一命令没有被处理前，不应该再发送下一命令。
 * 所以需要建立命令队列，将需要发送的命令排队依次发送。
 *
 * waiting_req(对方主动发送的命令的缓存队列)
 * 服务器：在本方命令没有被处理前，对方上发的Req将缓存，暂时不予处理。待收到本方命令的回复后，再处理挂起的req并回复。
 * 客户端：在本方命令没有被处理前，如收到服务器下发的req，将立即处理并回复。
 *
 * resend & repeated
 * 因网络不稳定，本方发出的命令或对方发出的回复有可能丢失，为保证通讯成功，需要加入超时重发机制。
 * 超时重发由发起方负责，发起方发送命令后，在预定时间内未收到对应的回复，即视为命令超时，需要进行重发。
 * 重发多次后仍未收到回复，则视为通讯异常，发起方将关闭socket连接。
 *
 * 超时重发会导致接收方可能收到重复消息。故消息接收方要保证消息处理的*幂等性*！
 * 每个命令都有一个相对唯一的顺序号，对方的回复也使用该顺序号。
 * 通过顺序号对方可以判断收到的命令是新消息还是重发的命令，如为重发的命令则将直接返回上次处理的结果。
 */
public class CmdUtils implements Runnable {
    private static LogWrapper logger = LogWrapper.getLogger(CmdUtils.class.getName());

    private MTService mtService = SpringContextHandler.getBean(MTService.class);

    private boolean waitClose;             //为true时，待当前命令处理结束后应关闭ctx。该标志用于配合发送TermLogout实现强制退录

    private int cmd_seq_no;              //主动发送的cmd的序列号
    private int req_seq_no;              //被动接收的req的序列号

    private LinkedList<Cmd> cmd_queue;      //命令队列
    private TermReq waiting_req;            //等待的请求
    private Cmd currCmd;                    //当前命令
    private ServerRsp lastRsp;                //上次请求的回复

    private ScheduledFuture future;         //定时任务，用于超时重发
    private int curr_resend_times;        //当前命令重发次数
    private String userId;                //所属userId
    private byte[] sessionId;

    private ChannelHandlerContext currentCtx;      //保存当前链接
    private InetSocketAddress sender;               //客户端地址

    public byte[] getSessionId() {
        return sessionId;
    }

    public void setSessionId(byte[] sessionId) {
        this.sessionId = sessionId;
    }

    public InetSocketAddress getSender() {
        return sender;
    }

    public void setSender(InetSocketAddress sender) {
        this.sender = sender;
    }

    public static CmdUtils get(byte[] sessionId) {
        return CCM.getCmdBySid(sessionId);
    }

    public ChannelHandlerContext getCurrentCtx() {
        return currentCtx;
    }

    public void setCurrentCtx(ChannelHandlerContext currentCtx) {
        this.currentCtx = currentCtx;
        setSender(CCM.getSender(currentCtx));
    }

    //--------------------------------------------------------------------------------------------
    //私有构造函数
    public CmdUtils(String userId) {

        this.cmd_seq_no = 0;
        this.req_seq_no = -1;

        this.cmd_queue = new LinkedList<>();
        this.waiting_req = null;
        this.currCmd = null;
        this.lastRsp = null;

        this.future = null;
        this.curr_resend_times = 0;

        this.userId = userId;
    }


    //获取当前命令
    public Term2Server getCurrCmdMessage() {
        Term2Server term2Server = null;

        if (currCmd != null) {
            term2Server = currCmd.getMessage();
        }

        return term2Server;
    }

    //------------------------------------------------------------------

    public void send(Server2Term server2Term){
        currentCtx.writeAndFlush(server2Term);
    }

    //发送回复,被动回复
    public void sendRsp(Server2Term server2Term, ChannelHandlerContext ctx) {
        ChannelFuture f = ctx.writeAndFlush(server2Term);
        f.addListeners(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    //--------------------------------------------------------------------------------
    //对ctx.writeAndFlush的带Exception检测的封装
    private void writeAndFlush_FireExceptOnFail(Object obj) {
        ChannelFuture f = currentCtx.writeAndFlush(obj);
        f.addListeners(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);    //if any error occur in writeAndFlush, will fire exception can caught by 'exceptionCaught'
    }

    //发送当前命令
    private boolean _sendCmd() {

        if (currCmd != null && currentCtx != null) {
            future = currentCtx.executor().schedule(this, Integer.parseInt(CCM.getGlobalConfigMap().get("RESEND_TIMEOUT_SECONDS")), TimeUnit.SECONDS);
            writeAndFlush_FireExceptOnFail(currCmd.getMessage());
            return true;
        }

        logger.debug(sessionId, "[_sendCmd] No currCmd!");
        return false;
    }


    //发送命令(带超时重发机制)，如果当前有命令正在处理，将插入到后台命令之前
    public boolean sendCmd(Term2Server term2Server) {
        while (true) {
            if (currentCtx == null) {
                logger.warn(sessionId, "[sendCmd] channel not isOpen!");
                return false;
            }
            if (!currentCtx.channel().isOpen()) {
                clostCtx();
            } else {
                break;
            }
        }

        Cmd cmd = new Cmd(term2Server, false);
        if (future == null) {
            currCmd = cmd;
            curr_resend_times = 0;
            return _sendCmd();
        } else {
            int index;
            for (index = 0; index < cmd_queue.size(); index++) {
                if (cmd_queue.get(index).getIsBgCmd()) {
                    break;
                }
            }
            cmd_queue.add(index, cmd);//将这个命令加载到后台命令之前，
            logger.debug(sessionId, "[sendCmd] insert to cmd_queue!");
            return true;
        }
    }


    //取消重发Timer。 A)已收到回复  B)检测到致命错误(将消除消息队列)
    public void cancelSendingCmd(boolean bSucc) {
        logger.debug(sessionId, "[cancelSendingCmd] " + bSucc);
        try {
            if (future != null) {
                future.cancel(true);
                future = null;
                curr_resend_times = 0;
            }
        } catch (Exception e) {
            logger.debug("cancelSendingCmd Exception");
            e.printStackTrace();
        }

        if (!bSucc) {
            cmd_queue.clear();
        }
    }

    //超时，进行命令重发
    @Override
    public void run() {
        if (!currentCtx.channel().isOpen()) {
            logger.debug(sessionId, "ResendCmd failed. ctx closed!!!");
            return;
        }

        if (curr_resend_times < Integer.parseInt(CCM.getGlobalConfigMap().get("RESEND_MAX_TIMES"))) {
            curr_resend_times++;
            if (!_sendCmd()) {
                logger.debug(sessionId, "ResendCmd failed!!!");
            }
        } else {
            logger.warn(sessionId, "MaxResendTimes reached!!!");
            clostCtx();
            cancelSendingCmd(true);
            sendCmd(this.getCurrCmdMessage());
        }
    }


    //关闭删除currentCtx
    private void clostCtx() {
        String CtxIp = currentCtx.channel().remoteAddress().toString();
        logger.warn("clostCtx !!! ipAddr:" + CtxIp);
        if (currentCtx != null) {
            currentCtx.channel().close();
            currentCtx = null;
        }
    }

    private static final class Cmd {
        private Term2Server term2Server;                    //要发送的消息
        private boolean isBgCmd;                   //是否为后台命令

        Cmd(Term2Server term2Server, boolean isBgCmd) {
            this.term2Server = term2Server;
            this.isBgCmd = isBgCmd;
        }

        Term2Server getMessage() {
            return term2Server;
        }

        boolean getIsBgCmd() {
            return isBgCmd;
        }
    }

    //处理消息
    public void dealMsg(Term2Server term2Server) throws Exception{
        mtService.procMsg(term2Server);
    }

}
