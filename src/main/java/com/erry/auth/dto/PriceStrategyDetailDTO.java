package com.erry.auth.dto;

import java.math.BigDecimal;

import com.erry.auth.model.PriceStrategyDetail;
import com.erry.util.DateUtil;
import com.erry.util.NumberUtil;
import com.erry.util.StringUtil;

public class PriceStrategyDetailDTO {
	private String id;
	private String sid;
	private String materialid;
	private String assembly;
	private String prodname;
	private String prodid;
	private String prodcode;
	private String lastprice;
	private String dealerprice;//不含税价
	private String dealerpriceinc;//含税价
	private String ismain;//是否主要
	private String lifecycle;//生命周期
	private String createdate;//创建时间
	private String listprice;//市场价
	private String listpriceinc;//市场含税价
	private String code;//政策编码
	private String active;//
	private String materialcode;
	private String materialname;
	private String publicdate;
	private String validfrom;
	private String validto;
	private String dealerprofit;
	private String reduceper;
	private String ispublic;
	private String applytype;//TODO
	private String applydealer;//TODO
	private String customername;//TODO
	private String isnotrebate;//是否一次性
	private String lastspl;//含税价（特价）
	private String lastsplinc;//不含税价（特价）
	private String applydate;//特价生效日期
	private String lastcustspl;//客户含税价(t)
	private String lastcustsplinc;//客户不含税价（t）
	private String auditremark;// 特价说明
	private String quantity;
	private String diff;
	private String pricea;
	private String priceb;
	private String eol;
private String unit;
private String stauts;
private String currency;
private String custsp;//客户起始特价
private String errormsg;
	public PriceStrategyDetailDTO(){}
	 public PriceStrategyDetailDTO(PriceStrategyDetail p){
		 this.id=p.getId();
		 if(p.getEol()!=null){
			 this.eol=DateUtil.format(p.getEol());
		 }
		 
		 this.custsp=p.getCustsp();
		 this.ispublic=p.getIspublic();
		 this.assembly = p.getAssembly();
		 this.currency=p.getCurrency();
		 if(p.getLastprice()!=null){
		 this.lastprice =StringUtil.fmtMicrometer4(p.getLastprice());// p.getLastprice().substring(0,p.getLastprice().indexOf(".")+4);
		 }
		 if( p.getDealerprice()!=null){
		 this.dealerprice = StringUtil.fmtMicrometer4(p.getDealerprice());//p.getDealerprice().substring(0, p.getDealerprice().indexOf(".")+4);
		 }
		 if(p.getDealerpriceinc()!=null){
		 this.dealerpriceinc =StringUtil.fmtMicrometer4(p.getDealerpriceinc());// p.getDealerpriceinc().substring(0,p.getDealerpriceinc().indexOf(".")+4);
		 }
		 this.ismain = p.getIsmain();
		 this.lifecycle = p.getLifecycle();
		 if(p.getListprice()!=null){
		 this.listprice = StringUtil.fmtMicrometer4(p.getListprice());//p.getListprice().substring(0,p.getListprice().indexOf(".")+4);
		 }
		 if(p.getListpriceinc()!=null){
		 this.listpriceinc = StringUtil.fmtMicrometer4(p.getListpriceinc());//p.getListpriceinc().substring(0,p.getListpriceinc().indexOf(".")+4);
		 }
		 this.active = p.getActive();
		 if(p.getDealerprofit()!=null){
		// this.dealerprofit = StringUtil.fmtMicrometer4(p.getDealerprofit());//p.getDealerprofit().substring(0,p.getDealerprofit().indexOf(".")+4);
		 this.dealerprofit =NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(p.getDealerprofit())), 4).toString();
			
		 
		 }
		 if(p.getReduceper()!=null){
			 this.reduceper = StringUtil.fmtMicrometer4(p.getReduceper());//p.getDealerprofit().substring(0,p.getDealerprofit().indexOf(".")+4);
			 }
		
		 if(p.getMaterialInfo()!=null){
			 if(p.getMaterialInfo().getProd()!=null){
				 this.prodname = p.getMaterialInfo().getProd().getProdname();
				 this.prodcode = p.getMaterialInfo().getProd().getProdcode();
				 this.prodid = p.getMaterialInfo().getProd().getProdid();
				 
			 }
				 this.materialid = p.getMaterialInfo().getId();
				 this.materialcode = p.getMaterialInfo().getMaterialcode();
				 this.materialname = p.getMaterialInfo().getMaterialname();
			 
		 }else{
			 this.materialcode = p.getMaterialcode();
			 this.materialname =p.getMaterialname();
			 this.materialid=p.getMterialid();
		 }
		 
		 if(p.getPriceStrategy()!=null){
			 this.sid = p.getPriceStrategy().getSid();
			 this.createdate = DateUtil.format(p.getPriceStrategy().getCreatedate());
			 this.code = p.getPriceStrategy().getCode();
			 this.publicdate = DateUtil.format(p.getPriceStrategy().getPublicdate());
			 this.validfrom = DateUtil.format(p.getPriceStrategy().getValidfrom());
			 this.validto = DateUtil.format(p.getPriceStrategy().getValidto());
		 }
		 this.errormsg=p.getErrmsg();
	 }
	 
	public String getEol() {
		return eol;
	}
	public void setEol(String eol) {
		this.eol = eol;
	}
	public String getProdname() {
		return prodname;
	}
	public void setProdname(String prodname) {
		this.prodname = prodname;
	}
	public String getProdid() {
		return prodid;
	}
	public void setProdid(String prodid) {
		this.prodid = prodid;
	}
	public String getProdcode() {
		return prodcode;
	}
	public void setProdcode(String prodcode) {
		this.prodcode = prodcode;
	}
	public String getDealerprice() {
		return dealerprice;
	}
	public void setDealerprice(String dealerprice) {
		this.dealerprice = dealerprice;
	}
	public String getDealerpriceinc() {
		return dealerpriceinc;
	}
	public void setDealerpriceinc(String dealerpriceinc) {
		this.dealerpriceinc = dealerpriceinc;
	}
	public String getIsmain() {
		return ismain;
	}
	public void setIsmain(String ismain) {
		this.ismain = ismain;
	}
	public String getLifecycle() {
		return lifecycle;
	}
	public void setLifecycle(String lifecycle) {
		this.lifecycle = lifecycle;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getListprice() {
		return listprice;
	}
	public void setListprice(String listprice) {
		this.listprice = listprice;
	}
	public String getListpriceinc() {
		return listpriceinc;
	}
	public void setListpriceinc(String listpriceinc) {
		this.listpriceinc = listpriceinc;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getMaterialid() {
		return materialid;
	}
	public void setMaterialid(String materialid) {
		this.materialid = materialid;
	}
	public String getAssembly() {
		return assembly;
	}
	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}
	public String getLastprice() {
		return lastprice;
	}
	public void setLastprice(String lastprice) {
		this.lastprice = lastprice;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}


	public String getMaterialcode() {
		return materialcode;
	}


	public void setMaterialcode(String materialcode) {
		this.materialcode = materialcode;
	}


	public String getMaterialname() {
		return materialname;
	}


	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}


	public String getPublicdate() {
		return publicdate;
	}


	public void setPublicdate(String publicdate) {
		this.publicdate = publicdate;
	}


	public String getValidfrom() {
		return validfrom;
	}


	public void setValidfrom(String validfrom) {
		this.validfrom = validfrom;
	}


	public String getValidto() {
		return validto;
	}


	public void setValidto(String validto) {
		this.validto = validto;
	}


	public String getDealerprofit() {
		return dealerprofit;
	}


	public void setDealerprofit(String dealerprofit) {
		this.dealerprofit = dealerprofit;
	}


	public String getReduceper() {
		return reduceper;
	}


	public void setReduceper(String reduceper) {
		this.reduceper = reduceper;
	}


	public String getIspublic() {
		return ispublic;
	}


	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}


	public String getApplytype() {
		return applytype;
	}


	public void setApplytype(String applytype) {
		this.applytype = applytype;
	}


	public String getApplydealer() {
		return applydealer;
	}


	public void setApplydealer(String applydealer) {
		this.applydealer = applydealer;
	}


	public String getCustomername() {
		return customername;
	}


	public void setCustomername(String customername) {
		this.customername = customername;
	}


	public String getIsnotrebate() {
		return isnotrebate;
	}


	public void setIsnotrebate(String isnotrebate) {
		this.isnotrebate = isnotrebate;
	}


	public String getLastspl() {
		return lastspl;
	}


	public void setLastspl(String lastspl) {
		this.lastspl = lastspl;
	}


	public String getLastsplinc() {
		return lastsplinc;
	}


	public void setLastsplinc(String lastsplinc) {
		this.lastsplinc = lastsplinc;
	}


	public String getApplydate() {
		return applydate;
	}


	public void setApplydate(String applydate) {
		this.applydate = applydate;
	}


	public String getLastcustspl() {
		return lastcustspl;
	}


	public void setLastcustspl(String lastcustspl) {
		this.lastcustspl = lastcustspl;
	}


	public String getLastcustsplinc() {
		return lastcustsplinc;
	}


	public void setLastcustsplinc(String lastcustsplinc) {
		this.lastcustsplinc = lastcustsplinc;
	}


	public String getAuditremark() {
		return auditremark;
	}


	public void setAuditremark(String auditremark) {
		this.auditremark = auditremark;
	}



	public String getUnit() {
		return unit;
	}



	public void setUnit(String unit) {
		this.unit = unit;
	}



	public String getStauts() {
		return stauts;
	}



	public void setStauts(String stauts) {
		this.stauts = stauts;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getDiff() {
		return diff;
	}
	public void setDiff(String diff) {
		this.diff = diff;
	}
	public String getPricea() {
		return pricea;
	}
	public void setPricea(String pricea) {
		this.pricea = pricea;
	}
	public String getPriceb() {
		return priceb;
	}
	public void setPriceb(String priceb) {
		this.priceb = priceb;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCustsp() {
		return custsp;
	}
	public void setCustsp(String custsp) {
		this.custsp = custsp;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	 
}
