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

import com.erry.auth.dto.ContactDTO;
import com.erry.auth.model.Contact;
import com.erry.auth.model.Json;
import com.erry.auth.repository.ContactRepository;
import com.erry.auth.service.ContactServiceImpl;


@Controller("contactControl")
public class ContactController {
	

	@Autowired
	private ContactServiceImpl contactService;
	
	@Autowired
	private ContactRepository contactRepository;

	Logger log = Logger.getLogger(ContactController.class);

	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<ContactDTO>  readcontactDTO(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="cpid",required=false)String cpid,
											@RequestParam(value="creator",required=false)String creator){
		Page<Contact> pageInfo =contactService.findList(request.getPage()-1, request.getLimit(),cpid,creator);
		List<ContactDTO> list = contactService.converTocustomerDTOList(pageInfo.getContent());
		ExtDirectStoreReadResult<ContactDTO> slDTO = new ExtDirectStoreReadResult<ContactDTO>(pageInfo.getTotalElements(),list);
		return slDTO;
	}
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json deleteAdd(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="id",required=false) String id
			){
		
				Contact add=new Contact();
				String msg=null;
				Boolean flag=false;
				add=contactService.deleteAdd(contactRepository.findById(id),"0");
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
			@RequestParam(value="record",required=false) Contact cust,
			@RequestParam(value="cpid",required=false) String cpid
			){
		
				Contact add=new Contact();
				String msg=null;
				Boolean flag=false;
				add=contactService.saveContact(cust, cpid);
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
