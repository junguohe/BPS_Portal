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
@Table(name = "T_BPS_Cust_bpsinfo")
public class CustBPS implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue
	@Column(name = "id")
	private String id;

	@Column(name = "cpid")
	private String cpid;

	@Column(name = "isshare")
	private String isshare;

	@Column(name = "custtype")
	private String custtype;

	@Column(name = "custcategory1")
	private String custcategory1;

	@Column(name = "custcategory2")
	private String custcategory2;

	@Column(name = "custcategory3")
	private String custcategory3;

	@Column(name = "dealersales")
	private String dealersales;

	@Column(name = "bpssales")
	private String bpssales;

	@Column(name = "bpsfae")
	private String bpsfae;

	@Column(name = "active")
	private String active;


	@Column(name="creator")
	private String creator;
	
	@Column(name="createdate")
	@ModelField(dateFormat = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date createdate;
	
	@Column(name="updator")
	private String updator;
	
	@Column(name="updatedate")
	@ModelField(dateFormat = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date updatedate;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cpid", nullable = false, updatable = false, insertable = false)
	private CustProdLine custprodline;

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

	public String getIsshare() {
		return isshare;
	}

	public void setIsshare(String isshare) {
		this.isshare = isshare;
	}

	public String getCusttype() {
		return custtype;
	}

	public void setCusttype(String custtype) {
		this.custtype = custtype;
	}

	public String getCustcategory1() {
		return custcategory1;
	}

	public void setCustcategory1(String custcategory1) {
		this.custcategory1 = custcategory1;
	}

	public String getCustcategory2() {
		return custcategory2;
	}

	public void setCustcategory2(String custcategory2) {
		this.custcategory2 = custcategory2;
	}

	public String getCustcategory3() {
		return custcategory3;
	}

	public void setCustcategory3(String custcategory3) {
		this.custcategory3 = custcategory3;
	}

	public String getDealersales() {
		return dealersales;
	}

	public void setDealersales(String dealersales) {
		this.dealersales = dealersales;
	}

	public String getBpssales() {
		return bpssales;
	}

	public void setBpssales(String bpssales) {
		this.bpssales = bpssales;
	}

	public String getBpsfae() {
		return bpsfae;
	}

	public void setBpsfae(String bpsfae) {
		this.bpsfae = bpsfae;
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


}