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

import com.erry.auth.dto.CustRegDTO;
import com.erry.auth.model.AuUser;
import com.erry.auth.model.Contact;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustBPS;
import com.erry.auth.model.CustProdLine;
import com.erry.auth.model.CustProjectInfo;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.Customer;
import com.erry.auth.model.Json;
import com.erry.auth.repository.AuUserRepository;
import com.erry.auth.repository.CustProdLineRepository;
import com.erry.auth.repository.CustRegRepository;
import com.erry.auth.repository.CustomerRepository;
import com.erry.auth.service.CustProdLineServiceImpl;
import com.erry.auth.service.CustRegServiceImpl;
import com.erry.auth.service.CustomerServiceImpl;
import com.erry.auth.service.MailService;

@Controller("custRegController")
public class CustRegController {

	@Autowired
	private CustRegServiceImpl custRegService;
	@Autowired
	private CustRegRepository custRegRepository;
	@Autowired
	private CustomerServiceImpl customerServiceImpl;
	
	@Autowired
	private CustProdLineServiceImpl custProdLineServiceImpl;
	@Autowired
	private CustProdLineRepository custProdLineRepository;
	
	@Autowired
	private AuUserRepository auUserRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private MailService mailService;
	
	Logger log = Logger.getLogger(CustRegController.class);

	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<CustRegDTO>  findList(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="name",required=false)String name,
											@RequestParam(value="prodid",required=false)String prodid,
											@RequestParam(value="approveresult",required=false)String approveresult,
											@RequestParam(value="dealername",required=false)String dealername,
											@RequestParam(value="startdate",required=false)String startdate,
											@RequestParam(value="enddate",required=false)String enddate,
											@RequestParam(value="regstatus",required=false)String regstatus){
		//获取分页后Map集合
				Map<Integer,List<CustReg>> pageMap = custRegService.findCustRegList(request.getPage()-1, request.getLimit()
						,name,prodid,approveresult,dealername,startdate,enddate,regstatus);
				Integer total = null;
				List<CustReg> custRegList = new ArrayList<CustReg>();
				for(Map.Entry<Integer,List<CustReg>> entry : pageMap.entrySet()){
					total = entry.getKey();
					custRegList = entry.getValue();
				}
				List<CustRegDTO> custRegDTOList = custRegService.converTocustRegDTOList(custRegList);
				ExtDirectStoreReadResult<CustRegDTO> slDTO = new ExtDirectStoreReadResult<CustRegDTO>(total,custRegDTOList);
				return slDTO;
	}
	//冲突查询
	//1
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<CustRegDTO>  readcustDTOs(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="custname",required=false)String custname,
			@RequestParam(value="taxno",required=false)String taxno,
			@RequestParam(value="id",required=false)String id){
		//获取分页后Map集合
				Map<Integer,List<CustReg>> pageMap = custRegService.findCustLists(request.getPage()-1, request.getLimit().intValue(),custname,taxno,id);
				Integer total = null;
				List<CustReg> custRegList = new ArrayList<CustReg>();
				for(Map.Entry<Integer,List<CustReg>> entry : pageMap.entrySet()){
					total = entry.getKey();
					custRegList = entry.getValue();
				}
				List<CustRegDTO> custRegDTOList = custRegService.converTocustRegDTOList(custRegList);
				ExtDirectStoreReadResult<CustRegDTO> slDTO = new ExtDirectStoreReadResult<CustRegDTO>(total,custRegDTOList);
				return slDTO;
	}
	
	
	//直接报备  不新增客户
	//1
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json createCustReg(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="custid",required=false) String custid,
			@RequestParam(value="prodid",required=false) String prodid,
			@RequestParam(value="address",required=false) List<CustAddress> address,
			@RequestParam(value="contact",required=false)List<Contact> contact,
			@RequestParam(value="project",required=false)List<CustProjectInfo> project,
			@RequestParam(value="custbps",required=false)  List<CustBPS> custbps
			
		){
		String msg = "报备失败";
		Boolean result = false;
		Customer cust = null;
		
		//判断是否存在有cpid
		CustProdLine prodline=custProdLineRepository.selectProdLine(prodid,custid);
		if(prodline!=null&&prodline.getCpid()!=null){
			CustReg reg = null;
			CustReg reg2=custRegRepository.finByBPSCpidBo(prodline.getCpid(),"1");
			if(reg2!=null){
				reg=custRegService.updateCustReg(reg2);
			}else{
				reg=custRegService.creatCustReg(prodline.getCpid());
			}
			//是否添加成功
			if(reg!=null &&reg.getId()!=null){
				//添加详细信息
				customerServiceImpl.creatDetail(prodline.getCpid(),address,contact, project,custbps);
				 msg = "报备成功";
				 result = true;
			}
		}else{
			prodline=custProdLineServiceImpl.saveCustProdLine(prodid, custid);
			if(prodline!=null&&prodline.getCpid()!=null){
				CustReg reg=custRegService.creatCustReg(prodline.getCpid());
				//是否添加成功
				if(reg!=null &&reg.getId()!=null){
					//添加详细信息
					customerServiceImpl.creatDetail(prodline.getCpid(),address,contact, project,custbps);
					 msg = "报备成功";
					 result = true;
				}
			}
		}
		Json json = new Json();
		json.setData(cust);
		json.setMsg(msg);
		json.setSuccess(result);
		return json;

	}
	
	//审批通过 同时驳回其他的相同的cpid
	//1
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json updateCustreg(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="reg",required=false) List<String> regid,
			@RequestParam(value="cpid",required=false) List<String> cpid,
			@RequestParam(value="custid",required=false) List<String> custid,
			@RequestParam(value="custname",required=false) List<String> custname,
			@RequestParam(value="prodid",required=false) List<String> prodid
			//@RequestParam(value="remark",required=false)  String  remark
			)
				{
					List<String> names = new ArrayList<String>();
					int count = 0;
					String flag=null;
					Boolean result=false;
					List<CustReg> cust=new ArrayList<CustReg>();
					if(regid.size()>0){
						 for (int i = 0; i < regid.size(); i++)
		                  {
							 for(int k = 0; k < custid.size(); k++){
								 CustReg reg = custRegRepository.findCustRegs(cpid.get(i),regid.get(i));
								 if(reg!=null){
									 CustReg custs = custRegService.updateCustreg(reg,"1",cpid.get(i),custid.get(k));
									 cust.add(custs);
									 if(custs.getDealerid()!=null){
									 	 Customer customer = customerRepository.findByCustid(custid.get(k));
									 	 boolean en = customer.getRegion().equals("海外")?true:false;
										 mailService.createCustMail(custs.getDealerid(),custname.get(i),"1",custs.getApproveremark(),en);
									 }
								 }
							 }
							// 驳回
							 List<CustReg> custList=custRegRepository.findCustReg(cpid.get(i), regid.get(i));
							 if(custList.size()>0){
								 for(int g = 0; g < custList.size(); g++){
									 names.add(custList.get(g).getCreator());
									 count+=1;
									 custRegService.updateRegStatus(custList.get(g));
									 Customer customer = customerRepository.findByCustname(custname.get(i));
									 boolean en = customer.getRegion().equals("海外")?true:false;
									 if(custList.get(g).getDealerid()!=null){
										 mailService.createCustMail(custList.get(g).getDealerid(), custname.get(i),"2",custList.get(g).getApproveremark(),en);
									 }else{
										 mailService.createCustMail("", custname.get(i),"2",custList.get(g).getApproveremark(),en);
									 }
								 }
								 
							 }
								 
		                  }
					 }
					
					if(cust.size()>0){
						result=true;
						flag="审批成功!";
							if(count!=0&&names.size()>0){
								flag+="共有"+count+"人被驳回</br>";
								for(String cid: names){
									 AuUser user = auUserRepository.findByUid(cid);
									 flag+=user.getUsername()+"</br>";
								}
							}
							
					}else{
						flag="审批失败";
					}
						
					Json json = new Json();
					json.setData(cust);
					json.setMsg(flag);
					json.setSuccess(result);
					return json;
				
			}
	//审批驳回
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json updateCustregStatus(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="reg",required=false) List<String> id,
			@RequestParam(value="cpid",required=false) List<String> cpid,
			@RequestParam(value="remark",required=false) String remark
			
			)
				
				{
					String flag=null;
					Boolean result=false;
					CustReg cust=null;
					if(id.size()>0){
						 for (int i = 0; i < id.size(); i++){
							 for (int j = 0; j < cpid.size(); j++){
								 CustReg reg = custRegRepository.findCustRegs(cpid.get(j),id.get(i));
								 if(reg!=null){
									 cust = custRegService.updateCustregStatus(reg,remark);
									 Customer c = customerRepository.findCustBycpid(cpid.get(j));
									 boolean en = c.getRegion().equals("海外")?true:false;
									 if(cust.getDealerid()!=null){
										 mailService.createCustMail(cust.getDealerid(), c.getCustname(),"2",cust.getApproveremark(),en);
									 }else{
										 mailService.createCustMail("",c.getCustname(),"2",cust.getApproveremark(),en);
									 }
								 }
							 }
							 
		                  }
					 }
					if(cust==null){
						
						flag="操作失败";
					}else{
						result=true;
						flag="操作成功";
					}
						
					Json json = new Json();
					json.setData(cust);
					json.setMsg(flag);
					json.setSuccess(result);
					return json;
				
			}
	
	//待处理
		@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json updateCustre(
				ExtDirectStoreReadRequest request ,
				@RequestParam(value="id",required=false) List <String> id
				
				){
						String flag=null;
						Boolean result=false;
						
						List<CustReg> cust=new ArrayList<CustReg>();
						for(int i = 0; i < id.size(); i++){
							CustReg custs = custRegService.updateCustre(custRegRepository.findById(id.get(i)));
							cust.add(custs);
						}
						if(cust.size()<=0){
							
							flag="操作失败";
						}else{
							result=true;
							flag="操作成功";
						}
							
						Json json = new Json();
						json.setData(cust);
						json.setMsg(flag);
						json.setSuccess(result);
						return json;
					
				}
	
}
