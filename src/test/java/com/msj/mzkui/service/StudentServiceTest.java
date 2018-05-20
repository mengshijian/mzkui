package com.msj.mzkui.service;

import com.msj.mzkui.BaseTest;
import com.msj.mzkui.entity.Student;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.UUID;


public class StudentServiceTest extends BaseTest {

    @Autowired
    private StudentService studentService;

    private Student student;

    @Before
    public void setUp(){
        student = new Student();
        student.setId(UUID.randomUUID().toString().replaceAll("-",""));
        student.setName("赵六");
        student.setAge(26);
        student.setCreateTime(new Date());
        student.setUpdateTime(new Date());
    }

    @Test
    public void testSaveOne() throws Exception {
        boolean flag = studentService.saveOne(student);
        System.out.println(flag);
    }
}
