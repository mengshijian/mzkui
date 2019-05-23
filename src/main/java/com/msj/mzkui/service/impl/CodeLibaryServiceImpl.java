package com.msj.mzkui.service.impl;

import com.msj.mzkui.entity.CodeLibary;
import com.msj.mzkui.service.CodeLibaryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CodeLibaryServiceImpl implements CodeLibaryService {

    @Autowired
    private JdbcTemplate template;

    @Override
    public List<CodeLibary> findCode(String upperCode) {
        StringBuffer sb =  new StringBuffer("select * from code_libary where 1 = 1 ");
        if (StringUtils.isBlank(upperCode)){
            sb.append(" and UPPERCODE is ? ");
        }else{
            sb.append(" and UPPERCODE = ? ");
        }
        List<CodeLibary> parents = template.query(sb.toString(),new Object[]{upperCode},new BeanPropertyRowMapper<>(CodeLibary.class));
        if (CollectionUtils.isNotEmpty(parents)){
            parents.forEach(e -> {
                List<CodeLibary> children = findCode(e.getCode());
                e.setChildren(children);
            });
        }
        return parents;
    }



    @Override
    public void findLeaf(List<CodeLibary> list,List<CodeLibary> leafs) {
        List<CodeLibary> target = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)){
            list.forEach(e -> {
                List<CodeLibary> children = e.getChildren();
                if (CollectionUtils.isEmpty(children)){
                    leafs.add(e);
                }else{
                    findLeaf(children,leafs);
                }
            });
        }
    }


}
