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

import com.erry.auth.dto.PriceSpecialAuditDTO2;
import com.erry.auth.model.Json;
import com.erry.auth.model.PriceSpecial;
import com.erry.auth.model.PriceSpecialAudit;
import com.erry.auth.model.PriceSpecialDetail;
import com.erry.auth.service.PriceSpecialServiceImpl;

@Controller("priceSpecialControl")
public class PriceSpecialController {
	Logger log = Logger.getLogger(PriceSpecialController.class);
	@Autowired
	private PriceSpecialServiceImpl priceSpecialServiceImpl;
	
	//审批查询
		@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
		public ExtDirectStoreReadResult<PriceSpecialAuditDTO2>  findList(
												ExtDirectStoreReadRequest request ,
												@RequestParam(value="dealername",required=false)String dealername,
												@RequestParam(value="billstatus",required=false)String billstatus,
												@RequestParam(value="custcode",required=false)String custcode,
												@RequestParam(value="custname",required=false)String custname,
												@RequestParam(value="billno",required=false)String billno,
												@RequestParam(value="materialname",required=false)String materialname,
												@RequestParam(value="startdate",required=false)String startdate,
												@RequestParam(value="status",required=false)String status,
												@RequestParam(value="region",required=false)String region,
												@RequestParam(value="enddate",required=false)String enddate){
			//获取分页后Map集合
					Map<Integer,List<PriceSpecialAudit>> pageMap = priceSpecialServiceImpl.findList(request.getPage()-1, request.getLimit()
							,dealername,billstatus,custcode,custname,billno,materialname,startdate,enddate,status,region);
					Integer total = null;
					List<PriceSpecialAudit> pricespecial = new ArrayList<PriceSpecialAudit>();
					for(Map.Entry<Integer,List<PriceSpecialAudit>> entry : pageMap.entrySet()){
						total = entry.getKey();
						pricespecial = entry.getValue();
					}
					List<PriceSpecialAuditDTO2> list = priceSpecialServiceImpl.converToDTOList2(pricespecial);
					ExtDirectStoreReadResult<PriceSpecialAuditDTO2> slDTO = new ExtDirectStoreReadResult<PriceSpecialAuditDTO2>(total,list);
					return slDTO;
		}
		

	
	
	
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json createPriceSpecial(ExtDirectStoreReadRequest request,
			@RequestParam(value="record",required=false) PriceSpecial priceSpecials,
			@RequestParam(value="pricespecialdetail",required=false)List<PriceSpecialDetail> priceSpecialsDeatils)
	{
		String result="";
		Boolean flag=false;
		PriceSpecial p = priceSpecialServiceImpl.savePriceSpecial(priceSpecials, priceSpecialsDeatils);
		if(p==null){
			result="申请失败";
		}else{
			flag=true;
			result="申请成功";
			
		}
		Json json = new Json();
		json.setData(p);
		json.setMsg(result);
		json.setSuccess(flag);
		return json;
	}
	
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json updatePriceStatus(ExtDirectStoreReadRequest request,
			@RequestParam(value="spid",required=false) String spid )
	{
		String result="";
		Boolean flag=false;
		
		PriceSpecial p = priceSpecialServiceImpl.updatePrice(spid,"4");
		if(p==null){
			result="操作失败";
		}else{
			flag=true;
			result="操作成功";
			
		}
		Json json = new Json();
		json.setData(p);
		json.setMsg(result);
		json.setSuccess(flag);
		return json;
	}
	
	//特价失效
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json inActivePrice(ExtDirectStoreReadRequest request,
			@RequestParam(value="did",required=false) String did,
			@RequestParam(value="spid",required=false)String spid)
	{
		String result="";
		Boolean flag=false;
		Boolean p = priceSpecialServiceImpl.inActivePrice(did, spid);
		if(p==false){
			result="操作失败";
		}else{
			flag=true;
			result="操作成功";
			
		}
		Json json = new Json();
		json.setData(p);
		json.setMsg(result);
		json.setSuccess(flag);
		return json;
	}
	

}
