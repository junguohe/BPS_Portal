package com.erry.auth.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.erry.auth.component.CurrentUserInfo;
import com.erry.auth.model.AuUser;
import com.erry.auth.model.Json;
import com.erry.auth.model.UserBPS;
import com.erry.auth.service.AuUserSeviceImpl;
import com.erry.auth.service.UserBPSServiceImpl;

@Controller("auuserController")
public class AuUserController {
	
	@Autowired
	private AuUserSeviceImpl impl;
	
	@Autowired
	private UserBPSServiceImpl userBPSServiceImpl;
	
	Logger log = Logger.getLogger(AuUserController.class);
	//重置密码
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json fulisheAuUser(ExtDirectStoreReadRequest request,@RequestParam(value="userid",required=false) String userid){
		return impl.updatePasswordNew(userid);
	}
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json updateAuUser(ExtDirectStoreReadRequest request 
			,@RequestParam(value="password",required=false) String password
			,@RequestParam(value="newpassword",required=false) String newpassword) throws Exception{
		return impl.updateAuUserPassWord(password,newpassword);
	}
	
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json saveAuUser(ExtDirectStoreReadRequest request 
			,@RequestParam(value="auuser",required=false) AuUser auuser){
		return impl.saveAuUser(auuser);
	}
	
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json getUser(){
				AuUser user=CurrentUserInfo.getUser();				
				Json json = new Json();
				json.setData(user);
				json.setMsg(null);
				json.setSuccess(true);
				return json;
		    }
	
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json getUserId(){
				String uid=CurrentUserInfo.getUid();				
				Json json = new Json();
				json.setData(uid);
				json.setMsg(null);
				json.setSuccess(true);
				return json;
		    }
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public ExtDirectStoreReadResult<UserBPS> readuserbps(ExtDirectStoreReadRequest request){
		ExtDirectStoreReadResult<UserBPS> user= new ExtDirectStoreReadResult<UserBPS>(userBPSServiceImpl.readAll());
		return user;
	}
}
