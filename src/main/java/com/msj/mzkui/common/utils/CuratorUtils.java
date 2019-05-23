package com.msj.mzkui.common.utils;

import com.msj.mzkui.config.ZkClientProperties;
import org.I0Itec.zkclient.DataUpdater;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CuratorUtils {

    private static final Logger logger = LoggerFactory.getLogger(CuratorUtils.class);

    private static final String charset = "UTF-8";

    //创建连接实例
    private static CuratorFramework client = null;

    /**
     * baseSleepTimeMs：初始的重试等待时间
     * maxRetries：最多重试次数
     */
    RetryPolicy retryPolicy = new ExponentialBackoffRetry(4000, 3);

    public void init(ZkClientProperties properties){
        //创建 CuratorFrameworkImpl实例
        //client = CuratorFrameworkFactory.newClient(properties.getServerAddress(), properties.getSessionTimeOut(), properties.getConnectionTimeOut(), retryPolicy);
        client = CuratorFrameworkFactory.newClient("192.168.239.129:2181", 1000, 1000, retryPolicy);
        //启动
        client.start();
    }

    /**
     * 测试创建节点
     * @throws Exception
     */
    public void testCreate() throws Exception{
        //创建永久节点
        /*client.createContainers("/dome/msjNo/test-ch/curator");
        client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/dome/msjNo/test-ch/curator","/curator data".getBytes());*/
        List<String> nodes = client.getChildren().forPath("/dome");
        nodes.forEach(e -> System.out.println(e));
        /*//创建永久有序节点
        client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/curator_sequential","/curator_sequential data".getBytes());

        //创建临时节点
        client.create().withMode(CreateMode.EPHEMERAL)
                .forPath("/curator/ephemeral","/curator/ephemeral data".getBytes());

        //创建临时有序节点
        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath("/curator/ephemeral_path1","/curator/ephemeral_path1 data".getBytes());

        client.create().withProtection().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath("/curator/ephemeral_path2","/curator/ephemeral_path2 data".getBytes());*/


    }

    /**
     * 创建节点
     */
    public static boolean createNode(String path, Object data,Boolean persistent) throws Exception {
        boolean flag = false;
        if (isInit() && !checkNode(path)) {
            if (persistent){
                client.create().withMode(CreateMode.PERSISTENT).forPath(path,data.toString().getBytes(charset));
            }else {
                client.create().forPath(path,data.toString().getBytes(charset));
            }
            if (checkNode(path)) {
                logger.debug("{} create success", path);
                flag = true;
            }
        }
        return flag;
    }

    private static boolean isInit() {
        if (client == null) {
            logger.error("ZkClient UnInitialize");
            return false;
        }
        return true;
    }

    /**
     * 检查节点是否存在
     *
     * @param path
     * @return
     */
    private static boolean checkNode(String path) throws Exception {
        boolean flag;
        flag = client.checkExists().forPath(path) == null;
        if (flag) {
            logger.debug("{} node exist", path);
        }
        return flag;
    }

    private static <T> boolean updateNode(String path, Object data) throws Exception {
        boolean flag = false;
        if (isInit() && checkNode(path)) {
            Stat sta = client.setData().forPath(path,data.toString().getBytes(charset));
            flag = sta == null;
        }
        return flag;
    }

    public static boolean saveOrUpdateNode(String path, Object data,Boolean persistent) throws Exception {
        boolean flag = false;
        if (isInit()) {
            //如果节点存在,则更新
            if (checkNode(path)) {
                flag = updateNode(path, (DataUpdater) o -> data);
            }else {
                flag = createNode(path,data,persistent);
            }
        }
        return flag && client.checkExists().forPath(path) != null;
    }

    public static boolean deleteNode(String path) throws Exception {
        if (isInit() && checkNode(path)) {
            client.delete().forPath(path);
        }
        return checkNode(path);
    }

    public static String readNode(String path) throws Exception {
        if (isInit() && checkNode(path)) {
            return new String(client.getData().forPath(path),charset);
        }
        return null;
    }

    public static List<String> getTreeNode(String path) throws Exception {
        if (isInit() && checkNode(path)) {
            return client.getChildren().forPath(path);
        }
        return new ArrayList<>();
    }

    public static void  main(String[] args){
        CuratorUtils utils = new CuratorUtils();
        try {
            utils.init(new ZkClientProperties());
            utils.testCreate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
