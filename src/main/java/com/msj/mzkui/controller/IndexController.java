package com.msj.mzkui.controller;

import com.msj.mzkui.common.utils.ExportByOpenCsvUtils;
import com.msj.mzkui.entity.Student;
import com.msj.mzkui.service.StudentService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView toIndex(){
        ModelAndView model = new ModelAndView();
        List<Student> list = studentService.findAll();
        model.addObject("items",list);
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = "/downLoad")
    public void export(HttpServletResponse response){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        List<Student> list = studentService.findAll();
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
        fieldMap.put("name","姓名");
        fieldMap.put("age","年龄");
        fieldMap.put("createTime","创建时间");
        fieldMap.put("updateTime","更新时间");
        ExportByOpenCsvUtils.export("测试导出",list,fieldMap,response);
        studentService.updateStauts(list.stream().map(e -> e.getId()).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/check")
    @ResponseBody
    public Map<String,Object> check(){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        List<Student> list = studentService.findAll();
        List<Student> exist = list.stream().filter(e -> e.getStatus() == 1).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(exist)){
            modelMap.put("reqCode","exist");
            modelMap.put("reqMsg","存在已经导出的数据,是否导出?");
        } else {
            modelMap.put("reqCode","ok");
            modelMap.put("reqMsg","");
        }
        return modelMap;
    }
}
