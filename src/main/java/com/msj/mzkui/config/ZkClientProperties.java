package com.msj.mzkui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * zk连接参数配置
 */
@Configuration
@ConfigurationProperties(prefix=ZkClientProperties.PREFIX)
public class ZkClientProperties {

    public static final String PREFIX = "spring.zk";

    private String serverAddress;

    private Integer sessionTimeOut = 1000;

    private Integer connectionTimeOut = 1000;

    private Integer operationRetryTimeout = 4000;

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getSessionTimeOut() {
        return sessionTimeOut;
    }

    public void setSessionTimeOut(int sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }

    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public int getOperationRetryTimeout() {
        return operationRetryTimeout;
    }

    public void setOperationRetryTimeout(int operationRetryTimeout) {
        this.operationRetryTimeout = operationRetryTimeout;
    }
}
