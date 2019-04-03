package com.erry.auth.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.erry.auth.dto.PriceDiffDTo;
import com.erry.auth.dto.PriceDowLoad;
import com.erry.auth.dto.PriceStrategyDTO;
import com.erry.auth.dto.PriceStrategyDetailDTO;
import com.erry.auth.model.DealerUploadReSale;
import com.erry.auth.model.Json;
import com.erry.auth.model.PriceStrategy;
import com.erry.auth.model.PriceStrategyDetail;
import com.erry.auth.model.UploadInventory;
import com.erry.auth.service.PriceStrategyDetailServiceImpl;
import com.erry.auth.service.PriceStrategyServiceImpl;
import com.erry.auth.service.UploadInventoryService;
import com.erry.util.DownLoadFileUtil;

@Controller("uploadinventorycontroller")
public class UploadInventoryController {

	@Autowired
	private UploadInventoryService uploadInventoryServiceImpl;

	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<UploadInventory>  findList(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="tid",required=false)String tid){
		//获取分页后Map集合
				Map<Integer,List<UploadInventory>> pageMap = uploadInventoryServiceImpl.findlist(request.getPage()-1, request.getLimit(),tid);
				Integer total = null;
				List<UploadInventory> custRegList = new ArrayList<UploadInventory>();
				for(Map.Entry<Integer,List<UploadInventory>> entry : pageMap.entrySet()){
					total = entry.getKey();
					custRegList = entry.getValue();
				}
				ExtDirectStoreReadResult<UploadInventory> slDTO = new ExtDirectStoreReadResult<UploadInventory>(total,custRegList);
				return slDTO;
	}
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json SaveOrCommit(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="inventory",required=false) List<UploadInventory> inventory,
			@RequestParam(value="value",required=false) String value
			){
		
				String msg=null;
				Boolean flag=false;
				List<UploadInventory> list=new ArrayList<UploadInventory>();
				if(inventory.size()>0){
					  for (int i = 0; i < inventory.size(); i++)
			         {
						  UploadInventory uploadinventory=uploadInventoryServiceImpl.SaveOrCommit(inventory.get(i), value);
						  list.add(uploadinventory);
			         } 
				  }
				if(list.size()>0){
					flag=true;
					msg="操作成功";
				}else{
					msg="操作失败";
				}
				
				Json json = new Json();
				json.setData(list);
				json.setMsg(msg);
				json.setSuccess(flag);
				return json;
		    }
}
