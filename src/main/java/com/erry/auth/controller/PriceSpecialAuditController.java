package com.erry.auth.controller;

import java.util.List;

import com.erry.auth.service.PriceSpecialServiceImpl;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;

import com.erry.auth.model.Json;
import com.erry.auth.model.PriceSpecialAudit;
import com.erry.auth.service.MailService;
import com.erry.auth.service.PriceSpecialAuditServiceImpl;
import com.erry.auth.service.PriceSpecialDetailServiceImpl;

@Controller("pricespecialauditcontroller")
public class PriceSpecialAuditController {

	@Autowired
	private PriceSpecialAuditServiceImpl priceSpecialAuditServiceImpl;
	
	@Autowired
	private PriceSpecialDetailServiceImpl priceSpecialDetailServiceImpl;

	@Autowired
	private PriceSpecialServiceImpl priceSpecialService;
	
	@Autowired
	private MailService mailService;
	//特价审批
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json ThroughSpecial(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="record",required=false) PriceSpecialAudit audit,
			@RequestParam(value="isnotrebate",required=false) String isnotrebate,
			@RequestParam(value="istax",required=false) String istax,
			@RequestParam(value="materialid",required=false) String materialid,
			@RequestParam(value="value",required=false) String value
			)
			{
				String flag=null;
				Boolean result=false;
				PriceSpecialAudit price=null;
				List<PriceSpecialAudit> list = priceSpecialAuditServiceImpl.findByDate(audit.getDid(),audit.getActivedate(),audit.getSpid());
				if(list.size()<=0){//该用户该物料 这个生效日期之后是否有生效的特价
					price=priceSpecialAuditServiceImpl.creatAudit(audit,value);
					priceSpecialDetailServiceImpl.updatePricespecialDetail(audit.getDid(), isnotrebate,audit.getCurrency(),materialid);
					priceSpecialService.updatePriceIsTax(price.getSpid(),istax);
				}
				if(price!=null&&price.getId()!=null){
					flag="操作成功";	
					if(price.getPriceSpecial()!=null && price.getPriceSpecialDetail()!=null){
						if(price.getPriceSpecial().getStatus().equals("1")&&price.getPriceSpecial().getBillstatus().equals("2")){
							 mailService.createPriceMail(price,"1");//审批通过邮件
						}else if(price.getPriceSpecial().getStatus().equals("2")&&price.getPriceSpecial().getBillstatus().equals("3")){
							 mailService.createPriceMail(price,"2");//审批驳回邮件
						}
					}
					result=true;
				}else{
					flag="审批失败！生效日期必须大于上次特价生效时间";
				}
		
				Json json = new Json();
				json.setData(price);
				json.setMsg(flag);
				json.setSuccess(result);
				return json;
			
		}
	//特价修改
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json UpdateSpecial(
			@RequestParam(value="istax",required=false) String istax,
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="isnotrebate",required=false) String isnotrebate,
			@RequestParam(value="materialid",required=false) String materialid,
			@RequestParam(value="record",required=false) PriceSpecialAudit audit
			)
			{
				String flag=null;
				Boolean result=false;
				PriceSpecialAudit price=null;
				List<PriceSpecialAudit> list = priceSpecialAuditServiceImpl.findByDate(audit.getDid(),audit.getActivedate(),audit.getSpid());
				if(list.size()<=0){
					priceSpecialDetailServiceImpl.updatePricespecialDetail(audit.getDid(), isnotrebate,audit.getCurrency(),materialid);
					price = priceSpecialAuditServiceImpl.updataPriceAudit(audit);
					priceSpecialService.updatePriceIsTax(price.getSpid(),istax);
				}
				if(price!=null){
					flag="操作成功";	
					result=true;
				}else{
					flag="操作失败!生效日期必须大于上次特价生效时间";	
				}
				Json json = new Json();
				json.setData(price);
				json.setMsg(flag);
				json.setSuccess(result);
				return json;
			
		}
	
}
