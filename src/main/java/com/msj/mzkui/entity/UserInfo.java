package com.msj.mzkui.entity;

import com.msj.mzkui.base.entity.BaseEntity;

public class UserInfo extends BaseEntity {

    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户密码
     */
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
