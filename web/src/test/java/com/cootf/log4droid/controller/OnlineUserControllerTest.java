package com.cootf.log4droid.controller;

import static org.junit.Assert.*;

import com.cootf.log4droid.BaseTest;
import com.cootf.log4droid.base.query.BaseQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class OnlineUserControllerTest extends BaseTest {

    @Autowired
    private OnlineUserController controller;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void findByPage() throws Exception {
        BaseQuery queryEntity = new BaseQuery();
        queryEntity.setPageNumber(1);
        queryEntity.setPageSize(5);
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(queryEntity);
        mvc.perform(MockMvcRequestBuilders.post("/onlineUser/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}