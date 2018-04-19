package com.ctf.log4droid.mt_body;

import java.util.ArrayList;

import com.google.protobuf.ByteString;
import com.ctf.ass_public.utils.ConvUtils;
import com.ctf.log4droid.protobuf.log4droid;
import com.ctf.ass_codec.struct.MessageBody;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import com.ctf.log4droid.protobuf.log4droid.GRADE;
import com.ctf.log4droid.protobuf.log4droid.LEVEL;
import com.ctf.log4droid.protobuf.log4droid.TRANSPORT;
import com.ctf.log4droid.protobuf.log4droid.NETWORK_TYPE;
import com.ctf.log4droid.protobuf.log4droid.RSQ;

/*
 * This file is auto generated by *proto_convert*
 *  and should *NOT* be modified manually!!!
 */
public class TermRsp extends MessageBody {
    //---------------------------------------------------------------------------
    // ALL fields (private)
    //---------------------------------------------------------------------------
    //required fields

    //optional fields
    private int exec_exit;
    private String exec;
    private Codes extractLog;
    private Ap ap_fixinfo;
    private Modem modem_fixinfo;
    private Mcwill mcwill_fixinfo;
    private Wlan wlan_fixinfo;
    private Acc acc_fixinfo;
    private Gps gps_fixinfo;

    //---------------------------------------------------------------------------
    // DEFAULT value for optional fields (private static final)
    //---------------------------------------------------------------------------
    private static final int DEFAULT_EXEC_EXIT = -1;
    private static final String DEFAULT_EXEC = null;
    private static final Codes DEFAULT_EXTRACTLOG = null;
    private static final Ap DEFAULT_AP_FIXINFO = null;
    private static final Modem DEFAULT_MODEM_FIXINFO = null;
    private static final Mcwill DEFAULT_MCWILL_FIXINFO = null;
    private static final Wlan DEFAULT_WLAN_FIXINFO = null;
    private static final Acc DEFAULT_ACC_FIXINFO = null;
    private static final Gps DEFAULT_GPS_FIXINFO = null;

    //---------------------------------------------------------------------------
    // Constructed Function
    //---------------------------------------------------------------------------
    // <> is *required*
    public TermRsp() {
        this(DEFAULT_EXEC_EXIT, DEFAULT_EXEC, DEFAULT_EXTRACTLOG, DEFAULT_AP_FIXINFO, DEFAULT_MODEM_FIXINFO, DEFAULT_MCWILL_FIXINFO, DEFAULT_WLAN_FIXINFO, DEFAULT_ACC_FIXINFO, DEFAULT_GPS_FIXINFO);
    }

    public TermRsp(int exec_exit, String exec, Codes extractLog, Ap ap_fixinfo, Modem modem_fixinfo, Mcwill mcwill_fixinfo, Wlan wlan_fixinfo, Acc acc_fixinfo, Gps gps_fixinfo) {
        this.exec_exit = exec_exit;
        this.exec = exec;
        this.extractLog = extractLog;
        this.ap_fixinfo = ap_fixinfo;
        this.modem_fixinfo = modem_fixinfo;
        this.mcwill_fixinfo = mcwill_fixinfo;
        this.wlan_fixinfo = wlan_fixinfo;
        this.acc_fixinfo = acc_fixinfo;
        this.gps_fixinfo = gps_fixinfo;
    }
    public log4droid.TermResponse.Builder toBuilder() {
        log4droid.TermResponse.Builder builder = log4droid.TermResponse.newBuilder();

        if (hasExecExit()) {
            builder.setExecExit(this.exec_exit);
        }
        if (hasExec()) {
            builder.setExec(this.exec);
        }
        if (hasExtractLog()) {
            builder.setExtractLog(this.extractLog.toBuilder());
        }
        if (hasApFixinfo()) {
            builder.setApFixinfo(this.ap_fixinfo.toBuilder());
        }
        if (hasModemFixinfo()) {
            builder.setModemFixinfo(this.modem_fixinfo.toBuilder());
        }
        if (hasMcwillFixinfo()) {
            builder.setMcwillFixinfo(this.mcwill_fixinfo.toBuilder());
        }
        if (hasWlanFixinfo()) {
            builder.setWlanFixinfo(this.wlan_fixinfo.toBuilder());
        }
        if (hasAccFixinfo()) {
            builder.setAccFixinfo(this.acc_fixinfo.toBuilder());
        }
        if (hasGpsFixinfo()) {
            builder.setGpsFixinfo(this.gps_fixinfo.toBuilder());
        }
        return builder;

    }
    public TermRsp(log4droid.TermResponse termResponse){
        this.exec_exit = termResponse.getExecExit();
        this.exec = termResponse.getExec();
        this.extractLog = new Codes(termResponse.getExtractLog());
        this.ap_fixinfo = new Ap(termResponse.getApFixinfo());
        this.modem_fixinfo = new Modem(termResponse.getModemFixinfo());
        this.mcwill_fixinfo = new Mcwill(termResponse.getMcwillFixinfo());
        this.wlan_fixinfo = new Wlan(termResponse.getWlanFixinfo());
        this.acc_fixinfo = new Acc(termResponse.getAccFixinfo());
        this.gps_fixinfo = new Gps(termResponse.getGpsFixinfo());
    }
    //---------------------------------------------------------------------------
    // getter Functions
    //---------------------------------------------------------------------------
    //required fields

