package com.erry.auth.controller;



import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.id.GUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.erry.auth.dto.CustAddressDTO;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.Json;
import com.erry.auth.repository.CustAddressRepository;
import com.erry.auth.service.CustAddressServiceImpl;


@Controller("custAddressControl")
public class CustAddressController {
	

	@Autowired
	private CustAddressServiceImpl custAddressService;
	
	@Autowired
	private CustAddressRepository custAddressRepository;

	Logger log = Logger.getLogger(CustAddressController.class);

	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<CustAddressDTO>  readcustDTO(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="cpid",required=false)String cpid,
											@RequestParam(value="creator",required=false)String creator){
		Page<CustAddress> pageInfo =custAddressService.findList(request.getPage()-1, request.getLimit(),cpid,creator);
		List<CustAddressDTO> custList = custAddressService.converTocustomerDTOList(pageInfo.getContent());
		
		ExtDirectStoreReadResult<CustAddressDTO> slDTO = new ExtDirectStoreReadResult<CustAddressDTO>(pageInfo.getTotalElements(),custList);
		return slDTO;
	}
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_MODIFY)
	public String createCustAddress(@Valid CustAddress c){
		GUIDGenerator guid = new GUIDGenerator();
		CustAddress cust = custAddressService.saveCustAddress(c);
		String msg= "";
		boolean flg=true; 
		if(cust==null){
			msg="保存失败";
			flg=false;
		}else{
			
			msg="保存成功";
		}
		return "[{msg:"+msg+",success:"+flg+",recodes:"+cust.getId()+"}]";
	}
	
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json deleteAdd(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="id",required=false) String id
			){
		
				CustAddress add=new CustAddress();
				String msg=null;
				Boolean flag=false;
				add=custAddressService.deleteAdd(custAddressRepository.findById(id),"0");
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
			@RequestParam(value="record",required=false) CustAddress cust,
			@RequestParam(value="cpid",required=false) String cpid
			){
		
				CustAddress add=new CustAddress();
				String msg=null;
				Boolean flag=false;
				add=custAddressService.saveAddress(cust, cpid);
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
