package com.erry.auth.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import com.erry.auth.dto.CustProdDTO;
import com.erry.auth.dto.PriceSpecialDetailDTO;
import com.erry.auth.model.PriceSpecialDetail;
import com.erry.auth.service.CustProdLineServiceImpl;
import com.erry.auth.service.PriceSpecialServiceImpl;
import com.erry.util.DateUtil;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

@Controller("pricespecialdetailcontrol")
public class PriceSpecialDetailController {
	@Autowired
	private CustProdLineServiceImpl prodLineServiceImpl;
	
	@Autowired
	private PriceSpecialServiceImpl priceSpecialDetailServiceImpl;
	
	
	//特价申请查询 
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<PriceSpecialDetailDTO>  findpriceList(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="billno",required=false)String billno,
											@RequestParam(value="billstatus",required=false)String billstatus,
											@RequestParam(value="custcode",required=false)String custcode,
											@RequestParam(value="custname",required=false)String custname,
											@RequestParam(value="applicator",required=false)String applicator,
											@RequestParam(value="applydealer",required=false)String applydealer,
											@RequestParam(value="startdate",required=false)String startdate,
											@RequestParam(value="materialcode",required=false)String materialcode,
											@RequestParam(value="materialname",required=false)String materialname,
											@RequestParam(value="isAccuracy",required=false)String isAccuracy,
											@RequestParam(value="publicdate",required=false)String publicdate,
											@RequestParam(value="enddate",required=false)String enddate){
		//获取分页后Map集合
				Map<Integer,List<PriceSpecialDetail>> pageMap = priceSpecialDetailServiceImpl.findpriced(request.getPage()-1, request.getLimit(),
						billno, billstatus, custcode, custname, applicator, applydealer, startdate, enddate, materialcode, materialname, isAccuracy, publicdate);
				Integer total = null;
				List<PriceSpecialDetail> pricespecial = new ArrayList<PriceSpecialDetail>();
				for(Map.Entry<Integer,List<PriceSpecialDetail>> entry : pageMap.entrySet()){
					total = entry.getKey();
					pricespecial = entry.getValue();
				}
				List<PriceSpecialDetailDTO> list = priceSpecialDetailServiceImpl.converToDTOList(pricespecial);
				ExtDirectStoreReadResult<PriceSpecialDetailDTO> slDTO = new ExtDirectStoreReadResult<PriceSpecialDetailDTO>(total,list);
				return slDTO;
	}
	// 特价查询
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<PriceSpecialDetailDTO> readPriceSpeDTO(
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
			@RequestParam(value = "isMain", required = false) String isMain,// 是否主要,
			@RequestParam(value = "ispublic", required = false) String ispublic,//是否有效 1有 0 无
			@RequestParam(value = "istax", required = false) String istax,//是否一次性 1是 0 否
			@RequestParam(value = "isnotrebate", required = false) String isnotrebate//是否一次性 1是 0 否
	) {
		//System.out.println("开始查询"+DateUtil.format(new Date(), DateUtil.PATTERN_DATE_TIME));
		Map<Integer, List<PriceSpecialDetailDTO>> pageMap = priceSpecialDetailServiceImpl
				.findPriceSpeList(request.getPage() - 1, request.getLimit(), materialcode, materialname, isAccuracy, prodid, dealername, startdate, enddate, isRelease, name, isMain,ispublic,isnotrebate,istax);
		Integer total = null;
		List<PriceSpecialDetailDTO> list = new ArrayList<PriceSpecialDetailDTO>();
		for (Map.Entry<Integer, List<PriceSpecialDetailDTO>> entry : pageMap
				.entrySet()) {
			total = entry.getKey();
			list = entry.getValue();
		}
		List<PriceSpecialDetailDTO> dtos = priceSpecialDetailServiceImpl.converToDTOPriceList(list);
		ExtDirectStoreReadResult<PriceSpecialDetailDTO> DTO = new ExtDirectStoreReadResult<PriceSpecialDetailDTO>(total,dtos);
		//System.out.println("结束查询"+DateUtil.format(new Date(), DateUtil.PATTERN_DATE_TIME));
		return DTO;
	}
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<CustProdDTO> readerCust(ExtDirectStoreReadRequest request ,
			@RequestParam(value="name",required=false)String name){
		ExtDirectStoreReadResult<CustProdDTO> custproddto = new ExtDirectStoreReadResult<CustProdDTO>(prodLineServiceImpl.getCustProd(name));
		return custproddto;
	}
}
