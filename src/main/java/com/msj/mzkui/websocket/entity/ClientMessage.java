package com.msj.mzkui.websocket.entity;

/**
 * 浏览器向服务端发送的消息
 */
public class ClientMessage {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
