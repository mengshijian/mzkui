package com.msj.mzkui.entity;

import com.sun.tools.javac.jvm.Code;

import java.io.Serializable;
import java.util.List;

public class CodeLibary implements Serializable {

    private String id;

    private String code;

    private String upperCode;

    private String codeName;

    private List<CodeLibary> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUpperCode() {
        return upperCode;
    }

    public void setUpperCode(String upperCode) {
        this.upperCode = upperCode;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public List<CodeLibary> getChildren() {
        return children;
    }

    public void setChildren(List<CodeLibary> children) {
        this.children = children;
    }
}
