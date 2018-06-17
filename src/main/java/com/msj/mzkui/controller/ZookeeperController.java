package com.msj.mzkui.controller;

import com.msj.mzkui.common.utils.XMLUtils;
import com.msj.mzkui.common.utils.ZkClientUtils;
import com.msj.mzkui.config.ZkClientProperties;
import com.msj.mzkui.controller.vo.ResResult;
import com.msj.mzkui.entity.Node;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zk")
public class ZookeeperController extends BaseRestfulController {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperController.class);

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
        try {
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
        } catch (Exception e){
            logger.error("查询异常:{}",e);
        }
        return null;
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

    @RequestMapping(value = "/export",method = RequestMethod.GET)
    public void exportXml(String root,HttpServletResponse response){
        try {
            List<Node> nodesList = loadNode(root);
            // 设置response头信息
            response.reset(); // 清空输出流
            response.setContentType("APPLICATION/OCTET-STREAM"); // 定义输出类型
            String xmlName = new String(root.substring(root.lastIndexOf("/") + 1).getBytes("gbk"), "ISO-8859-1");
            response.setHeader("Content-disposition", "attachment;  filename=" + xmlName + ".xml");
            response.setCharacterEncoding("UTF-8");
            XMLUtils.CreateXMLByDOM4J(nodesList,response.getOutputStream());
        } catch (IOException e) {
            logger.error("文件导出异常:{}",e);
        }
    }
}
