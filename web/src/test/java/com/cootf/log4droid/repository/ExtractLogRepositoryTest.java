package com.cootf.log4droid.repository;

import static org.junit.Assert.*;

import com.cootf.log4droid.BaseTest;
import com.cootf.log4droid.controller.vo.DataPageable;
import com.cootf.log4droid.entity.ExtractLog;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class ExtractLogRepositoryTest extends BaseTest {

    @Autowired
    private ExtractLogRepository repository;

    @Autowired
    private MongoTemplate template;


    @Test
    public void findByPageTest() {
        DataPageable<ExtractLog> pageable = new DataPageable<ExtractLog>();
        List<ExtractLog> list = template.find(new Query(), ExtractLog.class);
        System.out.println(list.size());
    }

}