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

import com.erry.auth.model.Json;
import com.erry.auth.model.PersonView;
import com.erry.auth.model.ProdPerson;
import com.erry.auth.repository.ProdPersonRepository;
import com.erry.auth.service.ProdPersonServiceImpl;


@Controller("prodpersonControl")
public class ProdPersonController {

	@Autowired
	private ProdPersonServiceImpl prodpersonserviceimpl;
	@Autowired
	private ProdPersonRepository prodpersonrepository;
	Logger log = Logger.getLogger(ProdPersonController.class);

	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<ProdPerson>  getAlls(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="area",required=false)String area,
											@RequestParam(value="mgr",required=false)String mgr){
		List<ProdPerson> pageInfo =prodpersonserviceimpl.findList(area,mgr);
		ExtDirectStoreReadResult<ProdPerson> slDTO = new ExtDirectStoreReadResult<ProdPerson>(pageInfo);
		return slDTO;
	}


	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<ProdPerson>  getAll(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="area",required=false)String area,
											@RequestParam(value="mgr",required=false)String mgr){
		Map<Integer, List<ProdPerson>> pageInfo =prodpersonserviceimpl.findLists(request.getPage()-1, request.getLimit(),area,mgr);
		List<ProdPerson> list = new ArrayList<ProdPerson>();
		Integer total=0;
		for(Map.Entry<Integer,List<ProdPerson>> entry : pageInfo.entrySet()){
			total = entry.getKey();
			list = entry.getValue();
		}
		ExtDirectStoreReadResult<ProdPerson> slDTO = new ExtDirectStoreReadResult<ProdPerson>(total,list);
		return slDTO;
	}
//	String id, String area_id, String area,
//	String mgr_id, String mgr, String fae_id,
//	String faemgr
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json updateOrsave(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="id",required=false) String id,
			@RequestParam(value="area_id",required=false) String area_id,
			@RequestParam(value="area",required=false) String area,
			@RequestParam(value="mgr_id",required=false) String mgr_id,
			@RequestParam(value="mgr",required=false) String mgr,
			@RequestParam(value="fae_id",required=false) String fae_id,
			@RequestParam(value="faemgr",required=false) String faemgr,
			@RequestParam(value="area_directory_id",required=false) String area_directory_id
			){
		
			    ProdPerson person=new ProdPerson();
				String msg=null;
				Boolean flag=false;
				int count=0;
				count=prodpersonrepository.findList(id,area_id);
				if(count>0){
					flag=false;
					msg="area_id 已存在,请重新输入";
				}else{
					person=prodpersonserviceimpl.updateOrsave(id,area_id,area,mgr_id,mgr,fae_id,faemgr,area_directory_id);
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
	public String DeleteProdPerson(ExtDirectStoreReadRequest request,
			@RequestParam(value="id",required=false) String id){
		return prodpersonserviceimpl.delPerson(id);
	}
	
	
}
