package com.erry.auth.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "T_BPS_Price_Special")
public class PriceSpecial implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="spid")
	private String spid;
	
	@Column(name="billno")
	private String billno;

	@Column(name="billstatus")
	private String billstatus;
	
	@Column(name="applytype")
	private String applytype;
	
	@Column(name="applicator")
	private String applicator;
	
	@Column(name="applydate")
	@ModelField(dateFormat = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date applydate;
	
	@Column(name="applydealer")
	private String applydealer;
	
	@Column(name="applybps")
	private String applybps;
	
	@Column(name="status")
	private String status;

	@Column(name="istax")
	private String istax;
	
	@Column(name="active")
	private String active;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="spid")
	//private List<CustProdLine> subOperUnits = new ArrayList<CustProdLine>();
	private List<PriceSpecialDetail> priceSpecialDetails = new ArrayList<PriceSpecialDetail>();

	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="spid")
	//private List<CustProdLine> subOperUnits = new ArrayList<CustProdLine>();
	private List<PriceSpecialAudit> priceSpecialAudits = new ArrayList<PriceSpecialAudit>();
	
	
	public String getSpid() {
		return spid;
	}

	public void setSpid(String spid) {
		this.spid = spid;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getBillstatus() {
		return billstatus;
	}

	public void setBillstatus(String billstatus) {
		this.billstatus = billstatus;
	}

	public String getApplytype() {
		return applytype;
	}

	public void setApplytype(String applytype) {
		this.applytype = applytype;
	}

	public String getApplicator() {
		return applicator;
	}

	public void setApplicator(String applicator) {
		this.applicator = applicator;
	}

	public Date getApplydate() {
		return applydate;
	}

	public void setApplydate(Date applydate) {
		this.applydate = applydate;
	}

	public String getApplydealer() {
		return applydealer;
	}

	public void setApplydealer(String applydealer) {
		this.applydealer = applydealer;
	}

	public String getApplybps() {
		return applybps;
	}

	public void setApplybps(String applybps) {
		this.applybps = applybps;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public List<PriceSpecialDetail> getPriceSpecialDetails() {
		return priceSpecialDetails;
	}

	public void setPriceSpecialDetails(List<PriceSpecialDetail> priceSpecialDetails) {
		this.priceSpecialDetails = priceSpecialDetails;
	}

	public List<PriceSpecialAudit> getPriceSpecialAudits() {
		return priceSpecialAudits;
	}

	public void setPriceSpecialAudits(List<PriceSpecialAudit> priceSpecialAudits) {
		this.priceSpecialAudits = priceSpecialAudits;
	}


	public String getIstax() {
		return istax;
	}

	public void setIstax(String istax) {
		this.istax = istax;
	}
}