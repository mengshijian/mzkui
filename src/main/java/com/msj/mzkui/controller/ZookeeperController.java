package com.msj.mzkui.controller;

import com.msj.mzkui.common.utils.ZkClientUtils;
import com.msj.mzkui.config.ZkClientProperties;
import com.msj.mzkui.controller.vo.ResResult;
import com.msj.mzkui.entity.Node;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zk")
public class ZookeeperController extends BaseRestfulController {

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
        String path = request.getParameter("id");
        if (StringUtils.isBlank(path)){
            path = PATH_SPLIT;
        }
        return loadNode(path);
    }

    private List<Node> loadNode(String path){
        List<Node> nodesList = new ArrayList<>();
        List<String> nodes = zkClientUtils.getTreeNode(path);
        if (CollectionUtils.isNotEmpty(nodes)){
            if (!path.equalsIgnoreCase(PATH_SPLIT)){
                path = path + PATH_SPLIT;
            }
            final String finalPath = path;
            nodes.forEach(e -> {
                if (!e.equalsIgnoreCase("dubbo")){
                    Node node = new Node(e, finalPath + e);
                    node.setChildren(loadNode(node.getPath()));
                    nodesList.add(node);
                }
            });
        }
        return nodesList;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Node> loadListByPath(String path){
        if (path.equalsIgnoreCase(PATH_SPLIT)){
            return new ArrayList<>();
        }
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
        node.setValue(zkClientUtils.readNode(node.getPath()));
        resultList.add(node);
        List<Node> children = node.getChildren();
        if (CollectionUtils.isNotEmpty(children)){
            children.forEach(e -> {
                loadNodeValue(e,resultList);
            });
        }
    }

    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    public ResResult<Boolean> addNode(@RequestBody Node node){
        boolean flag = false;
        if (StringUtils.isNotBlank(node.getText()) && StringUtils.isNotBlank(node.getPath())){
            node.setPath(node.getPath() + PATH_SPLIT + node.getText());
            flag = zkClientUtils.saveOrUpdateNode(node.getPath(),node.getValue());
        }
        return resultSuccess(flag);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ResResult<Boolean> deleteNode(@RequestBody Node node){
        boolean flag = false;
        if (StringUtils.isNotBlank(node.getPath())){
           flag = zkClientUtils.deleteNode(node.getPath(),true);
        }
        return resultSuccess(flag);
    }

    @RequestMapping(value = "/load",method = RequestMethod.POST)
    public ResResult<Node> loadNode (@RequestBody Node node) {
        if (StringUtils.isNotBlank(node.getPath())) {
            String value = zkClientUtils.readNode(node.getPath());
            node.setValue(value);
        }
        return resultSuccess(node);
    }
}
