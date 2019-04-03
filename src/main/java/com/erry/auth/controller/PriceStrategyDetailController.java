package com.erry.auth.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import com.erry.auth.model.Json;
import com.erry.auth.model.PriceStrategy;
import com.erry.auth.model.PriceStrategyDetail;
import com.erry.auth.service.PriceStrategyDetailServiceImpl;
import com.erry.auth.service.PriceStrategyServiceImpl;
import com.erry.util.DownLoadFileUtil;

@Controller("pricestrategydetailcontroller")
public class PriceStrategyDetailController {

	@Autowired
	private PriceStrategyDetailServiceImpl priceStrategyDetailServiceImpl;
	
	@Autowired
	private PriceStrategyServiceImpl priceStrategyServiceImpl;

	// 价格查询 
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<PriceStrategyDetailDTO> readPriceDTO(
			ExtDirectStoreReadRequest request,
			@RequestParam(value = "materialcode", required = false) String materialcode,// 产品编码
			@RequestParam(value = "materialname", required = false) String materialname,// 产品名称
			@RequestParam(value = "isAccuracy", required = false) String isAccuracy,// 是否精确
			@RequestParam(value = "prodid", required = false) String prodid,// 产品线
			@RequestParam(value = "dealername", required = false) String dealername,// 供应商
			@RequestParam(value = "startdate", required = false) String startdate,// 开始
			@RequestParam(value = "enddate", required = false) String enddate,// 结束
			@RequestParam(value = "isRelease", required = false) String isRelease,// 是否发布
			@RequestParam(value = "name", required = false) String name,// 客户
			@RequestParam(value = "isMain", required = false) String isMain// 是否主要
	) {
		Map<Integer, List<PriceStrategyDetail>> pageMap = priceStrategyDetailServiceImpl.findprice(request.getPage() - 1, request.getLimit(),materialcode, materialname, isAccuracy, prodid, dealername, startdate, enddate, isRelease, name, isMain);
		Integer total = null;
		List<PriceStrategyDetail> list = new ArrayList<PriceStrategyDetail>();
		for (Map.Entry<Integer, List<PriceStrategyDetail>> entry : pageMap.entrySet()) {
			total = entry.getKey();
			list = entry.getValue();
		}
		List<PriceStrategyDetailDTO> lists = priceStrategyDetailServiceImpl.mappingToDTO(list);
		ExtDirectStoreReadResult<PriceStrategyDetailDTO> DTO = new ExtDirectStoreReadResult<PriceStrategyDetailDTO>(
				total, lists);
		return DTO;
	}


	// 价格明细查询
		@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
		public ExtDirectStoreReadResult<PriceStrategyDTO> readPriceStrategy(
				ExtDirectStoreReadRequest request,

				@RequestParam(value = "versionno", required = false) String versionno,
				@RequestParam(value = "code", required = false) String code,
				@RequestParam(value = "materialcode", required = false) String materialcode,
				@RequestParam(value = "materialname", required = false) String materialname,
				@RequestParam(value = "isAccuracy", required = false) String isAccuracy,
				@RequestParam(value = "startdate", required = false) String publicdatef,
				@RequestParam(value = "enddate", required = false) String publicdatet,
				@RequestParam(value = "status", required = false) String status,
				@RequestParam(value = "isMain", required = false) String ismain) {
			Map<Integer, List<PriceStrategyDetail>> pageMap = priceStrategyServiceImpl.findpriceStrategy(request.getPage() - 1, request.getLimit(), versionno, code, materialcode, materialname, isAccuracy, publicdatef, publicdatet, status, ismain);
			Integer total = null;
			List<PriceStrategyDetail> list = new ArrayList<PriceStrategyDetail>();
			for (Map.Entry<Integer, List<PriceStrategyDetail>> entry : pageMap.entrySet()) {
				total = entry.getKey();
				list = entry.getValue();
			}
			List<PriceStrategyDTO> lists = priceStrategyServiceImpl.mappingToDTO(list);
			ExtDirectStoreReadResult<PriceStrategyDTO> DTO = new ExtDirectStoreReadResult<PriceStrategyDTO>(
					total, lists);
			return DTO;

		}
		
