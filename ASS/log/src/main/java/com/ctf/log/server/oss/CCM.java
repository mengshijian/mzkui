package com.ctf.log.server.oss;

import com.ctf.ass_public.utils.ConvUtils;
import com.ctf.log.server.pojo.OnLineUser;
import com.ctf.log.server.utils.CmdUtils;
import com.ctf.log.server.utils.LogWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import org.apache.commons.lang.StringUtils;
import org.jboss.netty.util.internal.ConcurrentHashMap;

import java.net.InetSocketAddress;
import java.util.HashMap;

/**
 * Client Connection Manager
 */
public class CCM {

    public static HashMap<byte[], OnLineUser> onLineUserHashMap = new HashMap<>();




    private static LogWrapper logger = LogWrapper.getLogger(CCM.class.getName());

    private static ConcurrentHashMap<byte[], String> mtIds;             //(sessionid,userId)
    private static ConcurrentHashMap<String, CmdUtils> userIdCmdUtils;  //(userId,cmdUtils)
    private static HashMap<String,String> globalConfigMap;
    private static HashMap<ChannelId,InetSocketAddress> senderMap;

    public static HashMap<String, InetSocketAddress> testMap;

    static {
        testMap = new HashMap<>();
        senderMap = new HashMap<>();
        mtIds = new ConcurrentHashMap<>();
        userIdCmdUtils = new ConcurrentHashMap<>();
        globalConfigMap = new HashMap<>();

        globalConfigMap.put("MT_IDLE_TIMEOUT_SECONDS","20");
        globalConfigMap.put("RESEND_TIMEOUT_SECONDS","20");
        globalConfigMap.put("RESEND_MAX_TIMES","20");

    }

    public static void putSender(ChannelHandlerContext ctx, InetSocketAddress sender){
        ChannelId channelId = ctx.channel().id();
        senderMap.put(channelId,sender);
    }
    public static InetSocketAddress getSender(ChannelHandlerContext ctx){
        ChannelId channelId = ctx.channel().id();
        return senderMap.get(channelId);
    }

    public static void delSender(ChannelHandlerContext ctx){
        ChannelId channelId = ctx.channel().id();
        if (senderMap.containsKey(channelId))
            senderMap.remove(channelId);
    }


    private static boolean isExistSid(byte[] sessionId) {
        if (null != sessionId && sessionId.length>0) {
            return true;
        }
        logger.debug("The sessionId: " + ConvUtils.bytesToStr(sessionId) + "|||||" + ConvUtils.bytesToHexStr(sessionId) + " is not exist");
        return false;
    }

    private static boolean isExistUserId(String userId) {
        if (StringUtils.isNotEmpty(userId)) {
            return true;
        }
        logger.debug("The user: " + userId + " is not exist");
        return false;
    }

    public static void addUserId(byte[] sessionId, String id) {
        mtIds.put(sessionId, id);
    }

    public static void addCmd(String userId, CmdUtils cmdUtils) {
        userIdCmdUtils.put(userId, cmdUtils);
    }

    public static String getUserId(byte[] sessionId) {
        if (isExistSid(sessionId))
            return mtIds.get(sessionId);
        return null;
    }

    public static CmdUtils getCmdByUserId(String userId) {
        if (isExistUserId(userId))
            return userIdCmdUtils.get(userId);
        return null;
    }

    public static CmdUtils getCmdBySid(byte[] sessionId) {
        if (isExistSid(sessionId)) {
            String userId = mtIds.get(sessionId);
            return getCmdByUserId(userId);
        }
        return null;
    }

    public static void delCmdBySid(byte[] sessionId) {

        if (isExistSid(sessionId)) {
            String userId = mtIds.get(sessionId);
            if (isExistUserId(userId)) {
                //先删除该用户cmdUtils
                userIdCmdUtils.remove(userId);
                mtIds.remove(sessionId);
                logger.debug("The User: " + userId + " is removed !!!!!");
            }
        }
    }

    public static void delCmdByUserId(String userId) {
        if (isExistUserId(userId)) {
            //先删除该用户cmdUtils
            userIdCmdUtils.remove(userId);
            logger.debug("The User: " + userId + " is removed !!!!!");
        }
    }
    public static HashMap<String, String> getGlobalConfigMap() {
        return globalConfigMap;
    }

    public static void setGlobalConfigMap(HashMap<String, String> globalConfigMap) {
        CCM.globalConfigMap = globalConfigMap;
    }


}
