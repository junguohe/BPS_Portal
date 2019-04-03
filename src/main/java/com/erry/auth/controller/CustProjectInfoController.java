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

import com.erry.auth.dto.CustProjectInfoDTO;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustProjectInfo;
import com.erry.auth.model.Json;
import com.erry.auth.repository.CustProjectInfoRepository;
import com.erry.auth.service.CustProjectInfoServiceImpl;

@Controller("custprojectinfoContro")
public class CustProjectInfoController {
	Logger log = Logger.getLogger(CustAddressController.class);
	@Autowired
	private CustProjectInfoServiceImpl impl;
	
	@Autowired
	private CustProjectInfoRepository custProjectInfoRepository;

	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<CustProjectInfoDTO> readDTO(
			ExtDirectStoreReadRequest request,
			@RequestParam(value = "cpid", required = false) String cpid,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "creator", required = false) String creator) {
		Page<CustProjectInfo> pageInfo = impl.fingList(request.getPage() - 1,
				(int)request.getLimit(), cpid,name,creator);
		List<CustProjectInfoDTO> custList = impl
				.converTocustomerDTOList(pageInfo.getContent());
		ExtDirectStoreReadResult<CustProjectInfoDTO> slDTO = new ExtDirectStoreReadResult<CustProjectInfoDTO>(
				pageInfo.getTotalElements(), custList);
		return slDTO;
	}
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<CustProjectInfo> findfortype(ExtDirectStoreReadRequest request,
			@RequestParam(value = "name", required = false) String name){
		ExtDirectStoreReadResult<CustProjectInfo> proj = new ExtDirectStoreReadResult<CustProjectInfo>(impl.findProjecttype(name));
		return proj;
	}
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json deleteAdd(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="id",required=false) String id
			){
		
				CustProjectInfo add=new CustProjectInfo();
				String msg=null;
				Boolean flag=false;
				add=impl.deleteAdd(custProjectInfoRepository.findById(id),"0");
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
			@RequestParam(value="record",required=false) CustProjectInfo cust,
			@RequestParam(value="cpid",required=false) String cpid
			){
		
				CustProjectInfo add=new CustProjectInfo();
				String msg=null;
				Boolean flag=false;
				add=impl.saveProject(cust, cpid);
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
