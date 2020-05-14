package com.example.EcommerceApp.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp(){
       // mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // this method is used for sending the request body
    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    // this method is used to handle the request  body
    protected<T> T mapFromJson(String json,Class<T> tClass) throws JsonParseException, JsonMappingException, IOException{
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.readValue(json,tClass);
    }
}
