package com.cootf.log4droid.service.impl;

import static org.junit.Assert.*;

import com.cootf.log4droid.BaseTest;
import com.cootf.log4droid.entity.ExtractLog;
import com.cootf.log4droid.service.ExtractLogService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ExtractLogServiceImplTest extends BaseTest {

    @Autowired
    private ExtractLogService service;

    @Test
    public void saveOne() {
        ExtractLog log = new ExtractLog();
        service.saveOne(log);
    }
}