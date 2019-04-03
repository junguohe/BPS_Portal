package com.erry.auth.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.erry.auth.dto.PriceSpecialAuditDTO2;
import com.erry.auth.model.PriceSpecialAudit;
import com.erry.auth.service.PriceSpecialServiceImpl;
import com.erry.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erry.auth.dto.CustDowload;
import com.erry.auth.dto.PriceDowLoad;
import com.erry.auth.service.DowLoadService;
import com.erry.util.DownLoadFileUtil;

@Controller("dowloadcontroller")
public class DowloadController {
	
	@Autowired
	private DowLoadService dowLoadService;

	@Resource
	private PriceSpecialServiceImpl priceSpecialService;
	
	@RequestMapping(value="DowloadController/custdownload")
	public void CustDownload(HttpServletRequest request, HttpServletResponse response ) throws Exception {
		String name = new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8"); 
		String prodid =  request.getParameter("prodid");
		String regstatus =  request.getParameter("regstatus");
		String region =   new String(request.getParameter("region").getBytes("ISO-8859-1"),"UTF-8");
		String dealername =  new String(request.getParameter("dealername").getBytes("ISO-8859-1"),"UTF-8");
		String isshare =  request.getParameter("isshare");
		String bpssales =  new String(request.getParameter("bpssales").getBytes("ISO-8859-1"),"UTF-8");
		String bpsfae =  new String(request.getParameter("bpsfae").getBytes("ISO-8859-1"),"UTF-8");
		String parenetcompany =  request.getParameter("parenetcompany");
		String telno =  request.getParameter("telno");
		String address =  new String(request.getParameter("address").getBytes("ISO-8859-1"),"UTF-8");
		String custtype =  request.getParameter("custtype");
		String taxno =  request.getParameter("taxno");
		
		String fname = "客户信息";
		List<CustDowload> list = dowLoadService.dowloadCustList(name, region, parenetcompany, custtype, taxno, prodid, regstatus, isshare, bpsfae, bpssales, telno, dealername, address);
		String tempname = "Cust";
		DownLoadFileUtil.download(request, response, fname, list,tempname);
	}
	
	@RequestMapping(value="DowloadController/PriceDownload")
	public void PriceDownload(HttpServletRequest request, HttpServletResponse response ) throws Exception {
		String materialcode =  request.getParameter("materialcode");
		String materialname =  new String(request.getParameter("materialname").getBytes("ISO-8859-1"),"UTF-8");
		String isAccuracy =  request.getParameter("isAccuracy");
		String prodid =  request.getParameter("prodid");
		String name =  new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
		String dealername =  new String(request.getParameter("dealername").getBytes("ISO-8859-1"),"UTF-8");
		String startdate =  request.getParameter("startdate");
		String enddate =  request.getParameter("enddate");
		String isRelease =  request.getParameter("isRelease");
		String isMain =  request.getParameter("isMain");
		String istax =  request.getParameter("istax");

		String fname = "特价信息";
		List<PriceDowLoad> list = dowLoadService.dowloadPriceList(materialcode, materialname, isAccuracy, prodid, name, dealername, startdate, enddate, isRelease, isMain,istax);
		String tempname = "SpecialPrice";
		DownLoadFileUtil.downloadprice(request, response, fname, list,tempname);

	}

	@RequestMapping(value = "DowloadController/priceApprovalDowload")
	public void priceApprovalDowload(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String dealername =  new String(request.getParameter("dealername").getBytes("ISO-8859-1"),"UTF-8");
        String custname =   new String(request.getParameter("custname").getBytes("ISO-8859-1"),"UTF-8");
        String region =   new String(request.getParameter("region").getBytes("ISO-8859-1"),"UTF-8");
        String billstatus =  request.getParameter("billstatus");
        String custcode =  request.getParameter("custcode");
        String startdate = request.getParameter("startdate").equals("null")?"":request.getParameter("startdate");
        String enddate =  request.getParameter("enddate").equals("null")?"":request.getParameter("enddate");
        String billno =  request.getParameter("billno");
        String materialname =  request.getParameter("materialname");

        String fname = "特价审批信息";
        Map<Integer,List<PriceSpecialAudit>> pageMap = priceSpecialService.findList(0, 0
                ,dealername,billstatus,custcode,custname,billno,materialname,startdate,enddate,"",region);
        Integer total = null;
        List<PriceSpecialAudit> pricespecial = new ArrayList<PriceSpecialAudit>();
        for(Map.Entry<Integer,List<PriceSpecialAudit>> entry : pageMap.entrySet()){
            pricespecial = entry.getValue();
        }
        List<PriceSpecialAuditDTO2> list = priceSpecialService.converToDTOList2(pricespecial);
        String tempname = "SpecialApprovalPrice";
        DownLoadFileUtil.downloadPriceApproval(request, response, fname, list,tempname);
	}
}
