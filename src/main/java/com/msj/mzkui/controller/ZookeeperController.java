package com.msj.mzkui.controller;

import com.msj.mzkui.common.utils.ZkClientUtils;
import com.msj.mzkui.config.ZkClientProperties;
import com.msj.mzkui.entity.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.AbstractHashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zk")
public class ZookeeperController {

    private static final String PATH_SPLIT = "/";

    @Autowired
    private ZkClientProperties zkClientProperties;

    private ZkClientUtils zkClientUtils;

    @PostConstruct
    public void initZk(){
        ZkClientUtils.initZkClient(zkClientProperties);
    }

    @RequestMapping(value = "/tree",method = RequestMethod.POST)
    public List<Node> treeList(HttpServletRequest request){
        List<Node> nodesList = new ArrayList<>();
        String path = request.getParameter("path");
        if (StringUtils.isBlank(path)){
            path = PATH_SPLIT;
        }
        return loadNode(path);
    }

    private List<Node> loadNode(String path){
        List<Node> nodesList = new ArrayList<>();
        List<String> nodes = zkClientUtils.getTreeNode(path);
        if (CollectionUtils.isNotEmpty(nodes)){
            nodes.forEach(e -> {
                Node node = new Node(e, path + e);
                node.setChildren(loadNode(node.getPath()));
                nodesList.add(node);
            });
        }
        return nodesList;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Node> loadListByPath(String path){
        List<Node> nodes = loadNode(path);
        List<Node> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(nodes)){
            nodes.forEach(e -> {
                loadNodeValue(e,list);
            });
        }
        return list;
    }

    private void loadNodeValue(Node node,List<Node> resultList){
        List<Node> children = node.getChildren();
        if (CollectionUtils.isNotEmpty(children)){
            children.forEach(e -> {
                loadNodeValue(e,resultList);
            });
        } else {
            node.setValue(zkClientUtils.readNode(node.getPath()));
            resultList.add(node);
        }
    }
}
