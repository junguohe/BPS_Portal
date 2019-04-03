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

import com.erry.auth.model.DealerUploadReSale;
import com.erry.auth.model.Json;
import com.erry.auth.repository.UserBPSRepository;
import com.erry.auth.service.UploadReSaleService;

@Controller("uploadresalecontrol")
public class UploadReSaleController {
	@Autowired
	private UploadReSaleService uploadReSaleService;
	@Autowired
	private UserBPSRepository userbpsrepository;
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<DealerUploadReSale> readerUploadResale(ExtDirectStoreReadRequest request,
			@RequestParam(value="tid",required=false)String tid,
			@RequestParam(value="period",required=false)String period,
			@RequestParam(value="taskseq",required=false)String taskseq){
		Map<Integer,List<DealerUploadReSale>> resultMap = uploadReSaleService.findDealerUploadReSale(request.getPage()-1, request.getLimit()
				, tid,period,taskseq);
		Integer total = null;
		List<DealerUploadReSale> list = new ArrayList<DealerUploadReSale>();
		for(Map.Entry<Integer, List<DealerUploadReSale>> entry:resultMap.entrySet()){
			total = entry.getKey();
			list = entry.getValue();
		}
		 ExtDirectStoreReadResult<DealerUploadReSale> uploadresaleLists=new ExtDirectStoreReadResult<DealerUploadReSale>(total,list);
		 return uploadresaleLists;
	}
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<DealerUploadReSale> readerUploadResales(ExtDirectStoreReadRequest request,
			@RequestParam(value="tid",required=false)String tid,
			@RequestParam(value="period",required=false)String period,
			@RequestParam(value="taskseq",required=false)String taskseq){
		Map<Integer,List<DealerUploadReSale>> resultMap = uploadReSaleService.readerUploadResales(request.getPage()-1, request.getLimit()
				, tid,period,taskseq);
		Integer total = null;
		List<DealerUploadReSale> list = new ArrayList<DealerUploadReSale>();
		for(Map.Entry<Integer, List<DealerUploadReSale>> entry:resultMap.entrySet()){
			total = entry.getKey();
			list = entry.getValue();
		}
		 ExtDirectStoreReadResult<DealerUploadReSale> uploadresaleLists=new ExtDirectStoreReadResult<DealerUploadReSale>(total,list);
		 return uploadresaleLists;
	}
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json SaveOrCommit(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="dealeruploadresale",required=false) List<DealerUploadReSale> dealeruploadresale,
			@RequestParam(value="value",required=false) String value
			){
				Json json = new Json();
				json.setMsg("确认成功！");
				try{
					for (DealerUploadReSale resale : dealeruploadresale){
						DealerUploadReSale uploadresale=uploadReSaleService.SaveOrCommit(resale, value);
					}
					json.setSuccess(true);
				}catch (Exception e){
					e.printStackTrace();
					json.setMsg("确认异常！请联系管理员");
					json.setSuccess(false);
				}
				return json;
		    }
	
	//下载模板
	@RequestMapping(value="uploadresalecontrol/importFileDownload")
	public void importFileDownload(HttpServletRequest request, HttpServletResponse response ) throws Exception {
		String fname = "销售数据";
		uploadReSaleService.importFileDownload(request,response,fname);
	}
	
	//上传数据
			@ExtDirectMethod(ExtDirectMethodType.FORM_POST)
			public ExtDirectFormPostResult uploadUploadReSale(HttpServletRequest request,
					@RequestParam("fileUpload") MultipartFile fileUpload,
					@RequestParam(value = "period", required = false) String periods,
					@RequestParam(value = "month", required = false) String month,
					@RequestParam(value = "year", required = false) String year)throws IOException, InvalidFormatException {
				 
				
				 String period=year+"-"+month;
				 String info = "success";
				 List<DealerUploadReSale> list= new ArrayList<DealerUploadReSale>();	
				 ExtDirectFormPostResult resp = new ExtDirectFormPostResult(true);
				 try{
					list = uploadReSaleService.readFile(fileUpload,period);//解析上传文件获取list
				 }catch(Exception e){
					 info = e.getMessage();
				 }
				 String tid = null;
				if(list.size()>0){
					tid=uploadReSaleService.SaveOrUpdate(list,period);	
					//调用存储过程
					uploadReSaleService.get9Id(tid);
				}
				
				resp.addResultProperty("list", list);
				resp.addResultProperty("info", info);
				resp.addResultProperty("tid", tid);
				return resp;
			}

			@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
			public Json SaveOrCommits(
					ExtDirectStoreReadRequest request ,
					@RequestParam(value="dealeruploadresale",required=false) List<DealerUploadReSale> dealeruploadresale,
					@RequestParam(value="value",required=false) String value
					){
				
						String msg=null;
						Boolean flag=false;
						List<DealerUploadReSale> list=new ArrayList<DealerUploadReSale>();
						if(dealeruploadresale.size()>0){
							  for (int i = 0; i < dealeruploadresale.size(); i++)
					         {
								  DealerUploadReSale uploadresale=uploadReSaleService.SaveOrCommits(dealeruploadresale.get(i), value);
								  list.add(uploadresale);
					         } 
							  uploadReSaleService.get9Id(list.get(0).getTid());
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
			
			@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
			public Json DeleteInfo(
					ExtDirectStoreReadRequest request ,
					@RequestParam(value="tid",required=false) String tid
					){
						String msg=null;
						Boolean flag=false;
						List<DealerUploadReSale> list=new ArrayList<DealerUploadReSale>();
						String result=uploadReSaleService.DeleteInfo(tid);
						if(result.equals("操作成功!")){
							flag=true;
							msg=result;
						}else{
							flag=false;
							msg=result;
						}
						
						Json json = new Json();
						json.setData(list);
						json.setMsg(msg);
						json.setSuccess(flag);
						return json;
				    }
	
}