		@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
		public Json readPriceStrategys(
				ExtDirectStoreReadRequest request ,
				@RequestParam(value = "materialid", required = false) String materialid,
				@RequestParam(value = "publicdate", required = false) String publicdate
				){
					String msg=null;
					Boolean result=true;
					List<PriceStrategyDetailDTO> list = priceStrategyDetailServiceImpl.getLastPriceByMaterial(materialid,publicdate);
					Json json = new Json();
					json.setData(list);
					json.setMsg(msg);
					json.setSuccess(result);
					return json;
			    }
	//下载模板
	@RequestMapping(value="PriceStrategyDetailController/importFileDownload")
	public void importFileDownload(HttpServletRequest request, HttpServletResponse response ) throws Exception {
		String fname = "价格政策";
		String billno = request.getParameter("billno");
		priceStrategyDetailServiceImpl.importFileDownload(request,response,billno,fname);
	}
	
	//上传数据
	@ExtDirectMethod(ExtDirectMethodType.FORM_POST)
	public ExtDirectFormPostResult uploadAttachment(HttpServletRequest request,@RequestParam("fileUpload") MultipartFile fileUpload )throws IOException, InvalidFormatException {
		 String info = "success";
		 List<PriceStrategyDetailDTO> list= new ArrayList<PriceStrategyDetailDTO>();	
		 ExtDirectFormPostResult resp = new ExtDirectFormPostResult(true);
		 try{
			list = priceStrategyDetailServiceImpl.readFile(fileUpload);
		 }catch(Exception e){
			 info = "上传失败";
			 e.printStackTrace();
		 }
		
		resp.addResultProperty("list", list);
		resp.addResultProperty("info", info);
		return resp;
	}
	
	//上传数据显示
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<PriceStrategyDetailDTO> readUploadInfo(
			ExtDirectStoreReadRequest request,
			@RequestParam(value = "sid", required = false) String sid 
	) {
		List<PriceStrategyDetail> pageMap = priceStrategyDetailServiceImpl.readUploadInfo(request.getPage() - 1, request.getLimit(),sid);
		List<PriceStrategyDetailDTO> lists = priceStrategyDetailServiceImpl.mappingToDTO(pageMap);
		ExtDirectStoreReadResult<PriceStrategyDetailDTO> DTO = new ExtDirectStoreReadResult<PriceStrategyDetailDTO>(lists);
		return DTO;
	}
	
