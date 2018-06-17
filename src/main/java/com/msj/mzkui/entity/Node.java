package com.msj.mzkui.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msj.mzkui.common.annotation.FieldName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class Node {

    /**
     * 节点id
     */
    @FieldName(Ignore = true)
    private String id;

    /**
     * 节点值
     */
    private String value;

    /**
     * 节点名称
     */
    private String text;

    /**
     * 节点路径
     */
    private String path;

    /**
     * 前端展开状态:关闭
     */
    @FieldName(Ignore = true)
    private String state = "closed";

    /**
     * 子节点
     */
    private List<Node> children;

    public Node() {
    }

    public Node(String text, String path) {
        this.text = text;
        this.path = path;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        if (StringUtils.isBlank(id)){
            this.id = path;
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
