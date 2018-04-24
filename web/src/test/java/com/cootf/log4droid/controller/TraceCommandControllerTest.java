package com.cootf.log4droid.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.cootf.log4droid.BaseTest;
import com.cootf.log4droid.base.query.BaseQuery;
import com.cootf.log4droid.entity.TraceCommand;
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

public class TraceCommandControllerTest extends BaseTest {

    @Autowired
    private TraceCommandController controller;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void save() throws Exception {
        TraceCommand command = new TraceCommand();
        command.setUserId("123456");
        command.setCommandLine("ls");
        command.setCreateTime(new Date());
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(command);
        mvc.perform(MockMvcRequestBuilders.post("/trace/save")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.code").exists());
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/trace/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("userId", "123456")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    public void findList() throws Exception {
        BaseQuery queryEntity = new BaseQuery();
        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(queryEntity);
        mvc.perform(MockMvcRequestBuilders.post("/trace/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


}