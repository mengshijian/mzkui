package com.cootf.log4droid.service;

import static org.junit.Assert.*;

import com.cootf.log4droid.BaseTest;
import com.cootf.log4droid.controller.vo.DataPageable;
import com.cootf.log4droid.entity.ExtractLog;
import com.cootf.log4droid.service.ExtractLogService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ExtractLogServiceTest extends BaseTest {

    @Autowired
    private ExtractLogService service;

    @Test
    public void saveOne() {
        DataPageable<ExtractLog> page = new DataPageable<ExtractLog>();
        service.findByPage(null, page, ExtractLog.class);
    }
}