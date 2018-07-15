package com.msj.mzkui.common.utils;

import com.msj.mzkui.config.ZkClientProperties;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class CuratorUtils {

    private ZkClientProperties properties;

    //创建连接实例
    private CuratorFramework client = null;

    /**
     * baseSleepTimeMs：初始的重试等待时间
     * maxRetries：最多重试次数
     */
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

    public void init(){
        //创建 CuratorFrameworkImpl实例
        client = CuratorFrameworkFactory.newClient(properties.getServerAddress(), properties.getSessionTimeOut(), properties.getConnectionTimeOut(), retryPolicy);

        //启动
        client.start();
    }

    /**
     * 测试创建节点
     * @throws Exception
     */
    public void testCreate() throws Exception{
        //创建永久节点
        client.create().forPath("/curator","/curator data".getBytes());

        //创建永久有序节点
        client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/curator_sequential","/curator_sequential data".getBytes());

        //创建临时节点
        client.create().withMode(CreateMode.EPHEMERAL)
                .forPath("/curator/ephemeral","/curator/ephemeral data".getBytes());

        //创建临时有序节点
        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath("/curator/ephemeral_path1","/curator/ephemeral_path1 data".getBytes());

        client.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath("/curator/ephemeral_path2","/curator/ephemeral_path2 data".getBytes());
    }
}
