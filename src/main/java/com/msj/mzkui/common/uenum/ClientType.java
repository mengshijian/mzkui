package com.msj.mzkui.common.uenum;

public enum ClientType {

    PC(0, "pc"),

    PAD(1, "平板"),

    SMART(2, "智能穿戴设备"),

    MOBILE(3, "移动设备");

    private Integer code;

    private String name;

    ClientType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
