package com.cootf.log4droid.entity;

import com.cootf.log4droid.base.entity.BaseEntity;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class OnLineUser extends BaseEntity {

    private static final long serialVersionUID = -390314176640985841L;
    /**
     * 终端类型
     */
    private Integer clientType;

    /**
     * 型号
     */
    private String model;

    /**
     * 版本
     */
    private String version;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 在线用户mac地址
     */
    private String macAddress;

    /**
     * IMEI编号
     */
    private String imei;

    /**
     * sessionId
     */
    private String sessionId;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
