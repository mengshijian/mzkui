package com.ctf.log.server.pojo;

import com.ctf.ass_public.utils.ConvUtils;

public class User {

    private String username;
    private String psdMd5;
    private byte[] proto_ver;
    private byte[] codes_ver;
    private byte[] sessionId;

    public User(String username, String psdMd5) {
        this.username = username;
        this.psdMd5 = psdMd5;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPsdMd5() {
        return psdMd5;
    }

    public void setPassword(String psdMd5) {
        this.psdMd5 = psdMd5;
    }

    public byte[] getProto_ver() {
        return proto_ver;
    }

    public void setProto_ver(byte[] proto_ver) {
        this.proto_ver = proto_ver;
    }

    public byte[] getCodes_ver() {
        return codes_ver;
    }

    public void setCodes_ver(byte[] codes_ver) {
        this.codes_ver = codes_ver;
    }

    public byte[] getSessionId() {
        return sessionId;
    }

    public void setSessionId(byte[] sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username +
                ", psdMd5='" + psdMd5 +
                ", proto_ver=" + ConvUtils.bytesToHexStr(proto_ver) +
                ", codes_ver=" + ConvUtils.bytesToHexStr(codes_ver) +
                ", sessionId=" + ConvUtils.bytesToHexStr(sessionId) +
                '}';
    }
}
