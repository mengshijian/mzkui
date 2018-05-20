package com.msj.mzkui.controller.vo;

import java.io.Serializable;

public class ResResult<T> implements Serializable {

    private static final long serialVersionUID = 1170501474786892441L;
    /**
     * 状态码
     */
    private String code;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 描述信息
     */
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResResult() {
    }

    public ResResult(String code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
}
