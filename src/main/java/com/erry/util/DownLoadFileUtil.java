package com.erry.util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.erry.auth.dto.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.erry.auth.model.DealerUploadReSale;
import com.erry.auth.model.PriceStrategyDetail;
import com.erry.auth.model.UploadInventory;
import com.erry.auth.model.UploadInventoryAdjustment;

public class DownLoadFileUtil {
	 public static void download(HttpServletRequest request,  
	            HttpServletResponse response, String fileName,List<CustDowload> list,String tempname) throws Exception {  
		 	String path = request.getSession().getServletContext()
					.getRealPath("/download");
			InputStream in = new FileInputStream(path + "/custload.xls");
//		 	InputStream in = new FileInputStream("C:/custload.xls");
			HSSFWorkbook wb = new HSSFWorkbook(in);
			for (int i = 0; i < list.size(); i++) {
				HSSFRow row = wb.getSheet("Sheet1").createRow(i + 1);
				
				
				String dealername = list.get(i).getDealername();
				if(dealername.equals("null")){
					dealername="";
				}
				row.createCell(0).setCellValue(dealername);
				
				String cust = list.get(i).getCust();
				if(cust.equals("null")){
					cust="";
				}
				row.createCell(1).setCellValue(cust);
				
				String custcode = list.get(i).getCustcode();
				if(custcode.equals("null")){
					custcode="";
				}
				row.createCell(2).setCellValue(custcode);
				
				String region = list.get(i).getRegion();
				if(region.equals("null")){
					region="";
				}
				row.createCell(3).setCellValue(region);
				
				String taxno = list.get(i).getTaxno();
				if(taxno.equals("null")){
					taxno="";
				}
				row.createCell(4).setCellValue(taxno);
				
				String paren = list.get(i).getParen();
				if(paren.equals("null")){
					paren="";
				}
				row.createCell(5).setCellValue(paren);
				
				String prodid = list.get(i).getProdid();
				if(prodid.equals("null")){
					prodid="";
				}
				row.createCell(6).setCellValue(prodid);
				
				String addtype = list.get(i).getAddtype();
				if(addtype.equals("null")){
					addtype="";
				}
				row.createCell(7).setCellValue(addtype);
				
				String province = list.get(i).getProvince();
				if(province.equals("null")){
					province="";
				}
				row.createCell(8).setCellValue(province);
				
				String city = list.get(i).getCity();
				if(city.equals("null")){
					city="";
				}
				row.createCell(9).setCellValue(city);
				
				String address = list.get(i).getAddress();
				if(address.equals("null")){
					address="";
				}
				row.createCell(10).setCellValue(address);
				
				String remark = list.get(i).getRemark();
				if(remark.equals("null")){
					remark="";
				}
				row.createCell(11).setCellValue(remark);
				
				String title = list.get(i).getTitle();
				if(title.equals("null")){
					title="";
				}
				row.createCell(12).setCellValue(title);
				
				String name = list.get(i).getName();
				if(name.equals("null")){
					name="";
				}
				row.createCell(13).setCellValue(name);

				String mobile = list.get(i).getMobile();
				if(mobile.equals("null")){
					mobile="";
				}
				row.createCell(14).setCellValue(mobile);
				
				String telno = list.get(i).getTelno();
				if(telno.equals("null")){
					telno="";
				}
				row.createCell(15).setCellValue(telno);
				
				String emial = list.get(i).getEmial();
				if(emial.equals("null")){
					emial="";
				}
				row.createCell(16).setCellValue(emial);
				
				String im = list.get(i).getIm();
				if(im.equals("null")){
					im="";
				}
				row.createCell(17).setCellValue(im);
				
				String isshare = list.get(i).getIsshare();
				if(isshare.equals("1")){
					row.createCell(18).setCellValue("共营");
				}else{
					row.createCell(18).setCellValue("非共营");
				}
				
				String custtype = list.get(i).getCusttype();
				if(custtype.equals("null")){
					custtype="";
				}
				row.createCell(19).setCellValue(custtype);
				
				String bpssales = list.get(i).getBpssales();
				if(bpssales.equals("null")){
					bpssales="";
				}
				row.createCell(20).setCellValue(bpssales);
				
				String bpsfae = list.get(i).getBpsfae();
				if(bpsfae.equals("null")){
					bpsfae="";
				}
				row.createCell(21).setCellValue(bpsfae);
				
				String c1 = list.get(i).getC1();
				if(c1.equals("null")){
					c1="";
				}
				row.createCell(22).setCellValue(c1);

				String c2 = list.get(i).getC2();
				if(c2.equals("null")){
					c2="";
				}
				row.createCell(23).setCellValue(c2);

				String c3 = list.get(i).getC3();
				if(c3.equals("null")){
					c3="";
				}
				row.createCell(24).setCellValue(c3);

				String dealersales = list.get(i).getDealersales();
				if(dealersales.equals("null")){
					dealersales="";
				}
				row.createCell(25).setCellValue(dealersales);
				
				String startdate = list.get(i).getRegstartdate();
				if(startdate==null){
					startdate="";
				}
				row.createCell(26).setCellValue(startdate);
			}
			String file = new String(fileName.getBytes("gb2312"), "ISO-8859-1")
			+ ".xls";

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + file
					+ " ");
			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.close();
	 }
	 
	 public static void downloadprice(HttpServletRequest request,  
	            HttpServletResponse response, String fileName,List<PriceDowLoad> list,String tempname) throws Exception {  
		 	String path = request.getSession().getServletContext()
					.getRealPath("/download");
			InputStream in = new FileInputStream(path + "/priceload.xls");
			HSSFWorkbook wb = new HSSFWorkbook(in);
			for (int i = 0; i < list.size(); i++) {
				HSSFRow row = wb.getSheet("Sheet1").createRow(i + 1);
				
				String dealername = list.get(i).getDealername();
				row.createCell(0).setCellValue(dealername);
				
				String cust = list.get(i).getCust();
				row.createCell(1).setCellValue(cust);
				
				String custcode = list.get(i).getCustcode();
				row.createCell(2).setCellValue(custcode);
				
				String applytype = list.get(i).getApplytype();
				row.createCell(3).setCellValue(applytype);
				
				String materialcode = list.get(i).getMaterialcode();
				row.createCell(4).setCellValue(materialcode);
				
				String materialname = list.get(i).getMaterialname();
				row.createCell(5).setCellValue(materialname);
				
				String projname = list.get(i).getProjname();
				if(projname==null||projname.equals("null")){
					row.createCell(6).setCellValue("");
				}else{
					row.createCell(6).setCellValue(projname);
				}
				
				
				String projstatus = list.get(i).getProjstatus();
				if(projstatus==null||projstatus.equals("null")){
					row.createCell(7).setCellValue("");
				}else{
					row.createCell(7).setCellValue(projstatus);
				}
				
				
				String volyear = list.get(i).getVolyear();
				if(volyear==null||volyear.equals("null")){
					row.createCell(8).setCellValue("");
				}else{
					row.createCell(8).setCellValue(volyear);
				}
			
				
				String compmaterial = list.get(i).getCompmaterial();
				if(compmaterial==null||compmaterial.equals("null")){
					row.createCell(9).setCellValue("");
				}else{
					row.createCell(9).setCellValue(compmaterial);
				}
				
				
				String comppriceinc = list.get(i).getComppriceinc();
				if(comppriceinc==null){
					row.createCell(10).setCellValue("");
				}else{
					row.createCell(10).setCellValue(StringUtil.fmtMicrometer4(comppriceinc));
				}
				
				
				String applyprice = list.get(i).getApplyprice();
				if(applyprice==null){
					row.createCell(11).setCellValue("");
				}else{
					row.createCell(11).setCellValue(StringUtil.fmtMicrometer4(applyprice));
				}
				
				
				String applypriceinc = list.get(i).getApplypriceinc();
				if(applypriceinc==null){
					row.createCell(12).setCellValue("");
				}else{
					row.createCell(12).setCellValue(StringUtil.fmtMicrometer4(applypriceinc));
				}
				
				String remark = list.get(i).getRemark();
				if(remark==null||remark.equals("null")){
					row.createCell(13).setCellValue("");
				}else{
					row.createCell(13).setCellValue(remark);
				}
				
				String isnotrebate = list.get(i).getIsnotrebate();
				String temp1 ="";
				if(isnotrebate.equals("0")){
					temp1 = "否";
				}else{
					temp1="是";
				}
				row.createCell(14).setCellValue(temp1);
				
				String sugcustspl = list.get(i).getSugcustspl();
				if(sugcustspl==null){
					row.createCell(15).setCellValue("");
				}else{
					row.createCell(15).setCellValue(StringUtil.fmtMicrometer4(sugcustspl));
				}
				
				String sugcustsplinc = list.get(i).getSugcustsplinc();
				if(sugcustsplinc==null){
					row.createCell(16).setCellValue("");
				}else{
					row.createCell(16).setCellValue(StringUtil.fmtMicrometer4(sugcustsplinc));
				}
				
				String sugdealerspl = list.get(i).getSugdealerspl();
				if(sugdealerspl==null){
					row.createCell(17).setCellValue("");
				}else{
					row.createCell(17).setCellValue(StringUtil.fmtMicrometer4(sugdealerspl));
				}
				
				String sugdealersplinc = list.get(i).getSugdealersplinc();
				if(sugdealersplinc==null){
					row.createCell(18).setCellValue("");
				}else{
					row.createCell(18).setCellValue(StringUtil.fmtMicrometer4(sugdealersplinc));
				}
				
				String sugdealerprofit = list.get(i).getSugdealerprofit();
				if(sugdealerprofit==null){
					row.createCell(19).setCellValue("");
				}else{
					row.createCell(19).setCellValue(sugdealerprofit);
				}
				
				String isspl = list.get(i).getIsspl();
				String temp2="";
				if(isspl.equals("0")){
					temp2="否";
				}else{
					temp2="是";
				}
				row.createCell(20).setCellValue(temp2);
				
				String isrebate = list.get(i).getIsrebate();
				String temp3="";
				if(isrebate.equals("0")){
					temp3="否";
				}else{
					temp3="是";
				}
				row.createCell(21).setCellValue(temp3);
				
				String activedate = list.get(i).getActivedate();
				if(activedate==null){
					row.createCell(22).setCellValue("");
				}else{
					row.createCell(22).setCellValue(activedate);
				}
				

				String billno = list.get(i).getBillno();
				if(billno==null||billno.equals("null")){
					row.createCell(23).setCellValue("");
				}else{
					row.createCell(23).setCellValue(billno);
				}
				

				String auditremark = list.get(i).getAuditremark();
				if(auditremark==null||auditremark.equals("null")){
					row.createCell(24).setCellValue("");
				}else{
					row.createCell(24).setCellValue(auditremark);
				}
				
				String approver = list.get(i).getApprover();
				if(approver==null||approver.equals("null")){
					row.createCell(25).setCellValue("");
				}else{
					row.createCell(25).setCellValue(approver);
				}
				
				
				if(list.get(i).getApplydate()==null){
					row.createCell(26).setCellValue("");		
				}else{
					row.createCell(26).setCellValue(list.get(i).getApplydate());		
				}
				
				if(list.get(i).getApprovedate()==null){
					row.createCell(27).setCellValue("");		
				}else{
					row.createCell(27).setCellValue(list.get(i).getApprovedate());		
				}	
				if(list.get(i).getCurrency()==null){
					row.createCell(28).setCellValue("");		
				}else{
					row.createCell(28).setCellValue(list.get(i).getCurrency());		
				}

				String istax = "是";
				if(list.get(i).getIstax().equals("0")){
					istax="否";
				}
				row.createCell(29).setCellValue(istax);

				row.createCell(30).setCellValue(list.get(i).getId());
				
			}
			String file = new String(fileName.getBytes("gb2312"), "ISO-8859-1")
			+ ".xls";

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + file
					+ " ");
			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.close();
	 }
	 //期末库存导出
	 public static void downloadInventory(HttpServletRequest request,HttpServletResponse response, String fileName,List<UploadInventory> list,String tempname) throws Exception {  
		 	String path = request.getSession().getServletContext()
					.getRealPath("/download");
			InputStream in = new FileInputStream(path + "/UploadInventorys.xls");
			HSSFWorkbook wb = new HSSFWorkbook(in);
			for (int i = 0; i < list.size(); i++) {
				HSSFRow row = wb.getSheet("Sheet1").createRow(i + 1);
				
				String errormsg = list.get(i).getErrormsg();
				row.createCell(0).setCellValue(errormsg);
				
				String materialcode = list.get(i).getMaterialcode();
				row.createCell(1).setCellValue(materialcode);
				
				String materialname = list.get(i).getMaterialname();
				row.createCell(2).setCellValue(materialname);
				
				String qty = list.get(i).getQty();
				row.createCell(3).setCellValue(qty);
				
				String period = list.get(i).getPeriod();
				row.createCell(4).setCellValue(period);
				
				String dealername = list.get(i).getDealername();
				row.createCell(5).setCellValue(dealername);
				
				String adjdate = String.valueOf(DateUtil.format(list.get(i).getAdjdate(),DateUtil.PATTERN_DATE));
				row.createCell(6).setCellValue(adjdate);
				
			}
			String file = new String(fileName.getBytes("gb2312"), "ISO-8859-1")
			+ ".xls";

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + file
					+ " ");
			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.close();
	 }
	 

	//销售数据导出
	 public static void downloadUploadReSale(HttpServletRequest request,HttpServletResponse response, String fileName,List<DealerUploadReSale> list,String tempname) throws Exception {  
		 	String path = request.getSession().getServletContext()
					.getRealPath("/download");
			InputStream in = new FileInputStream(path + "/UploadReSales.xls");
			HSSFWorkbook wb = new HSSFWorkbook(in);
			for (int i = 0; i < list.size(); i++) {
				HSSFRow row = wb.getSheet("Sheet1").createRow(i + 1);
				
				String errormsg = list.get(i).getErrormsg();
				row.createCell(0).setCellValue(errormsg);
				
				String region = list.get(i).getRegion();
				row.createCell(1).setCellValue(region);
				
				String dealername = list.get(i).getDealername();
				row.createCell(2).setCellValue(dealername);
				
				String custname = list.get(i).getCustname();
				row.createCell(3).setCellValue(custname);
				
				String materialname = list.get(i).getMaterialname();
				row.createCell(4).setCellValue(materialname);
		
				String materialcode = list.get(i).getMaterialcode();
				row.createCell(5).setCellValue(materialcode);

				if(list.get(i).getQty()==null){
					row.createCell(6).setCellValue("");
				}else{
					row.createCell(6).setCellValue(list.get(i).getQty());
				}

				String currency = list.get(i).getCurrency();
				if(list.get(i).getCurrency()==null){
					row.createCell(7).setCellValue("");
				}else{
					row.createCell(7).setCellValue(currency);
				}
				
				String isspl = list.get(i).getIsspl();
				String isspls="";
				if(list.get(i).getIsspl().equals("1")){
					isspls="是";
				}else{
					isspls="否";
				}
				row.createCell(8).setCellValue(isspls);
				
				//"经销商标准未税价"

				if(list.get(i).getDealerstdcost()==null){
					row.createCell(9).setCellValue("");
				}else{
					row.createCell(9).setCellValue(StringUtil.fmtMicrometer4(list.get(i).getDealerstdcost().toString()));
				}
				//"BPS标准未税价"
				if(list.get(i).getBpsstdcost()==null){
					row.createCell(10).setCellValue("");
				}else{
					row.createCell(10).setCellValue(StringUtil.fmtMicrometer4(list.get(i).getBpsstdcost().toString()));
				}
				//经销商特价未税价
				if(list.get(i).getDealersplcost()==null){
					row.createCell(11).setCellValue("");
				}else{
					row.createCell(11).setCellValue(StringUtil.fmtMicrometer4(list.get(i).getDealersplcost().toString()));
				}
				//"BPS特价未税价"
				if(list.get(i).getBpssplcost()==null){
					row.createCell(12).setCellValue("");
				}else{
					row.createCell(12).setCellValue(StringUtil.fmtMicrometer4(list.get(i).getBpssplcost().toString()));
				}
				//返货金额
				if(list.get(i).getRebateamount()==null){
					row.createCell(13).setCellValue("");
				}else{
					row.createCell(13).setCellValue(StringUtil.fmtMicrometer2(list.get(i).getRebateamount().toString()));
				}
				//返货差异
				if(list.get(i).getRebatediff()==null){
					row.createCell(14).setCellValue("");
				}else{
					row.createCell(14).setCellValue(StringUtil.fmtMicrometer2(list.get(i).getRebatediff().toString()));
				}
				
				String remark = list.get(i).getRemark();
				row.createCell(15).setCellValue(remark);
				
				String paymenttype = list.get(i).getPaymenttype();
				if(list.get(i).getPaymenttype()==null){
					row.createCell(16).setCellValue("");
				}else{
					row.createCell(16).setCellValue(paymenttype);
				}
				
				String adjdate = String.valueOf(DateUtil.format(list.get(i).getDeliverydate(),DateUtil.PATTERN_DATE));
				row.createCell(17).setCellValue(adjdate);

				if(list.get(i).getUnitprice()==null){
					row.createCell(18).setCellValue("");
				}else{
					row.createCell(18).setCellValue(StringUtil.fmtMicrometer(list.get(i).getUnitprice().toString()));
				}

				String istax = list.get(i).getIstax();
				String istaxs="";
				if(list.get(i).getIstax().equals("1")){
					istaxs="是";
				}else{
					istaxs="否";
				}
				row.createCell(19).setCellValue(istaxs);
				
				if(list.get(i).getContractamount()==null){
					row.createCell(20).setCellValue("");
				}else{
					row.createCell(20).setCellValue(StringUtil.fmtMicrometer(list.get(i).getContractamount().toString()));
				}

				if(list.get(i).getDealerstdpo()==null){
					row.createCell(21).setCellValue("");
				}else{
					row.createCell(21).setCellValue(StringUtil.fmtMicrometer4(list.get(i).getDealerstdpo().toString()));
				}
				
				if(list.get(i).getDealerstdact()==null){
					row.createCell(22).setCellValue("");
				}else{
					row.createCell(22).setCellValue(StringUtil.fmtMicrometer4(list.get(i).getDealerstdact().toString()));
				}
				
				String seqno = list.get(i).getSeqno();
				row.createCell(23).setCellValue(seqno);
			}
			String file = new String(fileName.getBytes("gb2312"), "ISO-8859-1")
			+ ".xls";

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + file
					+ " ");
			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.close();
	 }

	 //库存调整导出
	 public static void downloadInventoryAdjustment(HttpServletRequest request,HttpServletResponse response, 
			 String fileName,List<UploadInventoryAdjustment> list,String tempname) throws Exception {  
		 	String path = request.getSession().getServletContext()
					.getRealPath("/download");
			InputStream in = new FileInputStream(path + "/UploadInventoryAdjustments.xls");
			HSSFWorkbook wb = new HSSFWorkbook(in);
			for (int i = 0; i < list.size(); i++) {
				HSSFRow row = wb.getSheet("Sheet1").createRow(i + 1);
				
				String errormsg = list.get(i).getErrormsg();
				row.createCell(0).setCellValue(errormsg);
				
				String dealername = list.get(i).getDealername();
				row.createCell(1).setCellValue(dealername);
				
				String materialname = list.get(i).getMaterialname();
				row.createCell(2).setCellValue(materialname);
				
				String refdealer = list.get(i).getRefdealer();
				row.createCell(3).setCellValue(refdealer);
				
				String adjtype = list.get(i).getAdjtype();
				String adjtypes="";
				if(adjtype.equals("1")){
					adjtypes="入";
				}else{
					adjtypes="出";
				}
				row.createCell(4).setCellValue(adjtypes);
				
				String adjdate = String.valueOf(DateUtil.format(list.get(i).getAdjdate(),DateUtil.PATTERN_DATE));
				row.createCell(5).setCellValue(adjdate);
				
//				String adjdate = String.valueOf(list.get(i).getAdjdate());
//				row.createCell(5).setCellValue(adjdate);
				
				String qty = list.get(i).getQty();
				row.createCell(6).setCellValue(StringUtil.fmtMicrometer(qty));
				
				String remark = list.get(i).getRemark();
				row.createCell(7).setCellValue(remark);
			}
			String file = new String(fileName.getBytes("gb2312"), "ISO-8859-1")
			+ ".xls";

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + file
					+ " ");
			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.close();
	 }

	public static void downloadpriceStrategy(HttpServletRequest request,
			HttpServletResponse response, String fileName,
			List<PriceStrategyDTO> lists, String tempname)throws Exception {  
	 	String path = request.getSession().getServletContext()
				.getRealPath("/download");
		InputStream in = new FileInputStream(path + "/PriceStrategyDetail.xls");
		HSSFWorkbook wb = new HSSFWorkbook(in);
		for (int i = 0; i < lists.size(); i++) {
			HSSFRow row = wb.getSheet("Sheet1").createRow(i + 1);
			
			String code = lists.get(i).getCode();
			row.createCell(0).setCellValue(code);
			
			String Materialcode = lists.get(i).getMaterialcode();
			row.createCell(1).setCellValue(Materialcode);
			
			String materialname = lists.get(i).getMaterialname();
			row.createCell(2).setCellValue(materialname);
			
			String Prodname = lists.get(i).getProdname();
			row.createCell(3).setCellValue(Prodname);
			
			if(lists.get(i).getLifecycle()!=null){	
				row.createCell(4).setCellValue(lists.get(i).getLifecycle());
			}else{
				row.createCell(4).setCellValue("");
			}
			
			if(lists.get(i).getIsmain().equals("1")){	
				row.createCell(5).setCellValue("是");
			}else{
				row.createCell(5).setCellValue("否");
			}
			
			String publicdate = String.valueOf(DateUtil.format(DateUtil.parse(lists.get(i).getPublicdate()),DateUtil.PATTERN_DATE));
			row.createCell(6).setCellValue(publicdate);
			
			if(lists.get(i).getIspublic().equals("1")){	
				row.createCell(7).setCellValue("是");
			}else{
				row.createCell(7).setCellValue("否");
			}
			
			String validfrom = String.valueOf(DateUtil.format(DateUtil.parse(lists.get(i).getValidfrom()),DateUtil.PATTERN_DATE));
			row.createCell(8).setCellValue(validfrom);
			
			String validto = String.valueOf(DateUtil.format(DateUtil.parse(lists.get(i).getValidto()),DateUtil.PATTERN_DATE));
			row.createCell(9).setCellValue(validto);
			
			if(lists.get(i).getListprice()!=null){
				row.createCell(10).setCellValue(StringUtil.fmtMicrometer4(lists.get(i).getListprice()));
			}else{
				row.createCell(10).setCellValue("");
			}
			

			if(lists.get(i).getListpriceinc()!=null){
				row.createCell(11).setCellValue(StringUtil.fmtMicrometer4(lists.get(i).getListpriceinc()));
			}else{
				row.createCell(11).setCellValue("");
			}

			if(lists.get(i).getDealerprice()!=null){
				row.createCell(12).setCellValue(StringUtil.fmtMicrometer4(lists.get(i).getDealerprice()));
			}else{
				row.createCell(13).setCellValue("");
			}

			String dealerpriceinc = lists.get(i).getDealerpriceinc();
			if(lists.get(i).getDealerpriceinc()!=null){
				row.createCell(13).setCellValue(StringUtil.fmtMicrometer4(dealerpriceinc));
			}else{
				row.createCell(13).setCellValue("");
			}

			if(lists.get(i).getDealerprofit()!=null){
				row.createCell(14).setCellValue(StringUtil.fmtMicrometer4(lists.get(i).getDealerprofit()));
			}else{
				row.createCell(14).setCellValue("");
			}

			if(lists.get(i).getLastprice()!=null){
				row.createCell(15).setCellValue(StringUtil.fmtMicrometer4(lists.get(i).getLastprice()));
			}else{
				row.createCell(15).setCellValue("");
			}

			if(lists.get(i).getReduceper()!=null){
				row.createCell(16).setCellValue(StringUtil.fmtMicrometer4(lists.get(i).getReduceper()));
			}else{
				row.createCell(16).setCellValue("");
			}
			
			String currency = lists.get(i).getCurrency();
			row.createCell(17).setCellValue(currency);
		}
		String file = new String(fileName.getBytes("gb2312"), "ISO-8859-1")
		+ ".xls";

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + file
				+ " ");
		OutputStream out = response.getOutputStream();
		wb.write(out);
		out.close();
 }

	public static void downloadPriceTrial(HttpServletRequest request,
			HttpServletResponse response, String filename,
			List<PriceDiffDTo> lists, String tempname,String pricea,String priceb) throws Exception {
		// TODO Auto-generated method stub
		String path = request.getSession().getServletContext().getRealPath("/download");
		InputStream in = new FileInputStream(path + "/pricetrial.xls");
		HSSFWorkbook wb = new HSSFWorkbook(in);
		for (int i = 0; i < lists.size(); i++) {
			HSSFRow row = wb.getSheet("Sheet1").createRow(i + 1);
			
			String dealername = lists.get(i).getDealername();
			row.createCell(0).setCellValue(dealername);
			
			String Materialcode = lists.get(i).getMaterialcode();
			row.createCell(1).setCellValue(Materialcode);
			
			String materialname = lists.get(i).getMaterialname();
			row.createCell(2).setCellValue(materialname);
			
			String currency = lists.get(i).getCurrency();
			row.createCell(3).setCellValue(currency);
			
			String qut = lists.get(i).getQuantity();
			row.createCell(4).setCellValue(qut);
			
			if(lists.get(i).getDealerpriceinca()!=null){	
				row.createCell(5).setCellValue(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(lists.get(i).getDealerpriceinca())), 4).toString());
			}else{
				row.createCell(5).setCellValue("");
			}
			if(lists.get(i).getDealerpriceincb()!=null){	
				row.createCell(6).setCellValue(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(lists.get(i).getDealerpriceincb())), 4).toString());
			}else{
				row.createCell(6).setCellValue("");
			}
			if(lists.get(i).getDiff()!=null){	
				row.createCell(7).setCellValue(NumberUtil.setScale(lists.get(i).getDiff(), 4).toString());
			}else{
				row.createCell(7).setCellValue("");
			}
			
		}
		
		String file = new String(filename.getBytes("gb2312"), "ISO-8859-1")
		+ ".xls";

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + file
				+ " ");
		OutputStream out = response.getOutputStream();
		wb.write(out);
		out.close();
	}

	public static void downloadPriceApproval(HttpServletRequest request,
										  HttpServletResponse response, String filename,
										  List<PriceSpecialAuditDTO2> lists, String tempname) throws Exception {
		// TODO Auto-generated method stub
		String path = request.getSession().getServletContext().getRealPath("/download");
		InputStream in = new FileInputStream(path + "/priceApproal.xls");
		HSSFWorkbook wb = new HSSFWorkbook(in);
		for (int i = 0; i < lists.size(); i++) {
			HSSFRow row = wb.getSheetAt(0).createRow(i + 1);


			String result = "";
			if(lists.get(i).getBillstatus().equals("3")){
				result="审批驳回";
			}else if(lists.get(i).getBillstatus().equals("2")){
				result="审批通过";
			}else if(lists.get(i).getBillstatus().equals("4")){
				result="待处理";
			}else{
				result="已提交";
			}
			row.createCell(0).setCellValue(result);

			String date = lists.get(i).getApplydate();
			row.createCell(1).setCellValue(date);

			String dealername = lists.get(i).getDealername();
			row.createCell(2).setCellValue(dealername);

			String custname = lists.get(i).getCustname();
			row.createCell(3).setCellValue(custname);

			String materialName = lists.get(i).getMaterialname();
			row.createCell(4).setCellValue(materialName);

			String projName = lists.get(i).getProjname();
			row.createCell(5).setCellValue(projName);

			String projStatus = lists.get(i).getProjstatus();
			row.createCell(6).setCellValue(projStatus);

			String volYear = lists.get(i).getVolyear();
			row.createCell(7).setCellValue(volYear);

			String applypriceinc = lists.get(i).getApplypriceinc();
			row.createCell(8).setCellValue(applypriceinc);

			String applyprice = lists.get(i).getApplyprice();
			row.createCell(9).setCellValue(applyprice);

			String remark = lists.get(i).getRemark();
			row.createCell(10).setCellValue(remark);

			String compmaterial= lists.get(i).getCompmaterial();
			row.createCell(11).setCellValue(compmaterial);

			String comppriceinc= lists.get(i).getComppriceinc();
			row.createCell(12).setCellValue(comppriceinc);

			String currencys= lists.get(i).getCurrencys();
			row.createCell(13).setCellValue(currencys);

			String isnotrebate= lists.get(i).getIsnotrebate();
			if(isnotrebate.equals("1")){
				isnotrebate="是";
			}else{
				isnotrebate="否";
			}
			row.createCell(14).setCellValue(isnotrebate);

			String execqty= lists.get(i).getExecqty();
			row.createCell(15).setCellValue(execqty);

			String istax= lists.get(i).getIstax();
			if(istax.equals("1")){
				istax="是";
			}else{
				istax="否";
			}
			row.createCell(16).setCellValue(istax);
		}

		String file = new String(filename.getBytes("gb2312"), "ISO-8859-1")
				+ ".xls";

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + file
				+ " ");
		OutputStream out = response.getOutputStream();
		wb.write(out);
		out.close();
	}
	 
}
