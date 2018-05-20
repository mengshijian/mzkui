package com.msj.mzkui.service;

import com.msj.mzkui.entity.Student;

import java.util.List;

public interface StudentService {

    boolean saveOne(Student entity);

    List<Student> findAll();

    void updateStauts(List<String> ids);
}
