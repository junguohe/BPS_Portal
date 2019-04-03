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

import com.erry.auth.dto.CustRegDTO;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.DealarV;
import com.erry.auth.model.Dictionary;
import com.erry.auth.model.Person;
import com.erry.auth.model.PersonView;
import com.erry.auth.model.UploadInventoryAdjustment;
import com.erry.auth.service.DealarVServiceImpl;
import com.erry.auth.service.PersonServiceImpl;


@Controller("dealarvcontroller")
public class DealarVController {

	@Autowired
	private DealarVServiceImpl dealarvserviceimpl;

	Logger log = Logger.getLogger(DealarVController.class);

//	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
//	public ExtDirectStoreReadResult<DealarV>  getAll(
//											ExtDirectStoreReadRequest request){
//		List<DealarV> pageInfo =dealarvserviceimpl.findList();
//		ExtDirectStoreReadResult<DealarV> slDTO = new ExtDirectStoreReadResult<DealarV>(pageInfo);
//		return slDTO;
//	}
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<DealarV>  getAll(){
		Map<Integer,List<DealarV>> pageMap = dealarvserviceimpl.findList();
		Integer total = null;
		List<DealarV> custRegList = new ArrayList<DealarV>();
		for(Map.Entry<Integer,List<DealarV>> entry : pageMap.entrySet()){
			total = entry.getKey();
			custRegList = entry.getValue();
		}
		ExtDirectStoreReadResult<DealarV> slDTO = new ExtDirectStoreReadResult<DealarV>(total,custRegList);
		return slDTO;
	}

	

	
	
	
}
