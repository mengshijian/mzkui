package com.ctf.log.server.utils;

import com.ctf.ass_public.utils.ConvUtils;

import java.util.UUID;

public class Util {
    /**
     * 生成sessionId
     * @return
     */
    public static byte[] getSessionId(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        byte[] sessionId = ConvUtils.strToBytes(uuid);
        return sessionId;
    }
}
