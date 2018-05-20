package com.msj.mzkui.service.impl;

import com.msj.mzkui.entity.Student;
import com.msj.mzkui.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private JdbcTemplate template;
    @Override
    public boolean saveOne(Student entity) {
        String sql = "INSERT INTO tb_student (id,name,age,create_time,update_time) values (?,?,?,?,?)";
        int flag = template.update(sql, new Object[]{entity.getId(), entity.getName(), entity.getAge(), entity.getCreateTime(), entity.getUpdateTime()});
        return flag > 0;
    }

    @Override
    public List<Student> findAll() {
        String sql = "select * from tb_student";
        return template.query(sql, new RowMapper<Student>() {
            @Override
            public Student mapRow(ResultSet resultSet, int i) throws SQLException {
                Student student = new Student();
                student.setId(resultSet.getString("id"));
                student.setName(resultSet.getString("name"));
                student.setAge(resultSet.getInt("age"));
                student.setCreateTime(resultSet.getDate("create_time"));
                student.setUpdateTime(resultSet.getDate("update_time"));
                student.setStatus(resultSet.getInt("status"));
                return student;
            }
        });
    }

    @Override
    public void updateStauts(List<String> ids) {
        String sql = "update tb_student set status = ? where id = ?";
        List<Object[]> args = new ArrayList<>();
        for (String id : ids){
            Object[] arg = new Object[2];
            arg[0] = 1;
            arg[1] = id;
            args.add(arg);
        }
        template.batchUpdate(sql,args);
    }
}
