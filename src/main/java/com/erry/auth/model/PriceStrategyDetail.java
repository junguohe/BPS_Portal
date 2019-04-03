package com.erry.auth.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import ch.ralscha.extdirectspring.generator.ModelField;

import com.erry.ext.serializer.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * TAuUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_BPS_Price_Strategy_Detail")
public class PriceStrategyDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue
    @Column(name="id")
	private String id;
	
	@Column(name="sid")
	private String sid;

	@Column(name="materialid")
	private String mterialid;
	
	@Column(name="lifecycle")
	private String lifecycle;

	@Column(name="assembly")
	private String assembly;
	
	@Column(name="ismain")
	private String ismain;
	
	@Column(name="listprice")
	private String listprice;

	@Column(name="listpriceinc")
	private String listpriceinc;

	@Column(name="dealerprice")
	private String dealerprice;

	@Column(name="dealerpriceinc")
	private String dealerpriceinc;
	
	@Column(name="dealerprofit")
	private String dealerprofit;

	@Column(name="unit")
	private Integer unit;

	@Column(name="currency")
	private String currency;

	@Column(name="lastprice")
	private String lastprice;

	@Column(name="reduceper")
	private String reduceper;
	
	@Column(name="ispublic")
	private String ispublic;
	
	@Column(name="custsp")
	private String custsp;

	@Column(name="active")
	private String active;
	
	@Column(name="eol")
	@ModelField(dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date eol;
	
	@JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="sid",nullable = false, updatable = false, insertable = false)
    private PriceStrategy priceStrategy;
	
	@JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="materialid",nullable = false, updatable = false, insertable = false)
    private MaterialInfo materialInfo;
	
	@Column(name="materialcode")
	private String materialcode;
	
	@Column(name="materialname")
	private String materialname;
	
	@Column(name="errormsg")
	private String errmsg;
//	@Transient
//	private String errmsg;
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}
	
	public String getLifecycle() {
		return lifecycle;
	}


	public Date getEol() {
		return eol;
	}


	public void setEol(Date eol) {
		this.eol = eol;
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


	public String getIsmain() {
		return ismain;
	}


	public void setIsmain(String ismain) {
		this.ismain = ismain;
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


	public Integer getUnit() {
		return unit;
	}


	public void setUnit(Integer unit) {
		this.unit = unit;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getLastprice() {
		return lastprice;
	}


	public void setLastprice(String lastprice) {
		this.lastprice = lastprice;
	}


	public String getReduceper() {
		return reduceper;
	}


	public void setReduceper(String reduceper) {
		this.reduceper = reduceper;
	}


	public String getActive() {
		return active;
	}


	public void setActive(String active) {
		this.active = active;
	}


	public PriceStrategy getPriceStrategy() {
		return priceStrategy;
	}


	public void setPriceStrategy(PriceStrategy priceStrategy) {
		this.priceStrategy = priceStrategy;
	}


	public MaterialInfo getMaterialInfo() {
		return materialInfo;
	}


	public void setMaterialInfo(MaterialInfo materialInfo) {
		this.materialInfo = materialInfo;
	}


	public String getSid() {
		return sid;
	}


	public void setSid(String sid) {
		this.sid = sid;
	}


	public String getMterialid() {
		return mterialid;
	}


	public void setMterialid(String mterialid) {
		this.mterialid = mterialid;
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


	public String getIspublic() {
		return ispublic;
	}


	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}


	public String getCustsp() {
		return custsp;
	}


	public void setCustsp(String custsp) {
		this.custsp = custsp;
	}


	public String getErrmsg() {
		return errmsg;
	}


	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	


}