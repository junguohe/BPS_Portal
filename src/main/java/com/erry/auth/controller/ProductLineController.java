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

import com.erry.auth.dto.CustomerDTO;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.Customer;
import com.erry.auth.model.DealarV;
import com.erry.auth.model.Dictionary;
import com.erry.auth.model.Json;
import com.erry.auth.model.ProdLine;
import com.erry.auth.service.ProductLineServiceImpl;

@Controller("productlinecontroller")
public class ProductLineController {
	Logger log = Logger.getLogger(ProductLineController.class);
	@Autowired
	private ProductLineServiceImpl impl;
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<ProdLine> getAllProdLine(){
		ExtDirectStoreReadResult<ProdLine> readResult = new ExtDirectStoreReadResult<ProdLine>(impl.findAll());
		return readResult;
	}
	
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<ProdLine>  findByName(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="name",required=false)String name,
											@RequestParam(value="code",required=false)String code){
		//获取分页后Map集合
				Map<Integer,List<ProdLine>> pageMap = impl.findList(request.getPage()-1, request.getLimit(),name,code);
				Integer total = null;
				List<ProdLine> list = new ArrayList<ProdLine>();
				for(Map.Entry<Integer,List<ProdLine>> entry : pageMap.entrySet()){
					total = entry.getKey();
					list = entry.getValue();
				}
				ExtDirectStoreReadResult<ProdLine> slDTO = new ExtDirectStoreReadResult<ProdLine>(total,list);
				return slDTO;
	}
	
	
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json updateOrsave(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="prodid",required=false) String prodid,
			@RequestParam(value="prodname",required=false) String prodname,
			@RequestParam(value="prodcode",required=false) String prodcode,
			@RequestParam(value="remark",required=false) String remark,
			@RequestParam(value="active",required=false) String active
			){
		
				ProdLine prodline=new ProdLine();
				String msg=null;
				Boolean flag=false;
				prodline=impl.updateOrsave(prodid,prodname,prodcode,remark,active);
				if(prodline.getProdid()!=null){
					flag=true;
					msg="操作成功";
					
				}else{
					msg="操作失败";
				}
				
				Json json = new Json();
				json.setData(prodline);
				json.setMsg(msg);
				json.setSuccess(flag);
				return json;
		    }
	
	
}
