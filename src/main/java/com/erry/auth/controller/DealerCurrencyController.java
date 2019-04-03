package com.erry.auth.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;
import com.erry.auth.model.Dictionary;
import com.erry.auth.model.Json;
import com.erry.auth.service.DealerCurrencyServiceImpl;

@Controller("dealercurrencycontrol")
public class DealerCurrencyController {
	@Autowired
	private DealerCurrencyServiceImpl dealercurrencyserviceimpl;
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<Dictionary>  findList(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="dealerid",required=false)String dealerid){
		List<Dictionary> pageInfo =dealercurrencyserviceimpl.findList(dealerid);
		ExtDirectStoreReadResult<Dictionary> slDTO = new ExtDirectStoreReadResult<Dictionary>(pageInfo);
		return slDTO;
	}
	
	
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json SaveOrUpdate(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="dealerid",required=false) String  dealerid,
			@RequestParam(value="currencyids",required=false) List<String> currencyids
			)
			{
				String msg=null;
				Boolean result=true;
				dealercurrencyserviceimpl.SaveOrUpdate(dealerid,currencyids);
				Json json = new Json();
				json.setData(null);
				json.setMsg("操作成功！");
				json.setSuccess(result);
				return json;
		}
	
}
