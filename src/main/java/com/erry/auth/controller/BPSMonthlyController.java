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

import com.erry.auth.model.BPSMonthly;
import com.erry.auth.model.Json;
import com.erry.auth.service.BPSMonthlyService;
import com.erry.util.EncryptDecryptUtil;


@Controller("bpsmonthlycontrol")
public class BPSMonthlyController {
	Logger log = Logger.getLogger(BPSMonthlyController.class);
	@Autowired
	private BPSMonthlyService bpsmonthlyservice;
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<BPSMonthly>  readcustDTOs(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="period",required=false)String period,
											@RequestParam(value="creator",required=false)String creator,
											@RequestParam(value="creatordate",required=false)String creatordate){
		//获取分页后Map集合
				Map<Integer,List<BPSMonthly>> pageMap = bpsmonthlyservice.findLists(request.getPage()-1, request.getLimit(),period,creator,creatordate);
				Integer total = null;
				List<BPSMonthly> list = new ArrayList<BPSMonthly>();
				for(Map.Entry<Integer,List<BPSMonthly>> entry : pageMap.entrySet()){
					total = entry.getKey();
					list = entry.getValue();
				}
//				try {
//					System.out.println("密码"+EncryptDecryptUtil.detryptByAES("l3vHMSOODuafwYUVE9IBdA==")+"密码");
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				List<BPSMonthly> custRegDTOList = bpsmonthlyservice.converToList(list);
				ExtDirectStoreReadResult<BPSMonthly> slDTO = new ExtDirectStoreReadResult<BPSMonthly>(total,custRegDTOList);
				return slDTO;
	}
	
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json saveOrUpdateMonth(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="period",required=false) String period,
			@RequestParam(value="active",required=false) String active
			){
				
				Json json = new Json();
				json=bpsmonthlyservice.saveOrUpdateMonth(period, active);
				return json;
	  }	
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<BPSMonthly>  readList(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="period",required=false)String period){
		//获取分页后Map集合
		List<BPSMonthly> pageMap = bpsmonthlyservice.findLists(period);
		ExtDirectStoreReadResult<BPSMonthly> slDTO = new ExtDirectStoreReadResult<BPSMonthly>(pageMap);
		return slDTO;
	}
	
	
}
