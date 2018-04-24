package com.cootf.log4droid.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.cootf.log4droid.BaseTest;
import com.cootf.log4droid.common.utils.DateUtils;
import com.cootf.log4droid.entity.ExtractLog;
import com.cootf.log4droid.entity.UserInfo;
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

public class LoginControllerTest extends BaseTest {

    @Autowired
    private LoginController controller;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void login() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("ctf123");
        userInfo.setPassword("admin");
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(userInfo);
        mvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.code").exists());
    }
}