package com.ctf.log4droid.mt_body;

import com.ctf.log4droid.protobuf.log4droid;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;

import java.io.Serializable;

/*
 * This file is auto generated by *proto_convert*
 *  and should *NOT* be modified manually!!!
 */
public class Codes  implements Serializable{
    //---------------------------------------------------------------------------
    // ALL fields (private)
    //---------------------------------------------------------------------------
    //required fields

    //optional fields
    private int code;
    private int value;
    private int type;
    private String description;

    //---------------------------------------------------------------------------
    // DEFAULT value for optional fields (private static final)
    //---------------------------------------------------------------------------
    private static final int DEFAULT_CODE = -1;
    private static final int DEFAULT_VALUE = -1;
    private static final int DEFAULT_TYPE = -1;
    private static final String DEFAULT_DESCRIPTION = null;

    //---------------------------------------------------------------------------
    // Constructed Function
    //---------------------------------------------------------------------------
    // <> is *required*
    public Codes() {
        this(DEFAULT_CODE, DEFAULT_VALUE, DEFAULT_TYPE, DEFAULT_DESCRIPTION);
    }

    public Codes(String description) {
        this(DEFAULT_CODE, DEFAULT_VALUE, DEFAULT_TYPE, description );
    }

    public Codes(int code, int value, int type, String description) {
        this.code = code;
        this.value = value;
        this.type = type;
        this.description = description;
    }

    //---------------------------------------------------------------------------
    // decode Constructed Function
    //---------------------------------------------------------------------------
    public static Codes parseFrom(byte[] bytes) throws InvalidProtocolBufferException {
        log4droid._codes codes = log4droid._codes.parseFrom(bytes);

        int code = codes.getCode();
        int value = codes.getValue();
        int type = codes.getType();
        String description = codes.getDescription();

        Codes _ret = new Codes(code,value,type,description);
        return _ret;
    }

    public Codes(log4droid._codes codes) {
        //decode with required fields
        this();
        //---------------------------
        //optional fields
        if (codes.hasCode()) {
            this.code = codes.getCode();
        }
        if (codes.hasValue()) {
            this.value = codes.getValue();
        }
        if (codes.hasType()) {
            this.type = codes.getType();
        }
        if (codes.hasDescription()) {
            this.description = codes.getDescription();
        }
    }

    //---------------------------------------------------------------------------
    // getter Functions
    //---------------------------------------------------------------------------
    //required fields

    //optional fields
    public boolean hasCode() {
        return (this.code != DEFAULT_CODE);
    }
    public int getCode() {
        return this.code;
    }
    public boolean hasValue() {
        return (this.value != DEFAULT_VALUE);
    }
    public int getValue() {
        return this.value;
    }
    public boolean hasType() {
        return (this.type != DEFAULT_TYPE);
    }
    public int getType() {
        return this.type;
    }
    public boolean hasDescription() {
        return (this.description != DEFAULT_DESCRIPTION);
    }
    public String getDescription() {
        return this.description;
    }

    //---------------------------------------------------------------------------
    // setter Functions
    //---------------------------------------------------------------------------
    public void setCode(int code){
        this.code = code;
    }
    public void setValue(int value){
        this.value = value;
    }
    public void setType(int type){
        this.type = type;
    }
    public void setDescription(String description){
        this.description = description;
    }
   //--------------------------------------
   // encode (Java obj => Proto builder)
   //--------------------------------------

    public int toBytes(ByteBuf buf) {
        log4droid._codes.Builder builder = log4droid._codes.newBuilder();

        //required fields

        //optional fields
        if (hasCode()) {
            builder.setCode(code);
        }
        if (hasValue()) {
            builder.setValue(value);
        }
        if (hasType()) {
            builder.setType(type);
        }
        if (hasDescription()) {
            builder.setDescription(description);
        }

        byte[] bytes = builder.build().toByteArray();
        buf.writeBytes(bytes);
        return bytes.length;
    }
    public log4droid._codes.Builder toBuilder() {
        log4droid._codes.Builder builder = log4droid._codes.newBuilder();

        //required fields

        //optional fields
        if (hasCode()) {
            builder.setCode(code);
        }
        if (hasValue()) {
            builder.setValue(value);
        }
        if (hasType()) {
            builder.setType(type);
        }
        if (hasDescription()) {
            builder.setDescription(description);
        }

        //return
        return builder;
    }
    //---------------------------------------------------------------------------
    // toString Function
    //---------------------------------------------------------------------------
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Codes { ");

        //required fields

        //optional fields
        if (hasCode()) {
            sb.append("code:").append(code).append(",");
        }
        if (hasValue()) {
            sb.append("value:").append(value).append(",");
        }
        if (hasType()) {
            sb.append("type:").append(type).append(",");
        }
        if (hasDescription()) {
            sb.append("description:").append(description).append(",");
        }

        //remove last ','
        sb.setLength(sb.length() - 1);

        sb.append(" }");

        return sb.toString();
    }
}
