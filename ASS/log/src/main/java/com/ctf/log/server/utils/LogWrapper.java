package com.ctf.log.server.utils;

import com.ctf.log.server.oss.CCM;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;

public class LogWrapper {
    private Logger logger = null;

    private LogWrapper(String className) {
        this.logger = LogManager.getLogger(className);
    }

    private LogWrapper(Class<?> clazz) {
        this.logger = LogManager.getLogger(clazz);
    }

    public static LogWrapper getLogger(String className) {
        return new LogWrapper(className);
    }

    public static LogWrapper getLogger(Class<?> clazz) {
        return new LogWrapper(clazz);
    }

    // ==============  Server ==========================

    public void trace(String s) {
        logger.trace(s);
    }

    public void info(String s) {
        logger.info(s);
    }

    public void debug(String s) {
        logger.debug(s);
    }

    public void warn(String s) {
        logger.warn(s);
    }

    public void error(String s,Throwable e) {
        logger.error(s,e);
    }
    public void error(byte[] sessionId,String s,Throwable e) {
        String str = getClientInfo(sessionId,null) + s;
        logger.error(str,e);
    }


    public void error(String s) {
        logger.error(s);
    }

    public void fatal(String s) {
        logger.fatal(s);
    }

    // ===============  client =========================
    private String getClientInfo(byte[] sessionId, InetSocketAddress sender) {
        String clientInfo = "???";

        if (null!=sessionId && sessionId.length>0) {
            clientInfo = CCM.getUserId(sessionId);
        }
        InetSocketAddress clientIp = sender!=null ? sender:CCM.getCmdBySid(sessionId).getSender();


        return "[" + clientInfo + "@" + clientIp + "]";
    }

    public void trace(byte[] sessionId, String s) {
        logger.trace(getClientInfo(sessionId, null) + s);
    }

    public void info(byte[] sessionId, String s) {
        logger.info(getClientInfo(sessionId, null) + s);
    }

    public void debug(byte[] sessionId, String s) {
        logger.debug(getClientInfo(sessionId, null) + s);
    }

    public void warn(byte[] sessionId, String s, Throwable throwable) {
        logger.debug(getClientInfo(sessionId, null), s, throwable);
    }

    public void warn(byte[] sessionId, String s) {
        logger.warn(getClientInfo(sessionId, null) + s);
    }

    public void error(byte[] sessionId, String s) {
        logger.error(getClientInfo(sessionId, null) + s);
    }

    public void fatal(byte[] sessionId, String s) {
        logger.fatal(getClientInfo(sessionId, null) + s);
    }









    public void trace(InetSocketAddress sender, String s) {
        logger.trace(getClientInfo(null,sender) + s);
    }

    public void info(InetSocketAddress sender, String s) {
        logger.info(getClientInfo(null,sender) + s);
    }

    public void debug(InetSocketAddress sender, String s) {
        logger.debug(getClientInfo(null,sender) + s);
    }

    public void warn(InetSocketAddress sender, String s, Throwable throwable) {
        logger.debug(getClientInfo(null,sender), s, throwable);
    }

    public void warn(InetSocketAddress sender, String s) {
        logger.warn(getClientInfo(null,sender) + s);
    }

    public void error(InetSocketAddress sender, String s) {
        logger.error(getClientInfo(null,sender) + s);
    }

    public void fatal(InetSocketAddress sender, String s) {
        logger.fatal(getClientInfo(null,sender) + s);
    }

    public void debug(byte[] sessionId,InetSocketAddress sender, String s) {
        logger.debug(getClientInfo(sessionId,sender) + s);
    }
}
