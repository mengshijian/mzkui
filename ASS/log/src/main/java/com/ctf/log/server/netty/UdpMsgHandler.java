package com.ctf.log.server.netty;

import com.ctf.ass_public.utils.ConvUtils;
import com.ctf.log.server.component.MongoDbUtil;
import com.ctf.log.server.pojo.OnLineUser;
import com.ctf.log.server.pojo.User;
import com.ctf.log.server.utils.CmdUtils;
import com.ctf.log.server.utils.LogWrapper;
import com.ctf.log.server.utils.Util;
import com.ctf.log4droid.mt_body.*;
import com.ctf.log.server.oss.CCM;
import com.ctf.log.server.oss.CRMServer;
import com.ctf.log.server.oss.ErrCode;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("udpMsgHandler")
@Scope("prototype")
@ChannelHandler.Sharable
public class UdpMsgHandler extends SimpleChannelInboundHandler<Term2Server> {

    private static LogWrapper logger = LogWrapper.getLogger(UdpMsgHandler.class.getName());
    @Autowired
    private MongoDbUtil mongoDbUtil;
    @Autowired
    private CRMServer crmServer;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        logger.debug("Got Mt connection");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Term2Server term2Server) {
        if (null == term2Server) {
            procMtBadMsg(ctx, term2Server);
            return;
        }
        byte[] sid = term2Server.getSid();
        try {
            //登录
            if (sid.length == 0) {

                procMtLoginReq(ctx, term2Server);

            } else if (null != mongoDbUtil.getOnlineUserBySid(sid)) {
                CmdUtils cmdUtils = CCM.getCmdBySid(sid);
                if (null == cmdUtils) {
                    procMtBadMsg(ctx, term2Server);
                } else {
                    cmdUtils.setCurrentCtx(ctx);
                    cmdUtils.dealMsg(term2Server);
                }
            } else {
            }

            //构造发送数据

        } catch (Exception e) {
            logger.error(sid, "ERROR!!!!!!!!!!!!!!!!");
        } finally {
            CCM.delSender(ctx);
        }

    }

    /**
     * 登录
     *
     * @param ctx
     * @param term2Server
     */
    private void procMtLoginReq(ChannelHandlerContext ctx, Term2Server term2Server) {
        TermReq termReq = term2Server.getTermRequest();
        Login data = termReq.getLogin();
        //判断用户登录信息

        User login = mongoDbUtil.getUser(data.getUsername());
        ErrCode.ServerCode errCode = checkUser(data, login);
        //现在都是登录成功
        errCode = ErrCode.ServerCode.ERR_OK;


        String userName = data.getUsername();
        byte[] sessionId = Util.getSessionId();
        //web端
        OnLineUser onLineUser = new OnLineUser(sessionId, userName, data.getCodesVer(), data.getProtoVer());
        mongoDbUtil.insertOnlineUser(onLineUser);

        CmdUtils cmdUtils = new CmdUtils(userName);
        cmdUtils.setCurrentCtx(ctx);
        cmdUtils.setSessionId(sessionId);
        CCM.addCmd(userName, cmdUtils);
        CCM.addUserId(sessionId, userName);


        //回复
        Server2Term server2Term = new Server2Term();
        server2Term.setSid(sessionId);
        //登录回复
        buildLoginRsp(errCode, sessionId, server2Term);
        //配置处理
        crmServer.dealMsg(termReq, sessionId, server2Term);
        CCM.getCmdBySid(sessionId).send(server2Term);

    }


    /**
     * @param data
     * @param login
     * @return
     */
    private ErrCode.ServerCode checkUser(Login data, User login) {
        if (null != login)
            return ErrCode.ServerCode.USER_NOT_EXIST;
        if (!ErrCode.PROTO_MD5.equals(ConvUtils.bytesToHexStr(data.getProtoVer())))
            return ErrCode.ServerCode.ERR_PROTO_NOT_COMPATIABLE;
        if (!ErrCode.CODES_MD5.equals(ConvUtils.bytesToHexStr(data.getCodesVer())))
            return ErrCode.ServerCode.ERR_CODE_NOT_COMPATIABLE;
        if (!login.getPsdMd5().equals(ConvUtils.bytesToHexStr(data.getPassword())))
            return ErrCode.ServerCode.USER_WRONG_PASSWORD;
        return ErrCode.ServerCode.ERR_OK;

    }

    /**
     * 登录响应
     *
     * @param errCode
     * @param sessionId
     * @return
     */
    private Server2Term buildLoginRsp(ErrCode.ServerCode errCode, byte[] sessionId, Server2Term server2Term) {
        Codes login = new Codes(errCode.value(), 0, 0, errCode.cnErrMsg());
        ServerRsp serverRsp = new ServerRsp();
        serverRsp.setLogin(login);
        server2Term.setServerResponse(serverRsp);
        return server2Term;
    }

    //处理终端 错误消息
    private void procMtBadMsg(ChannelHandlerContext ctx, Term2Server term2Server) {
        logger.error(term2Server.getSid(), "UdpMsgHandler.channelRead0=> Mt bad msg found: " + term2Server);
    }


}