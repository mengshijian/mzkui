package com.msj.mzkui.service;

import com.msj.mzkui.BaseTest;
import com.msj.mzkui.entity.CodeLibary;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CodeLibaryServiceTest extends BaseTest {

    @Autowired
    private CodeLibaryService codeLibaryService;

    @Test
    public void findCodeTest() {
        List<CodeLibary> parents = codeLibaryService.findCode(null);
        List<CodeLibary> target = new ArrayList<>();
        codeLibaryService.findLeaf(parents,target);
        target.forEach(e -> {
            System.out.println(e.getCode());
        });
    }
}