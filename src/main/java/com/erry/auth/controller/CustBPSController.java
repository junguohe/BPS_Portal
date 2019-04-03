package com.erry.auth.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.erry.auth.dto.CustBPSDTO;
import com.erry.auth.model.CustBPS;
import com.erry.auth.model.Json;
import com.erry.auth.repository.CustBPSRepository;
import com.erry.auth.service.CustBPSServiceImpl;

@Controller("custbpscontrol")
public class CustBPSController {
	Logger log = Logger.getLogger(CustBPSController.class);
	@Autowired
	private CustBPSServiceImpl serviceImpl;

	@Autowired
	private CustBPSRepository custBPSRepository;
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<CustBPSDTO> readcustDTO(
			ExtDirectStoreReadRequest request,
			@RequestParam(value = "cpid", required = false) String cpid) {
		Page<CustBPS> pageinfo = serviceImpl.fingList(request.getPage() - 1,
				request.getLimit(), cpid);
		List<CustBPSDTO> list = serviceImpl.converTocustomerDTOList(pageinfo
				.getContent());
		ExtDirectStoreReadResult<CustBPSDTO> result = new ExtDirectStoreReadResult<CustBPSDTO>(
				pageinfo.getTotalElements(), list);
		return result;
	}
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json deleteAdd(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="id",required=false) String id
			){
				CustBPS add=new CustBPS();
				String msg=null;
				Boolean flag=false;
				add=serviceImpl.deleteAdd(custBPSRepository.findById(id),"0");
				if(add.getActive().equals("0")){
					flag=true;
					msg="删除成功";
				}else{
					msg="删除失败";
				}
				
				Json json = new Json();
				json.setData(add);
				json.setMsg(msg);
				json.setSuccess(flag);
				return json;
	  }	
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json updateOrsave(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="record",required=false) CustBPS cust,
			@RequestParam(value="cpid",required=false) String cpid
			){
		
				CustBPS add=new CustBPS();
				String msg=null;
				Boolean flag=false;
				add=serviceImpl.saveCustBPS(cust, cpid);
				if(add.getId()!=null){
					flag=true;
					msg="操作成功";
				}else{
					msg="操作失败";
				}
				
				Json json = new Json();
				json.setData(add);
				json.setMsg(msg);
				json.setSuccess(flag);
				return json;
		    }
}
