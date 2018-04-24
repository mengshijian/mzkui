package com.cootf.log4droid.entity;

import com.cootf.log4droid.base.entity.BaseEntity;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 提取日志命令实体
 */
@Document(collection = "extractLog")
public class ExtractLog extends BaseEntity {


    private static final long serialVersionUID = 2501795693279216172L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 提取人名称
     */
    private String extUserName;

    /**
     * 每日提取日志上线
     */
    private long limit;

    /**
     * 提起日志起始时间
     */
    private Date fromTime;

    /**
     * 提起日志结束时间
     */
    private Date toTime;

    /**
     * 日志级别
     */
    private Integer logLevel;

    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * TODO 上传方式 有待确定
     */
    private String uploadMethod;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExtUserName() {
        return extUserName;
    }

    public void setExtUserName(String extUserName) {
        this.extUserName = extUserName;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public Integer getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Integer logLevel) {
        this.logLevel = logLevel;
    }

    public String getUploadMethod() {
        return uploadMethod;
    }

    public void setUploadMethod(String uploadMethod) {
        this.uploadMethod = uploadMethod;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
