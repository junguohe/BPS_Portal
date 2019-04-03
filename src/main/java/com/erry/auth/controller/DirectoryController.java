package com.erry.auth.controller;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.erry.auth.model.Dictionary;
import com.erry.auth.model.Json;
import com.erry.auth.model.ProdLine;
import com.erry.auth.service.DirectoryServiceImpl;

@Controller("directoryController")
public class DirectoryController {


	@Autowired
	private DirectoryServiceImpl directoryService;

	Logger log = Logger.getLogger(DirectoryController.class);

	//
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<Dictionary>  findList(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="module",required=false)String module ,
											@RequestParam(value="functions",required=false)String function ){
		List<Dictionary> pageInfo =directoryService.findList(module,function);
		ExtDirectStoreReadResult<Dictionary> slDTO = new ExtDirectStoreReadResult<Dictionary>(pageInfo);
		return slDTO;
	}
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<Dictionary>  findDictionary(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="code",required=false)String code,
											@RequestParam(value="value",required=false)String value){
		//获取分页后Map集合
				Map<Integer,List<Dictionary>> pageMap = directoryService.findDictionary(request.getPage()-1, request.getLimit(),code,value);
				Integer total = null;
				List<Dictionary> list = new ArrayList<Dictionary>();
				for(Map.Entry<Integer,List<Dictionary>> entry : pageMap.entrySet()){
					total = entry.getKey();
					list = entry.getValue();
				}
				ExtDirectStoreReadResult<Dictionary> slDTO = new ExtDirectStoreReadResult<Dictionary>(total,list);
				return slDTO;
	}
	//{id:id,module:module,functions:functions,value:value,code:code,remark:remark,active:active}; 
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json updateOrsave(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="id",required=false) String id,
			@RequestParam(value="module",required=false) String module,
			@RequestParam(value="functions",required=false) String functions,
			@RequestParam(value="value",required=false) String value,
			@RequestParam(value="code",required=false) String code,
			@RequestParam(value="remark",required=false) String remark,
			@RequestParam(value="active",required=false) String active
			){
		
				Dictionary dictionary=new Dictionary();
				String msg=null;
				Boolean flag=false;
				dictionary=directoryService.updateOrsave(id,module,functions,value,code,remark,active);
				if(functions.equals("area")){
					directoryService.updateArea();
				}
				if(dictionary.getId()!=null){
					flag=true;
					msg="操作成功";
					
				}else{
					msg="操作失败";
				}
				
				Json json = new Json();
				json.setData(dictionary);
				json.setMsg(msg);
				json.setSuccess(flag);
				return json;
		    }
	
	
	
}
