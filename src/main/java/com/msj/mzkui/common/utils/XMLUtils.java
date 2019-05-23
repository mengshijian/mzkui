package com.msj.mzkui.common.utils;

import com.msj.mzkui.common.annotation.FieldName;
import com.msj.mzkui.entity.Node;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLUtils {

    private static final Logger logger = LoggerFactory.getLogger(XMLUtils.class);

    public static void CreateXMLByDOM4J(List<Node> nodes,OutputStream out) {
        // 创建Document对象
        Document document = DocumentHelper.createDocument();
        // 创建根节点
        Element rss = createHeader(document);

        // 创建channel子节点
        for (Node node : nodes) {
            createData(rss,node);
        }

        // 创建输出格式(OutputFormat对象)
        OutputFormat format = OutputFormat.createPrettyPrint();

        //设置输出文件的编码
        format.setEncoding("UTF-8");

        try {
            // 创建XMLWriter对象
            XMLWriter writer = new XMLWriter(out, format);

            //设置不自动进行转义
            writer.setEscapeText(false);

            // 生成XML文件
            writer.write(document);

            //关闭XMLWriter对象
            writer.close();
        } catch (IOException e) {
            logger.error("导出xml文件异常:{}",e);
        }
    }


    /**
     * 创建xml文件头
     * @param document
     * @return
     */
    public static Element createHeader(Document document){
        // 创建根节点
        Element rss = document.addElement("zookeeper");
        //为rss根节点添加属性
        rss.addAttribute("version", "2.0");

        return rss;
    }

    /**
     * 构造数据节点
     * @param element
     * @param node
     */
    public static void createData(Element element,Node node){
        try {
            Field[] fields = node.getClass().getDeclaredFields();
            for (Field field : fields){
                FieldName fieldAn = field.getAnnotation(FieldName.class);
                String eleName = field.getName();
                if (fieldAn != null){
                    if (!fieldAn.Ignore()){
                        eleName = fieldAn.value();
                    } else {
                        continue;
                    }
                }
                Object nodeVal = MethodUtils.invokeExactMethod(node,upperFirst(field),null);
                //如果值不为空，则创建节点
                if (nodeVal != null){
                    Element ele = element.addElement(eleName);
                    if (field.getName().equalsIgnoreCase("children")){
                        List<Node> childRen = node.getChildren();
                        childRen.forEach(e -> {
                            createData(ele,e);
                        });
                    } else {
                        ele.setText(nodeVal.toString());
                    }
                }
            }
        } catch (Exception e){
            logger.error("构造xml节点异常:{}",e);
        }
    }

    /**
     * 获取属性的get方法名称
     * @param field
     * @return
     */
    public static String upperFirst(Field field) {
        String prefix = "get";
        String fieldName = field.getName();
        if (field.getType() == Boolean.class || field.getType() == boolean.class) {
            prefix = "is";
        }
        return prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

}
