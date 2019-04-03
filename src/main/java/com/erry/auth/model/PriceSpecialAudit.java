package com.erry.auth.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Table(name = "T_BPS_Price_Special_Audit")
public class PriceSpecialAudit implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name="id")
	private String id;

	
    @Column(name="spid")
	private String spid;
	
	@Column(name="did")
	private String did;

	@Column(name="lastspl")
	private String lastspl;
	
	@Column(name="lastsplinc")
	private String lastsplinc;
	
	@Column(name="lastdate")
	@ModelField(dateFormat = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date lastdate;
	
	@Column(name="lastcustspl")
	private String lastcustspl;
	
	@Column(name="lastcustsplinc")
	private String lastcustsplinc;
	
	@Column(name="bpssales")
	private String bpssales;
	
	public String getBpssales() {
		return bpssales;
	}

	public void setBpssales(String bpssales) {
		this.bpssales = bpssales;
	}

	@Column(name="lastcustdate")
    @JsonSerialize(using = JsonDateSerializer.class)
	private String lastcustdate;
	
	@Column(name="lastquantity")
	private String lastquantity;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="sugcustspl")
	private String sugcustspl;
	
	@Column(name="sugcustsplinc")
	private String sugcustsplinc;
	
	@Column(name="sugdealerspl")
	private String sugdealerspl;
	
	@Column(name="sugdealersplinc")
	private String sugdealersplinc;
	
	@Column(name="sugdealerprofit")
	private String sugdealerprofit;
	
	
	@Column(name="isspl")
	private String isspl;
	
	@Column(name="isrebate")
	private String isrebate;
	
	
	@Column(name="activedate")

	@ModelField(dateFormat = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date activedate;
	
	
	@Column(name="projname")
	private String projname;
	
	
	@Column(name="projstatus")
	private String projstatus;
	
	
	@Column(name="splno")
	private String splno;
	
	@Column(name="volyear")
	private String volyear;
	
	
	@Column(name="auditremark")
	private String auditremark;
	
	
	@Column(name="approver")
	private String approver;
	
	
	@Column(name="approvedate")
	@ModelField(dateFormat = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date approvedate;
	
	
	@Column(name="approveresult")
	private String approveresult;
	
	@Column(name="active")
	private String active;
	
	@JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="did",nullable = false, updatable = false, insertable = false)
    private PriceSpecialDetail priceSpecialDetail;
	
	@JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="spid",nullable = false, updatable = false, insertable = false)
    private PriceSpecial priceSpecial;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

//	public String getSpid() {
//		return spid;
//	}
//
//	public void setSpid(String spid) {
//		this.spid = spid;
//	}
//
//	public String getDid() {
//		return did;
//	}
//
//	public void setDid(String did) {
//		this.did = did;
//	}

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

	public Date getLastdate() {
		return lastdate;
	}

	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
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

	public String getLastcustdate() {
		return lastcustdate;
	}

	public void setLastcustdate(String lastcustdate) {
		this.lastcustdate = lastcustdate;
	}

	public String getLastquantity() {
		return lastquantity;
	}

	public void setLastquantity(String lastquantity) {
		this.lastquantity = lastquantity;
	}

	public String getCurrency() {
		return currency;
	}

//	public Integer getLastquantity() {
//		return lastquantity;
//	}
//
//	public void setLastquantity(Integer lastquantity) {
//		this.lastquantity = lastquantity;
//	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSugcustspl() {
		return sugcustspl;
	}

	public void setSugcustspl(String sugcustspl) {
		this.sugcustspl = sugcustspl;
	}

	public String getSugcustsplinc() {
		return sugcustsplinc;
	}

	public void setSugcustsplinc(String sugcustsplinc) {
		this.sugcustsplinc = sugcustsplinc;
	}

	public String getSugdealerspl() {
		return sugdealerspl;
	}

	public void setSugdealerspl(String sugdealerspl) {
		this.sugdealerspl = sugdealerspl;
	}

	public String getSugdealersplinc() {
		return sugdealersplinc;
	}

	public void setSugdealersplinc(String sugdealersplinc) {
		this.sugdealersplinc = sugdealersplinc;
	}

	public String getSugdealerprofit() {
		return sugdealerprofit;
	}

	public void setSugdealerprofit(String sugdealerprofit) {
		this.sugdealerprofit = sugdealerprofit;
	}

	public String getIsspl() {
		return isspl;
	}

	public void setIsspl(String isspl) {
		this.isspl = isspl;
	}

	public String getIsrebate() {
		return isrebate;
	}

	public void setIsrebate(String isrebate) {
		this.isrebate = isrebate;
	}

	public Date getActivedate() {
		return activedate;
	}

	public void setActivedate(Date activedate) {
		this.activedate = activedate;
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

	public String getSplno() {
		return splno;
	}

	public void setSplno(String splno) {
		this.splno = splno;
	}

	public String getVolyear() {
		return volyear;
	}

	public void setVolyear(String volyear) {
		this.volyear = volyear;
	}

	public String getAuditremark() {
		return auditremark;
	}

	public void setAuditremark(String auditremark) {
		this.auditremark = auditremark;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Date getApprovedate() {
		return approvedate;
	}

	public void setApprovedate(Date approvedate) {
		this.approvedate = approvedate;
	}

	public String getApproveresult() {
		return approveresult;
	}

	public void setApproveresult(String approveresult) {
		this.approveresult = approveresult;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public PriceSpecialDetail getPriceSpecialDetail() {
		return priceSpecialDetail;
	}

	public void setPriceSpecialDetail(PriceSpecialDetail priceSpecialDetail) {
		this.priceSpecialDetail = priceSpecialDetail;
	}

	public PriceSpecial getPriceSpecial() {
		return priceSpecial;
	}

	public void setPriceSpecial(PriceSpecial priceSpecial) {
		this.priceSpecial = priceSpecial;
	}

	public String getSpid() {
		return spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}



}