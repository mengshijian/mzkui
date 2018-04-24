package com.cootf.log4droid.common.uenum;

public enum LoginStatus {
    NO(0, "未登录"),

    YES(1, "已登录");

    private Integer code;

    private String name;

    LoginStatus(Integer code, String name) {
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
