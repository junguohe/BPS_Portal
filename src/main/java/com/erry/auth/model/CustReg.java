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
@Table(name = "T_BPS_Cust_Reginfo")
public class CustReg implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue
    @Column(name="id")
	private String id;
	
	@Column(name="cpid")
	private String cpid;

	@Column(name="isbps")
	private String isbps;
	
	@Column(name="dealerid")
	private String dealerid;
	
	@Column(name="regstatus")
	private String regstatus;
	
	@Column(name="regstartdate")
	@ModelField(dateFormat = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date regstartdate;
	
	@Column(name="regenddate")
	@ModelField(dateFormat = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date regenddate;
	
	@Column(name="remark")
	private String remark;
	
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
	
	@Column(name="approveremark")
	private String approveremark;
	
	@Column(name="active")
	private String active;
	@Column(name="Creator")
	private String creator;

	@Column(name="Createdate")
	@ModelField(dateFormat = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date createdate;
	
	
	@Column(name="Updator")
	private String updator;
	
	@Column(name="Updatedate")
	@ModelField(dateFormat = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)	
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date updatedate;
	@JsonIgnore
	   @ManyToOne(fetch=FetchType.LAZY, optional = false)
	   @JoinColumn(name="cpid",nullable = false, updatable = false, insertable = false)
	   private CustProdLine custprodline;
	
	@JsonIgnore
	   @ManyToOne(fetch=FetchType.LAZY, optional = false)
	   @JoinColumn(name="dealerid",nullable = false, updatable = false, insertable = false)
	   private DealerInfo dealer;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCpid() {
		return cpid;
	}

	public void setCpid(String cpid) {
		this.cpid = cpid;
	}

	public String getDealerid() {
		return dealerid;
	}

	public void setDealerid(String dealerid) {
		this.dealerid = dealerid;
	}

	public String getRegstatus() {
		return regstatus;
	}

	public void setRegstatus(String regstatus) {
		this.regstatus = regstatus;
	}

	public Date getRegstartdate() {
		return regstartdate;
	}

	public void setRegstartdate(Date regstartdate) {
		this.regstartdate = regstartdate;
	}

	public Date getRegenddate() {
		return regenddate;
	}

	public void setRegenddate(Date regenddate) {
		this.regenddate = regenddate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getApproveremark() {
		return approveremark;
	}

	public void setApproveremark(String approveremark) {
		this.approveremark = approveremark;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public CustProdLine getCustprodline() {
		return custprodline;
	}

	public void setCustprodline(CustProdLine custprodline) {
		this.custprodline = custprodline;
	}

	public DealerInfo getDealer() {
		return dealer;
	}

	public void setDealer(DealerInfo dealer) {
		this.dealer = dealer;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getIsbps() {
		return isbps;
	}

	public void setIsbps(String isbps) {
		this.isbps = isbps;
	}


}