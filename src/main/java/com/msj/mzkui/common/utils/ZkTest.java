package com.msj.mzkui.common.utils;

import com.msj.mzkui.config.ZkClientProperties;
import org.apache.zookeeper.*;

import java.util.List;

public class ZkTest {

    public static void main(String[] args){
        ZkClientProperties properties = new ZkClientProperties();
        properties.setServerAddress("192.168.239.129:2181");
        ZkClientUtils.initZkClient(properties);
        /*ZkClientUtils.createNode("/dome/test/test0","test0",CreateMode.PERSISTENT);
        ZkClientUtils.createNode("/dome/test/test1","test1",CreateMode.PERSISTENT);
        ZkClientUtils.createNode("/dome/test/test2","test2",CreateMode.PERSISTENT);*/
        ZkClientUtils.createNode("/dome/msj/test2","test2");
        List<String> nodes = ZkClientUtils.getTreeNode("/dome");
        /*String val = ZkClientUtils.readNode("/dome/msj");
        System.out.println(val);*/
        //ZkClientUtils.saveOrUpdateNode("/dome/msj/test2","msjtest3");
        nodes.forEach(e ->System.out.println(e));
    }
}
