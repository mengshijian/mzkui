package com.ctf.log.server.netty;


import com.ctf.log.server.component.MongoDbUtil;
import com.ctf.log.server.pojo.OnLineUser;
import com.ctf.log.server.utils.LogWrapper;
import com.ctf.log4droid.mt_body.*;
import com.ctf.log.server.oss.CCM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("mtService")
public class MTService {
    private static LogWrapper logger = LogWrapper.getLogger(MTService.class.getName());

    @Autowired
    MongoDbUtil mongoDbUtil;

    public void procMsg( Term2Server term2Server){
        if (null == term2Server) {
            procMtBadMsg(term2Server);
            return;
        }
        byte[] sessionId = term2Server.getSid();
        int pkgUid = term2Server.getPkgUid();
        try {


            //获取终端响应
            TermRsp termRsp = term2Server.getTermResponse();
            //获取终端请求
            TermReq termReq = term2Server.getTermRequest();

            //server回复
            ServerRsp serverRsp = new ServerRsp();
            //server请求
            ServerReq serverReq = new ServerReq();



            //处理终端回复的消息
            if (null != termRsp) {
                logger.debug(term2Server.getSid(), "deal with termRsp!!!");
                //-------------------------------------
                //method
            }
            //处理终端请求
            if (null != termReq) {
                logger.debug(sessionId, "deal with MT termReq!!!");
                if (null != termReq.getCause()) {
                    logger.debug(sessionId, "deal with MT cause!!!");
                }
                if (null != termReq.getAlert()) {        //告警
                    logger.debug(sessionId, "deal with MT alert!!!");
                }
                if (null != termReq.getUploadLog()) {    //上传日志
                    logger.debug(sessionId, "deal with MT uploadLog!!!");
                }
            }
            //server请求
            //从任务列表中获取请求onlineUser
            buildServerReq(sessionId, serverReq);

            Server2Term server2Term = new Server2Term(pkgUid, 0, getUTC(), null, serverRsp, serverReq, null, null, sessionId);
            CCM.getCmdBySid(sessionId).send(server2Term);
        } catch (Throwable e) {
            logger.error(sessionId,"MT procMsg exception:", e);
            throw e;
        }
    }

    private ServerReq buildServerReq(byte[] sessionId, ServerReq serverReq) {
        OnLineUser onLineUser = mongoDbUtil.getOnlineUserBySid(sessionId);
        //获取请求命令：onLineUser.getCmd();
        //模拟-----------------------------------------
        String path = CCM.getUserId(sessionId) + System.currentTimeMillis() + ".log";
        ExtractLog extractLog = getExtractLog(30, "5", 2048, path, "http");
        //-----------------------------------------
        serverReq.setExtractLog(extractLog);
        return serverReq;

    }

    private ExtractLog getExtractLog(int minute, String level, int sizeLimet, String path, String method) {
        ExtractLog ret = new ExtractLog((int) (System.currentTimeMillis() - 1000 * 60 * minute) / 1000, (int) System.currentTimeMillis() / 1000, level, sizeLimet, path, method);
        return ret;
    }

    private int getUTC() {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        int utc_second = -(zoneOffset + dstOffset);

        return utc_second;
    }

    //----------------------------------------------------------------------------
    //处理终端 错误消息
    //-----------------
    private void procMtBadMsg(Term2Server term2Server) {
        logger.error(term2Server.getSid(), "Mt bad msg found:" + term2Server);
    }
}
