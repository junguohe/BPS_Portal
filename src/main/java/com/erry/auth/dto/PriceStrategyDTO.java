package com.erry.auth.dto;


import java.math.BigDecimal;

import com.erry.auth.model.PriceStrategy;
import com.erry.auth.model.PriceStrategyDetail;
import com.erry.util.DateUtil;
import com.erry.util.NumberUtil;
import com.erry.util.StringUtil;

public class PriceStrategyDTO {
private String code;
private String materialcode;
private String materialname;
private String prodcode;
private String prodname;
private String lifecycle;
private String ismain;
private String publicdate;
private String ispublic;
private String validfrom;
private String validto;
private String listprice;
private String listpriceinc;
private String dealerprice;
private String dealerpriceinc;
private String dealerprofit;
private String lastprice;
private String reduceper;
private String assembly;
private String currency;

public PriceStrategyDTO(){}
public PriceStrategyDTO(PriceStrategyDetail s){
	PriceStrategy p =s.getPriceStrategy();
	this.ispublic = s.getIspublic();
	this.currency=s.getCurrency();
	if(p!=null){
		this.code= p.getCode();
		this.publicdate = DateUtil.format(p.getPublicdate());
		this.validfrom = DateUtil.format(p.getValidfrom());
		this.validto = DateUtil.format(p.getValidto());

	}
	if(s.getMaterialInfo()!=null){
		this.materialcode =s.getMaterialInfo().getMaterialcode();
		this.materialname =s.getMaterialInfo().getMaterialname();
		this.prodcode = s.getMaterialInfo().getProd().getProdcode();
		this.prodname = s.getMaterialInfo().getProd().getProdname();
	}
		
		this.lifecycle = s.getLifecycle();
		this.assembly = s.getAssembly();
		this.ismain = s.getIsmain();
		if(s.getListprice()!=null){
		this.listprice = StringUtil.fmtMicrometer4(s.getListprice());//s.getListprice().substring(0,s.getListprice().indexOf(".")+4);
		}
		if(s.getListpriceinc()!=null){
		this.listpriceinc = StringUtil.fmtMicrometer4(s.getListpriceinc());//s.getListpriceinc().substring(0,s.getListpriceinc().indexOf(".")+4);
		}
		if(s.getDealerprice()!=null){
		this.dealerprice =StringUtil.fmtMicrometer4(s.getDealerprice());// s.getDealerprice().substring(0,s.getDealerprice().indexOf(".")+4);
		}
		if(s.getDealerpriceinc()!=null){
		this.dealerpriceinc = StringUtil.fmtMicrometer4(s.getDealerpriceinc());//s.getDealerpriceinc().substring(0,s.getDealerpriceinc().indexOf(".")+4);
		}
		if(s.getDealerprofit()!=null){
//		、、this.dealerprofit =StringUtil.fmtMicrometer4(s.getDealerprofit());// s.getDealerprofit().substring(0,s.getDealerprofit().indexOf(",")+4);
		 this.dealerprofit =NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(s.getDealerprofit())), 4).toString();
		}
		if(s.getLastprice()!=null){
		this.lastprice = StringUtil.fmtMicrometer4(s.getLastprice());//s.getLastprice().substring(0,s.getLastprice().indexOf(".")+4);
		}
		if(s.getReduceper()!=null){
			this.reduceper = StringUtil.fmtMicrometer4(s.getReduceper());//s.getLastprice().substring(0,s.getLastprice().indexOf(".")+4);
			}
}

public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
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
public String getProdcode() {
	return prodcode;
}
public void setProdcode(String prodcode) { 
	this.prodcode = prodcode;
}
public String getProdname() {
	return prodname;
}
public void setProdname(String prodname) {
	this.prodname = prodname;
}
public String getIsmain() {
	return ismain;
}
public void setIsmain(String ismain) {
	this.ismain = ismain;
}
public String getPublicdate() {
	return publicdate;
}
public void setPublicdate(String publicdate) {
	this.publicdate = publicdate;
}
public String getIspublic() {
	return ispublic;
}
public void setIspublic(String ispublic) {
	this.ispublic = ispublic;
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
public String getReduceper() {
	return reduceper;
}
public void setReduceper(String reduceper) {
	this.reduceper = reduceper;
}
public String getLifecycle() {
	return lifecycle;
}
public void setLifecycle(String lifecycle) {
	this.lifecycle = lifecycle;
}
public String getAssembly() {
	return assembly;
}
public void setAssembly(String assembly) {
	this.assembly = assembly;
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
public String getDealerprofit() {
	return dealerprofit;
}
public void setDealerprofit(String dealerprofit) {
	this.dealerprofit = dealerprofit;
}
public String getLastprice() {
	return lastprice;
}
public void setLastprice(String lastprice) {
	this.lastprice = lastprice;
}
public String getCurrency() {
	return currency;
}
public void setCurrency(String currency) {
	this.currency = currency;
}

}
