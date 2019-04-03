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
import ch.ralscha.extdirectspring.bean.ExtDirectRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.erry.auth.dto.PersonDTO;
import com.erry.auth.model.Json;
import com.erry.auth.model.PersonView;
import com.erry.auth.repository.PersonRepository;
import com.erry.auth.service.PersonServiceImpl;


@Controller("personControl")
public class PersonController {

	@Autowired
	private PersonServiceImpl personServiceImpl;
	@Autowired
	private PersonRepository personRepository;
	
	Logger log = Logger.getLogger(PersonController.class);

	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<PersonView>  getAll(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="fempgroup",required=false)String fempgroup,
											@RequestParam(value="name",required=false)String name){
		List<PersonView> pageInfo =personServiceImpl.findList(fempgroup,name);
		ExtDirectStoreReadResult<PersonView> slDTO = new ExtDirectStoreReadResult<PersonView>(pageInfo);
		return slDTO;
	}

	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<PersonDTO>  getAlls(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="fempgroup",required=false)String fempgroup,
											@RequestParam(value="name",required=false)String name){
		Map<Integer, List<PersonView>> pageInfo =personServiceImpl.findLists(request.getPage()-1, request.getLimit(),fempgroup,name);
		List<PersonView> list = new ArrayList<PersonView>();
		Integer total=0;
		for(Map.Entry<Integer,List<PersonView>> entry : pageInfo.entrySet()){
			total = entry.getKey();
			list = entry.getValue();
		}
		List<PersonDTO> dto = personServiceImpl.readForDTO(list);
		ExtDirectStoreReadResult<PersonDTO> slDTO = new ExtDirectStoreReadResult<PersonDTO>(total,dto);
		return slDTO;
	}
//	var param = {id:id,per_id:per_id,per_name:per_name,departmentid:departmentid,
//			departmentname:departmentname,empid:empid,mangerid:mangerid,account:account,
//			fhiredate:fhiredate,fempgroup:fempgroup,active:active}; 
	
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json updateOrsave(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="id",required=false) String id,
			@RequestParam(value="per_id",required=false) String per_id,
			@RequestParam(value="per_name",required=false) String per_name,
			@RequestParam(value="departmentid",required=false) String departmentid,
			@RequestParam(value="departmentname",required=false) String departmentname,
			@RequestParam(value="empid",required=false) String empid,
			@RequestParam(value="mangerid",required=false) String mangerid,
			@RequestParam(value="account",required=false) String account,
			@RequestParam(value="fhiredate",required=false) String fhiredate,
			@RequestParam(value="fempgroup",required=false) String fempgroup,
			@RequestParam(value="active",required=false) String active,
			@RequestParam(value="peremail",required=false) String peremail,
			@RequestParam(value="mangername",required=false) String mangername
			){
		
				PersonView person=new PersonView();
				String msg=null;
				Boolean flag=false;
				int count=0;
				count=personRepository.findList(id,per_id,empid);
				if(count>0){
					flag=false;
					msg="per_id 和 empid 已存在,请重新输入";
				}else{
					person=personServiceImpl.updateOrsave(id,per_id,per_name,departmentid,departmentname,empid,mangerid,account,fhiredate,fempgroup,active,peremail,mangername);
					if(person.getId()!=null){
						flag=true;
						msg="操作成功";
						
					}else{
						msg="操作失败";
					}
				}
				
				
				Json json = new Json();
				json.setData(person);
				json.setMsg(msg);
				json.setSuccess(flag);
				return json;
		    }
	
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public String DeletePerson(ExtDirectStoreReadRequest request,
			@RequestParam(value="id",required=false) String id){
		return personServiceImpl.delPerson(id);
	}
}
