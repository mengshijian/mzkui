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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
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
    @ResponseBody
    public List<Node> treeList(HttpServletRequest request){
        List<Node> nodesList = new ArrayList<>();
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
                Node node = new Node(e, finalPath + e);
                node.setChildren(loadNode(node.getPath()));
                nodesList.add(node);
            });
        }
        return nodesList;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
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

    @RequestMapping(value = "/preAdd",method = RequestMethod.GET)
    public ModelAndView preAdd(){
        ModelAndView model = new ModelAndView();
        model.setViewName("addOrUpdate");
        return model;
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public ResResult<Boolean> addNode(@RequestBody Node node){
        boolean flag = false;
        if (StringUtils.isNotBlank(node.getText()) && StringUtils.isNotBlank(node.getPath())){
            node.setPath(node.getPath() + PATH_SPLIT + node.getText());
            flag = zkClientUtils.createNode(node.getText(),node.getPath());
        }
        return resultSuccess(flag);
    }
}
