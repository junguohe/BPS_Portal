package com.erry.auth.dto;

import java.math.BigDecimal;

import com.erry.util.StringUtil;

public class PriceDiffDTo {
	private String dealername;
	private String materialcode;
	private String materialname;
	private String quantity;
	private String dealerpriceinca;
	private String dealerpriceincb;
	private BigDecimal diff;
	private String currency;
	public PriceDiffDTo(){}
	public PriceDiffDTo(PriceDiffDTo dto){
		this.dealername = dto.getDealername();
		this.materialname = dto.getMaterialname();
		this.quantity = dto.getQuantity();
		this.currency = dto.getCurrency();
		this.dealerpriceinca = StringUtil.fmtMicrometer(dto.getDealerpriceinca());//dto.getDealerpriceinca();
		this.dealerpriceincb = StringUtil.fmtMicrometer(dto.getDealerpriceincb());//dto.getDealerpriceincb();
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getDealerpriceinca() {
		return dealerpriceinca;
	}
	public void setDealerpriceinca(String dealerpriceinca) {
		this.dealerpriceinca = dealerpriceinca;
	}
	public String getDealerpriceincb() {
		return dealerpriceincb;
	}
	public void setDealerpriceincb(String dealerpriceincb) {
		this.dealerpriceincb = dealerpriceincb;
	}
	
	public String getDealername() {
		return dealername;
	}
	public void setDealername(String dealername) {
		this.dealername = dealername;
	}
	public String getMaterialname() {
		return materialname;
	}
	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}
	public String getMaterialcode() {
		return materialcode;
	}
	public void setMaterialcode(String materialcode) {
		this.materialcode = materialcode;
	}
	public BigDecimal getDiff() {
		return diff;
	}
	public void setDiff(BigDecimal diff) {
		this.diff = diff;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
}
