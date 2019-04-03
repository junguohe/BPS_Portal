package com.erry.auth.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.erry.auth.dto.TreeDTO;
import com.erry.auth.model.Menu;
import com.erry.auth.service.MenuServiceImpl;

@Controller("menuController")
public class MenuController {

	@Autowired
	private MenuServiceImpl menuserviceimpl;

	Logger log = Logger.getLogger(MenuController.class);

	@ExtDirectMethod
	public TreeDTO getMenu(){
		return menuserviceimpl.findFunction();
	}
	

}
