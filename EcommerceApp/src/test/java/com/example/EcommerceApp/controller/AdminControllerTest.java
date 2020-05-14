package com.example.EcommerceApp.controller;
import com.example.EcommerceApp.entities.Customer;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.constraints.AssertTrue;

class AdminControllerTest extends AbstractTest{

    @Override
    @Before
    public void setUp(){
        super.setUp();
    }

    @Test
    public void test_GetCustomer() throws Exception {
        String uri="/admin/home/customers";
        MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        int httpStatusCode=mvcResult.getResponse().getStatus();
        assertEquals(httpStatusCode,200);
        String content=mvcResult.getResponse().getContentAsString();
        Customer[] customerArray=super.mapFromJson(content,Customer[].class);
        assertTrue(customerArray.length>0);
    }
}