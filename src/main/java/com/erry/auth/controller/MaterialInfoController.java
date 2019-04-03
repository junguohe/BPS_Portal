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

import com.erry.auth.dto.MaterialInfoDTO;
import com.erry.auth.model.MaterialInfo;
import com.erry.auth.service.MaterialInfoServiceImpl;

@Controller("materialInfoControl")
public class MaterialInfoController {
	@Autowired
	private MaterialInfoServiceImpl impl;
	
	Logger log = Logger.getLogger(MaterialInfoController.class);

	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<MaterialInfoDTO> getAllList(ExtDirectStoreReadRequest request,
			@RequestParam(value="materialname",required=false )String materialname){
		 List<MaterialInfoDTO> list = impl.findAll(materialname);
		ExtDirectStoreReadResult<MaterialInfoDTO> readResult = new ExtDirectStoreReadResult<MaterialInfoDTO>(list);
		return readResult;
	}
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<MaterialInfoDTO> getListByActive(ExtDirectStoreReadRequest request,
			@RequestParam(value="materialname",required=false )String materialname){
		 List<MaterialInfoDTO> list = impl.findAllByActive(materialname);
		ExtDirectStoreReadResult<MaterialInfoDTO> readResult = new ExtDirectStoreReadResult<MaterialInfoDTO>(list);
		return readResult;
	}
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<MaterialInfoDTO> getAllLists(ExtDirectStoreReadRequest request,
			@RequestParam(value="materialname",required=false )String materialname){
		 List<MaterialInfoDTO> list = impl.findAlls(materialname);
		ExtDirectStoreReadResult<MaterialInfoDTO> readResult = new ExtDirectStoreReadResult<MaterialInfoDTO>(list);
		return readResult;
	}
	
}
