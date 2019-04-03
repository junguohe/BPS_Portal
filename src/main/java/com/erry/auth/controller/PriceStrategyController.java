package com.erry.auth.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.erry.auth.model.PriceStrategy;
import com.erry.auth.service.PriceStrategyServiceImpl;

@Controller("pricestrategycontrol")
public class PriceStrategyController {
	@Autowired
	private PriceStrategyServiceImpl priceStrategyServiceImpl;
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public  ExtDirectStoreReadResult<PriceStrategy> findPriceCodes(ExtDirectStoreReadRequest request){
		ExtDirectStoreReadResult<PriceStrategy> ps = new ExtDirectStoreReadResult<PriceStrategy>(priceStrategyServiceImpl.findPriceStrategyNo());
		return ps;
	}
}
