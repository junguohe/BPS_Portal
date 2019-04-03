package com.erry.test.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.erry.test.BaseTest;

/**
 * 
 * @author Lishiyong
 * @date 2015年11月12日
 */
@WebAppConfiguration
public class ControllerTest extends BaseTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	
	private MockMvc mockMvc;
	
	@Test
	public void testUpdatePassword() throws Exception {
		ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders
                .post("/sysUser/updatePassword")
                .accept(MediaType.APPLICATION_JSON).param("password", "123456"));
        MvcResult mr = ra.andReturn();
        String result = mr.getResponse().getContentAsString();
        System.out.println(result);
	}
	
	
}
