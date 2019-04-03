package com.erry.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.erry.util.EncrypUtil;

@Controller("sysAuthorityContro")
@RequestMapping("/authority")
public class SysAuthorityController {
	
    @RequestMapping(value="/loginAuto", method=RequestMethod.GET)
    public ModelAndView loginAuto(
    		@RequestParam(value="username") String username, 
    		@RequestParam(value="password") String password) {
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("username", EncrypUtil.decoder(username));
    	model.put("password", EncrypUtil.decoder(password));
    	return new ModelAndView("loginauto", model);
    }
	
}
