package com.cootf.log4droid.common.uenum;

/**
 * 下发状态
 */
public enum SendState {

    INIT(0, "记录生成"),
    SENDTO(1, "命令已下发"),
    RECEIVED(2, "终端已接收命令"),
    SUCCESS(3, "执行成功"),
    PARAMETERERROR(4, "WEB参数错误"),
    FAILURE(-1, "执行失败");

    private Integer code;

    private String name;

    SendState(Integer code, String name) {
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
