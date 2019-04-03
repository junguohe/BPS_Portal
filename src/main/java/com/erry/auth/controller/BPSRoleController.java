package com.erry.auth.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.erry.auth.model.BPSRole;
import com.erry.auth.model.Json;
import com.erry.auth.service.BPSRoleServiceImpl;

@Controller("bpsrolecontrol")
public class BPSRoleController {
	Logger log = Logger.getLogger(BPSRoleController.class);
	
	@Autowired
	private BPSRoleServiceImpl bpsroleserviceimpl;
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<BPSRole>  findList(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="userid",required=false)String userid){
		List<BPSRole> pageInfo =bpsroleserviceimpl.findList(userid);
		ExtDirectStoreReadResult<BPSRole> slDTO = new ExtDirectStoreReadResult<BPSRole>(pageInfo);
		return slDTO;
	}
	
	
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json SaveOrUpdate(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="userid",required=false) String  userid,
			@RequestParam(value="roleids",required=false) List<String> roleids
			)
			{
				String msg=null;
				Boolean result=true;
				bpsroleserviceimpl.SaveOrUpdate(userid,roleids);
				Json json = new Json();
				json.setData(null);
				json.setMsg("操作成功！");
				json.setSuccess(result);
				return json;
		}
	
}