    //optional fields
    public boolean hasExecExit() {
        return (this.exec_exit != DEFAULT_EXEC_EXIT);
    }

    public int getExecExit() {
        return this.exec_exit;
    }

    public void setExecExit(int exec_exit) {
        this.exec_exit = exec_exit;
    }

    public boolean hasExec() {
        return (this.exec != DEFAULT_EXEC);
    }

    public String getExec() {
        return this.exec;
    }

    public void setExec(String exec) {
        this.exec = exec;
    }

    public boolean hasExtractLog() {
        return (this.extractLog != DEFAULT_EXTRACTLOG);
    }

    public Codes getExtractLog() {
        return this.extractLog;
    }

    public void setExtractLog(Codes extractLog) {
        this.extractLog = extractLog;
    }

    public boolean hasApFixinfo() {
        return (this.ap_fixinfo != DEFAULT_AP_FIXINFO);
    }

    public Ap getApFixinfo() {
        return this.ap_fixinfo;
    }

    public void setApFixinfo(Ap ap_fixinfo) {
        this.ap_fixinfo = ap_fixinfo;
    }

    public boolean hasModemFixinfo() {
        return (this.modem_fixinfo != DEFAULT_MODEM_FIXINFO);
    }

    public Modem getModemFixinfo() {
        return this.modem_fixinfo;
    }

    public void setModemFixinfo(Modem modem_fixinfo) {
        this.modem_fixinfo = modem_fixinfo;
    }

    public boolean hasMcwillFixinfo() {
        return (this.mcwill_fixinfo != DEFAULT_MCWILL_FIXINFO);
    }

    public Mcwill getMcwillFixinfo() {
        return this.mcwill_fixinfo;
    }

    public void setMcwillFixinfo(Mcwill mcwill_fixinfo) {
        this.mcwill_fixinfo = mcwill_fixinfo;
    }

    public boolean hasWlanFixinfo() {
        return (this.wlan_fixinfo != DEFAULT_WLAN_FIXINFO);
    }

    public Wlan getWlanFixinfo() {
        return this.wlan_fixinfo;
    }

    public void setWlanFixinfo(Wlan wlan_fixinfo) {
        this.wlan_fixinfo = wlan_fixinfo;
    }

    public boolean hasAccFixinfo() {
        return (this.acc_fixinfo != DEFAULT_ACC_FIXINFO);
    }

    public Acc getAccFixinfo() {
        return this.acc_fixinfo;
    }

    public void setAccFixinfo(Acc acc_fixinfo) {
        this.acc_fixinfo = acc_fixinfo;
    }

    public boolean hasGpsFixinfo() {
        return (this.gps_fixinfo != DEFAULT_GPS_FIXINFO);
    }

    public Gps getGpsFixinfo() {
        return this.gps_fixinfo;
    }

