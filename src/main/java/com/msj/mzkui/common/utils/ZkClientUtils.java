package com.msj.mzkui.common.utils;

import com.msj.mzkui.config.ZkClientProperties;
import org.I0Itec.zkclient.DataUpdater;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class ZkClientUtils {

    private static final Logger logger = LoggerFactory.getLogger(ZkClientUtils.class);

    private static ZkClient zkClient;

    public static void initZkClient(ZkClientProperties properties) {
        zkClient = new ZkClient(properties.getServerAddress(), properties.getSessionTimeOut(),
                properties.getConnectionTimeOut(), new CusZkSerializer(),
                properties.getOperationRetryTimeout());
    }

    /**
     * 创建节点
     */
    public static boolean createNode(String path, Object data) {
        boolean flag = false;
        if (isInit() && !checkNode(path)) {
            zkClient.createPersistent(path,true);
            if (checkNode(path)) {
                if (data != null){
                    zkClient.writeData(path,data);
                }
                logger.debug("{} create success", path);
                flag = true;
            }
        }
        return flag;
    }

    private static boolean isInit() {
        if (zkClient == null) {
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
    private static boolean checkNode(String path) {
        boolean flag = false;
        flag = zkClient.exists(path);
        if (flag) {
            logger.debug("{} node exist", path);
        }
        return flag;
    }

    private static <T> boolean updateNode(String path, DataUpdater<T> updater) {
        boolean flag = false;
        if (isInit() && checkNode(path)) {
            zkClient.updateDataSerialized(path, updater);
            flag = true;
        }
        return flag;
    }

    public static boolean saveOrUpdateNode(String path, Object data) {
        boolean flag = false;
        if (isInit()) {
            //如果节点存在,则更新
            if (checkNode(path)) {
                flag = updateNode(path, (DataUpdater) o -> data);
            }else {
                flag = createNode(path,data);
            }
        }
        return flag && zkClient.exists(path);
    }

    public static boolean deleteNode(String path, boolean recursive) {
        boolean flag = false;
        if (isInit() && checkNode(path)) {
            if (recursive) {
                flag = zkClient.deleteRecursive(path);
            } else {
                flag = zkClient.delete(path);
            }
        }
        return flag;
    }

    public static <T> T readNode(String path) {
        if (isInit() && checkNode(path)) {
            T t = zkClient.readData(path, new Stat());
            return t;
        }
        return null;
    }

    public static List<String> getTreeNode(String path) {
        if (isInit() && checkNode(path)) {
            return zkClient.getChildren(path);
        }
        return new ArrayList<>();
    }
}
