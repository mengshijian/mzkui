package com.cootf.log4droid.common.uenum;

public enum LogLevel {

    FATAL(0, "FATAL"),
    ALERT(1, "ALERT"),
    CRITICAL(2, "CRITICAL"),
    ERROR(3, "ERROR"),
    WARNING(4, "WARNING"),
    NOTICE(5, "NOTICE"),
    INFO(6, "INFO"),
    DEBUG(7, "DEBUG");

    private Integer code;

    private String name;

    LogLevel(Integer code, String name) {
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