    public void setGpsFixinfo(Gps gps_fixinfo) {
        this.gps_fixinfo = gps_fixinfo;
    }

    //---------------------------------------------------------------------------
    // decode Function
    //---------------------------------------------------------------------------

    public static TermRsp parseFrom(byte[] bytes) throws InvalidProtocolBufferException {
        log4droid.TermResponse _response = log4droid.TermResponse.parseFrom(bytes);

        int exec_exit = _response.getExecExit();
        String exec = _response.getExec();
        Codes extractLog = new Codes(_response.getExtractLog());
        Ap ap_fixinfo = new Ap(_response.getApFixinfo());
        Modem modem_fixinfo = new Modem(_response.getModemFixinfo());
        Mcwill mcwill_fixinfo = new Mcwill(_response.getMcwillFixinfo());
        Wlan wlan_fixinfo = new Wlan(_response.getWlanFixinfo());
        Acc acc_fixinfo = new Acc(_response.getAccFixinfo());
        Gps gps_fixinfo = new Gps(_response.getGpsFixinfo());

        TermRsp _ret = new TermRsp(exec_exit,exec,extractLog,ap_fixinfo,modem_fixinfo,mcwill_fixinfo,wlan_fixinfo,acc_fixinfo,gps_fixinfo);
        return _ret;
    }


    //---------------------------------------------------------------------------
    // encode Function
    //---------------------------------------------------------------------------
    @Override
    public int toBytes(ByteBuf buf) {
        log4droid.TermResponse.Builder builder = log4droid.TermResponse.newBuilder();

        //required fields

        //optional fields
        if (hasExecExit()) {
            builder.setExecExit(exec_exit);
        }
        if (hasExec()) {
            builder.setExec(exec);
        }
        if (hasExtractLog()) {
            builder.setExtractLog(extractLog.toBuilder());
        }
        if (hasApFixinfo()) {
            builder.setApFixinfo(ap_fixinfo.toBuilder());
        }
        if (hasModemFixinfo()) {
            builder.setModemFixinfo(modem_fixinfo.toBuilder());
        }
        if (hasMcwillFixinfo()) {
            builder.setMcwillFixinfo(mcwill_fixinfo.toBuilder());
        }
        if (hasWlanFixinfo()) {
            builder.setWlanFixinfo(wlan_fixinfo.toBuilder());
        }
        if (hasAccFixinfo()) {
            builder.setAccFixinfo(acc_fixinfo.toBuilder());
        }
        if (hasGpsFixinfo()) {
            builder.setGpsFixinfo(gps_fixinfo.toBuilder());
        }

        //encode
        byte[] bytes = builder.build().toByteArray();
        buf.writeBytes(bytes);

        //return
        return bytes.length;
    }

    //---------------------------------------------------------------------------
    // toString Function
    //---------------------------------------------------------------------------
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("TermRsp { ");

        //required fields

        //optional fields
        if (hasExecExit()) {
            sb.append("exec_exit:").append(exec_exit).append(",");
        }
        if (hasExec()) {
            sb.append("exec:").append(exec).append(",");
        }
        if (hasExtractLog()) {
            sb.append("extractLog:").append(extractLog).append(",");
        }
        if (hasApFixinfo()) {
            sb.append("ap_fixinfo:").append(ap_fixinfo).append(",");
        }
        if (hasModemFixinfo()) {
            sb.append("modem_fixinfo:").append(modem_fixinfo).append(",");
        }
        if (hasMcwillFixinfo()) {
            sb.append("mcwill_fixinfo:").append(mcwill_fixinfo).append(",");
        }
        if (hasWlanFixinfo()) {
            sb.append("wlan_fixinfo:").append(wlan_fixinfo).append(",");
        }
        if (hasAccFixinfo()) {
            sb.append("acc_fixinfo:").append(acc_fixinfo).append(",");
        }
        if (hasGpsFixinfo()) {
            sb.append("gps_fixinfo:").append(gps_fixinfo).append(",");
        }

        //remove last ','
        sb.setLength(sb.length() - 1);

        sb.append(" }");

        return sb.toString();
    }
}

