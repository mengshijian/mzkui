package com.cootf.log4droid.controller;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.cootf.log4droid.BaseTest;
import com.cootf.log4droid.base.query.BaseQuery;
import com.cootf.log4droid.common.utils.DateUtils;
import com.cootf.log4droid.entity.ExtractLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class ExtractLogControllerTest extends BaseTest {

    @Autowired
    private ExtractLogController controller;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void save() throws Exception {
        ExtractLog log = new ExtractLog();
        log.setUserId("123456");
        log.setLimit(64);
        log.setFromTime(DateUtils.addDays(new Date(), -1));
        log.setToTime(new Date());
        log.setDeviceId("10087");
        log.setLogLevel(3);
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(log);
        mvc.perform(MockMvcRequestBuilders.post("/extractLog/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.code").exists());
    }

    @Test
    public void findList() throws Exception {
        BaseQuery queryEntity = new BaseQuery();
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(queryEntity);
        mvc.perform(MockMvcRequestBuilders.post("/extractLog/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}