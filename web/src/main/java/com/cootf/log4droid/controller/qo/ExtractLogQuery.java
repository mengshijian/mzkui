package com.cootf.log4droid.controller.qo;

import com.cootf.log4droid.base.query.BaseQuery;
import java.util.Date;

public class ExtractLogQuery extends BaseQuery {

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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
