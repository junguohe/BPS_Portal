package com.erry.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.erry.auth.dto.RegionInfoDTO;
import com.erry.auth.model.Json;
import com.erry.auth.model.RegionInfo;
import com.erry.auth.service.RegionInfoServiceImpl;

@Controller("regioninfocontrol")
public class RegionInfoController {
	@Autowired
	private RegionInfoServiceImpl regionInfoServiceImpl;
	

	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<RegionInfo>  findpro(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="city",required=false)String city ){
		List<RegionInfo> pageInfo =regionInfoServiceImpl.findList(city);
		ExtDirectStoreReadResult<RegionInfo> slDTO = new ExtDirectStoreReadResult<RegionInfo>(pageInfo);
		return slDTO;
	}
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<RegionInfo> findcity(ExtDirectStoreReadRequest request,@RequestParam(value="name",required=false)String name ){
		ExtDirectStoreReadResult<RegionInfo> st = new ExtDirectStoreReadResult<RegionInfo>(regionInfoServiceImpl.findRegionInfoByCity(name));
		return st;
	}
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<RegionInfoDTO>  readerRrgion(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="province",required=false)String province ,
											@RequestParam(value="city",required=false)String city ){
		List<RegionInfoDTO> pageInfo =regionInfoServiceImpl.findRegionForUpdate(province,city);
		ExtDirectStoreReadResult<RegionInfoDTO> slDTO = new ExtDirectStoreReadResult<RegionInfoDTO>(pageInfo);
		return slDTO;
	}
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<RegionInfo>  findByCity(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="province",required=false) String province,
											@RequestParam(value="city",required=false) String city ){
		List<RegionInfo> pageInfo =regionInfoServiceImpl.findByCity(province,city);
		ExtDirectStoreReadResult<RegionInfo> slDTO = new ExtDirectStoreReadResult<RegionInfo>(pageInfo);
		return slDTO;
	}
	//新增省
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json saveProvince(ExtDirectStoreReadRequest request ,
			@RequestParam(value="province",required=false) String province
			){
		return regionInfoServiceImpl.saveProvince(province);
	}
	
	//新增市
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json saveCity(ExtDirectStoreReadRequest request ,
			@RequestParam(value="city",required=false) String city,
			@RequestParam(value="provinceid",required=false) String provinceid
			){
		return regionInfoServiceImpl.saveCity(city, provinceid);
	}
	
	//修改省市
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json updateOrSaveRegion(ExtDirectStoreReadRequest request ,
			@RequestParam(value="id",required=false) String id,
			@RequestParam(value="city",required=false) String city,
			@RequestParam(value="province",required=false) String province,
			@RequestParam(value="provinceid",required=false) String provinceid
			){
		return regionInfoServiceImpl.updateOrsaveCity(id, provinceid, city, province);
	}
}
