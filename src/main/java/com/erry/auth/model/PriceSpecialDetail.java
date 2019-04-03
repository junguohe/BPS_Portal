package com.erry.auth.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * TAuUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_BPS_Price_Special_Detail")
public class PriceSpecialDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue
    @Column(name="did")
	private String did;
	
	@Column(name="spid")
	private String spid;

	@Column(name="seqno")
	private String seqno;
	
	@Column(name="applytype")
	private String applytype;
	
	@Column(name="cpid")
	private String cpid;

	
	@Column(name="materialid")
	private String materialid;
	
	@Column(name="projname")
	private String projname;
	
	@Column(name="projstatus")
	private String projstatus;
	
	@Column(name="volyear")
	private String volyear;

	@Column(name="compmaterial")
	private String compmaterial;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="comppriceinc")
	private String comppriceinc;
	
	@Column(name="applypriceinc")
	private String applypriceinc;
	
	@Column(name="applyprice")
	private String applyprice;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="isnotrebate")
	private String isnotrebate;
	
	@Column(name="active")
	private String active;

	@Column(name="execqty")
	private String execqty;
	
	@JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="spid",nullable = false, updatable = false, insertable = false)
    private PriceSpecial priceSpecial;
	
	@JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="materialid",nullable = false, updatable = false, insertable = false)
    private MaterialInfo materialInfo;
	
	@JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="cpid",nullable = false, updatable = false, insertable = false)
    private CustProdLine custProdLine;

	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="did")
	//private List<CustProdLine> subOperUnits = new ArrayList<CustProdLine>();
	private List<PriceSpecialAudit> priceSpecialAudits = new ArrayList<PriceSpecialAudit>();
	
	@Transient
	private String materialcode;
	
	@Transient
	private String materialname;
	
	@Transient
	private String orderdate;
	
	
	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getSpid() {
		return spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}

	public String getSeqno() {
		return seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	public String getApplytype() {
		return applytype;
	}

	public void setApplytype(String applytype) {
		this.applytype = applytype;
	}

	public String getCpid() {
		return cpid;
	}

	public void setCpid(String cpid) {
		this.cpid = cpid;
	}

	public String getMaterialid() {
		return materialid;
	}

	public void setMaterialid(String materialid) {
		this.materialid = materialid;
	}

	public String getProjname() {
		return projname;
	}

	public void setProjname(String projname) {
		this.projname = projname;
	}

	public String getProjstatus() {
		return projstatus;
	}

	public void setProjstatus(String projstatus) {
		this.projstatus = projstatus;
	}

	public String getVolyear() {
		return volyear;
	}

	public void setVolyear(String volyear) {
		this.volyear = volyear;
	}

	public String getCompmaterial() {
		return compmaterial;
	}

	public void setCompmaterial(String compmaterial) {
		this.compmaterial = compmaterial;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getComppriceinc() {
		return comppriceinc;
	}

	public void setComppriceinc(String comppriceinc) {
		this.comppriceinc = comppriceinc;
	}

	public String getApplypriceinc() {
		return applypriceinc;
	}

	public void setApplypriceinc(String applypriceinc) {
		this.applypriceinc = applypriceinc;
	}

	public String getApplyprice() {
		return applyprice;
	}

	public void setApplyprice(String applyprice) {
		this.applyprice = applyprice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsnotrebate() {
		return isnotrebate;
	}

	public void setIsnotrebate(String isnotrebate) {
		this.isnotrebate = isnotrebate;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public PriceSpecial getPriceSpecial() {
		return priceSpecial;
	}

	public void setPriceSpecial(PriceSpecial priceSpecial) {
		this.priceSpecial = priceSpecial;
	}

	public MaterialInfo getMaterialInfo() {
		return materialInfo;
	}

	public void setMaterialInfo(MaterialInfo materialInfo) {
		this.materialInfo = materialInfo;
	}

	public CustProdLine getCustProdLine() {
		return custProdLine;
	}

	public void setCustProdLine(CustProdLine custProdLine) {
		this.custProdLine = custProdLine;
	}

	public List<PriceSpecialAudit> getPriceSpecialAudits() {
		return priceSpecialAudits;
	}

	public void setPriceSpecialAudits(List<PriceSpecialAudit> priceSpecialAudits) {
		this.priceSpecialAudits = priceSpecialAudits;
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

	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}


	public String getExecqty() {
		return execqty;
	}

	public void setExecqty(String execqty) {
		this.execqty = execqty;
	}
}