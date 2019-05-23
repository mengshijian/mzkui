package com.msj.mzkui.service;

import com.msj.mzkui.entity.CodeLibary;

import java.util.List;

public interface CodeLibaryService {

    List<CodeLibary> findCode(String upperCode);

    void findLeaf(List<CodeLibary> list,List<CodeLibary> leafs);
}
