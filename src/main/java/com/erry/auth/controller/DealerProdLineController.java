package com.erry.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.erry.auth.dto.DealerRegNumDTO;
import com.erry.auth.model.DealerProdLine;
import com.erry.auth.model.Dictionary;
import com.erry.auth.model.Json;
import com.erry.auth.model.ProdLine;
import com.erry.auth.service.DealerCurrencyServiceImpl;
import com.erry.auth.service.DealerProdLineServiceImpl;

@Controller("dealerprodlinecontrol")
public class DealerProdLineController {
	@Autowired
	private DealerProdLineServiceImpl dealerprodlineserviceimpl;
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<ProdLine>  findList(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="dealerid",required=false)String dealerid){
		List<ProdLine> pageInfo =dealerprodlineserviceimpl.findList(dealerid);
		ExtDirectStoreReadResult<ProdLine> slDTO = new ExtDirectStoreReadResult<ProdLine>(pageInfo);
		return slDTO;
	}
	//修改产品线
	
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json SaveOrUpdate(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="dealerid",required=false) String  dealerid,
			@RequestParam(value="prodids",required=false) List<String> ProdLine
			)
			{
				String msg=null;
				Boolean result=true;
				dealerprodlineserviceimpl.SaveOrUpdate(dealerid,ProdLine);
				Json json = new Json();
				json.setData(null);
				json.setMsg("操作成功！");
				json.setSuccess(result);
				return json;
		}
	//修改报备数
	
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json SaveOrUpdateRegNum(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="DealerReg",required=false) List<DealerProdLine> dealerprodline
			)
			{
				String msg=null;
				Boolean result=true;
				dealerprodlineserviceimpl.SaveOrUpdateRegNum(dealerprodline);
				Json json = new Json();
				json.setData(null);
				json.setMsg("操作成功！");
				json.setSuccess(result);
				return json;
		}
	
		@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
		public ExtDirectStoreReadResult<DealerRegNumDTO>  findDealerRegList(
												ExtDirectStoreReadRequest request ,
												@RequestParam(value="dealerid",required=false)String dealerid){
			List<DealerRegNumDTO> pageInfo =dealerprodlineserviceimpl.findDealerRegList(dealerid);
			ExtDirectStoreReadResult<DealerRegNumDTO> slDTO = new ExtDirectStoreReadResult<DealerRegNumDTO>(pageInfo);
			return slDTO;
		}
}