	//提交
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public Json createprice(ExtDirectStoreReadRequest request,@RequestParam(value = "records", required = false) PriceStrategy priceStrategy,
			@RequestParam(value = "pricestrategy", required = false) List<PriceStrategyDetail> priceStrategyDetails){
		return priceStrategyDetailServiceImpl.savePrice(priceStrategy,priceStrategyDetails);
		
	}
	
	
	//调价试算
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<PriceDiffDTo> pricediff(ExtDirectStoreReadRequest request,
			@RequestParam(value = "dealername", required = false) String dealername,
			@RequestParam(value = "period", required = false) String period,
			@RequestParam(value = "pricea", required = false) String pricea,
			@RequestParam(value = "priceb", required = false) String priceb){
		List<PriceDiffDTo> lists = priceStrategyDetailServiceImpl.PriceDiff(dealername, period, pricea, priceb);
		ExtDirectStoreReadResult<PriceDiffDTo> DTO = new ExtDirectStoreReadResult<PriceDiffDTo>(lists);
		return DTO;
	}
	//调价试算
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<PriceDiffDTo> readerPriceDiffInfo(ExtDirectStoreReadRequest request,
			@RequestParam(value = "dealername", required = false) String dealername,
			@RequestParam(value = "period", required = false) String period,
			@RequestParam(value = "pricea", required = false) String pricea,
			@RequestParam(value = "priceb", required = false) String priceb){
		List<PriceDiffDTo> lists = priceStrategyDetailServiceImpl.readerPriceDiffInfo(dealername,pricea,priceb,period);
		ExtDirectStoreReadResult<PriceDiffDTo> DTO = new ExtDirectStoreReadResult<PriceDiffDTo>(lists);
		return DTO;
	}
	//调价试算导出
	@RequestMapping(value="PriceStrategyDetailController/pricediffdownload")
	public void pricediffdownload(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String filename="调价试算报表";
		String dealername =  new String(request.getParameter("dealername").getBytes("ISO-8859-1"),"UTF-8");
		String period =request.getParameter("period");
		String pricea =request.getParameter("pricea");
		String priceb =request.getParameter("priceb");
		List<PriceDiffDTo> list = new ArrayList<PriceDiffDTo>();
		list= priceStrategyDetailServiceImpl.readerPriceDiffInfo(dealername, pricea, priceb,period);
	    String tempname = "调价试算报表";
		try {
			DownLoadFileUtil.downloadPriceTrial(request, response, filename, list,tempname,pricea,priceb);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		
		//标准价导出
		@RequestMapping(value="PriceStrategyDetailController/PriceDownload")
		public void pricedownload(HttpServletRequest request, HttpServletResponse response) throws IOException{
			String filename="标准价";
			String materialcode =  request.getParameter("materialcode");
			String materialname =  request.getParameter("materialname");
			String isAccuracy   =  request.getParameter("isAccuracy");
			String prodid       =  request.getParameter("prodid");
			String dealername   =  request.getParameter("dealername");
			String startdate    =  request.getParameter("startdate");
			String enddate      =  request.getParameter("enddate");
			String isRelease    =  request.getParameter("isRelease");
			String name         =  request.getParameter("name");
			String isMain       =  request.getParameter("isMain");
			
			String fname = "标准价信息";
			List<PriceDowLoad> list = priceStrategyDetailServiceImpl.pricedownload(request, response, materialcode, materialname, 
					isAccuracy, prodid, dealername, startdate, enddate, isRelease,name,isMain,filename);
			
			String tempname = "PriceStrategy";
			try {
				DownLoadFileUtil.downloadprice(request, response, fname, list,tempname);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	
		
		@RequestMapping(value="PriceStrategyDetailController/priceStrategydownload")
		public void priceStrategydownload(HttpServletRequest request, HttpServletResponse response) throws IOException{
			String filename="价格政策";
			String code =  request.getParameter("code");
			String versionno = request.getParameter("versionno");
			String materialcode = request.getParameter("materialcode");
			String materialname = request.getParameter("materialname");
			String isAccuracy =request.getParameter("isAccuracy");
			String startdate =request.getParameter("startdate");
			String enddate =request.getParameter("enddate");
			String status =request.getParameter("status");
			String isMain =request.getParameter("isMain");
			String fname = "价格政策信息";
			
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz", Locale.ENGLISH);
			String newStrat=null;
			String newEnd=null;
			try {
				
				if(startdate!=null&&!startdate.equals("null")){
					Date s= sdf.parse(startdate);
					sdf.applyPattern("yyyy-MM-dd");
					newStrat=sdf.format(s);
				}
				if(enddate!=null&&!enddate.equals("null")){
					Date ed = sdf.parse(enddate);
					sdf.applyPattern("yyyy-MM-dd");
					newEnd=sdf.format(ed);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 List<PriceStrategyDetail> list=priceStrategyDetailServiceImpl.priceStrategydownload(request, response, code, versionno,
					 materialcode, materialname, isAccuracy, newStrat,newEnd,status,isMain, filename);
			 List<PriceStrategyDTO> lists = priceStrategyServiceImpl.mappingToDTO(list);
			 String tempname = "价格政策";
			try {
					DownLoadFileUtil.downloadpriceStrategy(request, response, fname, lists,tempname);
			} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		
		}
		
		@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
		public Json DeletePriceStrategyDetail(
				ExtDirectStoreReadRequest request ,
				@RequestParam(value = "id", required = false) String id
				){
					String msg=null;
					Boolean result=true;
					PriceStrategyDetail pr = new PriceStrategyDetail();
					pr=priceStrategyDetailServiceImpl.DeletePriceStrategyDetail(id);
					Json json = new Json();
					json.setData(pr);
					json.setMsg(msg);
					json.setSuccess(result);
					return json;
			    }
		
		
}
