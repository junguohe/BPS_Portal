package com.erry.auth.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.erry.auth.dto.UploadTaskInfoDTO;
import com.erry.auth.model.Contact;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustBPS;
import com.erry.auth.model.CustProdLine;
import com.erry.auth.model.CustProjectInfo;
import com.erry.auth.model.Customer;
import com.erry.auth.model.DealerUploadReSale;
import com.erry.auth.model.Json;
import com.erry.auth.model.UploadInventory;
import com.erry.auth.model.UploadInventoryAdjustment;
import com.erry.auth.model.UploadTaskInfo;
import com.erry.auth.service.UploadInventoryAdjustmentService;
import com.erry.auth.service.UploadInventoryService;
import com.erry.auth.service.UploadReSaleService;
import com.erry.auth.service.UploadTaskInfoServiceImpl;
import com.erry.util.DownLoadFileUtil;

@Controller("UploadTaskInfoController")
public class UploadTaskInfoController {

	@Autowired
	private UploadTaskInfoServiceImpl uploadtaskinfoserviceimpl;
	@Autowired
	private UploadReSaleService uploadReSaleService;
	@Autowired
	private UploadInventoryService uploadInventoryServiceImpl;
	@Autowired
	private UploadInventoryAdjustmentService  uploadinventoryadjustmentservice;
	
	Logger log = Logger.getLogger(UploadTaskInfoController.class);

	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<UploadTaskInfoDTO>  findList(//{period:period,dealername:dealername,taskconfirm:taskconfirm}
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="period",required=false)String period,
											@RequestParam(value="dealername",required=false)String dealername,
											@RequestParam(value="remark",required=false)String remark,
											@RequestParam(value="taskconfirm",required=false)String taskconfirm,
											@RequestParam(value="taskcontent",required=false)String taskcontent){
		
		//获取分页后Map集合
				Map<Integer,List<UploadTaskInfo>> pageMap = uploadtaskinfoserviceimpl.findList(request.getPage()-1, request.getLimit()
						,period,dealername,remark, taskconfirm,taskcontent);
				Integer total = null;
				List<UploadTaskInfo> UploadTaskInfoList = new ArrayList<UploadTaskInfo>();
				for(Map.Entry<Integer,List<UploadTaskInfo>> entry : pageMap.entrySet()){
					total = entry.getKey();
					UploadTaskInfoList = entry.getValue();
				}
				List<UploadTaskInfoDTO> list = uploadtaskinfoserviceimpl.converToUploadtaskinfoList(UploadTaskInfoList);
				ExtDirectStoreReadResult<UploadTaskInfoDTO> dto = new ExtDirectStoreReadResult<UploadTaskInfoDTO>(total,list);
				return dto;
	}
	//查询批次
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<UploadTaskInfo>  findTaskOwnerList(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="taskowner",required=false)String taskowner){
				List<UploadTaskInfo> pageMap = uploadtaskinfoserviceimpl.findTaskOwnerList(request.getPage()-1, request.getLimit(),taskowner);
				ExtDirectStoreReadResult<UploadTaskInfo> dto = new ExtDirectStoreReadResult<UploadTaskInfo>(pageMap);
				return dto;
	}

	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json validateReseales(ExtDirectStoreReadRequest request ,@RequestParam(value="tid",required=false) String tid) {
		Json json = new Json();
		try{
			uploadtaskinfoserviceimpl.validateReseale(tid);
			json.setSuccess(true);
			json.setMsg("校验成功");
		}catch (Exception e){
			json.setSuccess(false);
			json.setMsg("校验失败");
		}
		return json;
	}
	
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json DeleteTask(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="tid",required=false) String tid,
			@RequestParam(value="remark",required=false) String remark,
			@RequestParam(value="type",required=false) String type
			)
			{
				String msg=null;
				Boolean result=false;
				UploadTaskInfo task=new UploadTaskInfo();
				task=uploadtaskinfoserviceimpl.deleteTask(tid,type,remark);
				if(task!=null&&task.getId()!=null){
					msg="操作成功";
					result=true;
				}else{
					msg="操作失败";
				}
				Json json = new Json();
				json.setData(task);
				json.setMsg(msg);
				json.setSuccess(result);
				return json;
		}
	
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json UploadRemark(
			ExtDirectStoreReadRequest request ,
			@RequestParam(value="id",required=false) String id,
			@RequestParam(value="remark",required=false) String remark
			)
			{
				String msg=null;
				Boolean result=false;
				UploadTaskInfo task=new UploadTaskInfo();
				task=uploadtaskinfoserviceimpl.UploadRemark(id,remark);
				if(task!=null&&task.getId()!=null){
					msg="操作成功";
					result=true;
				}else{
					msg="操作失败";
				}
				Json json = new Json();
				json.setData(task);
				json.setMsg(msg);
				json.setSuccess(result);
				return json;
		}

	//导出
			@RequestMapping(value="uploadtaskinfocontrol/UploadDownload")
			public void UploadDownload(HttpServletRequest request, HttpServletResponse response) throws IOException{

					String filename="数据明细";
					String period =  request.getParameter("period");
					String dealername =  request.getParameter("dealername");
					String taskconfirm =  request.getParameter("taskconfirm");
					String remark =  request.getParameter("remark");
					String tid =  request.getParameter("tid");
					String type =  request.getParameter("type");
					String fname = "数据明细";
					if(type.equals("resale")){
						List<DealerUploadReSale> list = uploadReSaleService.UploadDownload(request, response,tid,filename);
						String tempname = "dealeruploadresale";
						try {
							DownLoadFileUtil.downloadUploadReSale(request, response, fname, list,tempname);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else if(type.equals("inventory")){
						List<UploadInventory> list = uploadInventoryServiceImpl.UploadDownload(request, response,tid,filename);
						String tempname = "UploadInventory";
						try {
							DownLoadFileUtil.downloadInventory(request, response, fname, list,tempname);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else{
						List<UploadInventoryAdjustment> list = uploadinventoryadjustmentservice.UploadDownload(request, response,tid,filename);
						String tempname = "Inventoryadjustment";
						try {
							DownLoadFileUtil.downloadInventoryAdjustment(request, response, fname, list,tempname);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				
			}


}
