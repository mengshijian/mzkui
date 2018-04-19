package com.ctf.log.server.pojo;

import com.ctf.ass_public.utils.ConvUtils;

public class OnLineUser {
    private byte[] sessionId;
    private String userName;
    private byte[] proto_ver;
    private byte[] codes_ver;

    private Object cmd;

    public Object getCmd() {
        return cmd;
    }

    public void setCmd(Object cmd) {
        this.cmd = cmd;
    }

    public byte[] getSessionId() {
        return sessionId;
    }

    public void setSessionId(byte[] sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public OnLineUser(byte[] sessionId, String userName, byte[] proto_ver, byte[] codes_ver) {
        this.sessionId = sessionId;
        this.userName = userName;
        this.proto_ver = proto_ver;
        this.codes_ver = codes_ver;
    }

    @Override
    public String toString() {
        return "OnLineUser{" +
                "sessionId=" + ConvUtils.bytesToHexStr(sessionId) +
                ", userName='" + userName + '\'' +
                ", proto_ver=" + ConvUtils.bytesToHexStr(proto_ver) +
                ", codes_ver=" + ConvUtils.bytesToHexStr(codes_ver) +
                ", cmd=" + cmd +
                '}';
    }
}
