package com.erry.auth.model;

import java.io.Serializable;
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

@Entity
@Table(name="T_BPS_Cust_Projectinfo")
public class CustProjectInfo implements Serializable{

	@Id
	//@GeneratedValue
    @Column(name="id")
	private String id;
	
	@Column(name="cpid")
	private String cpid;

	@Column(name="materialid")
	private String materialid;
	
	@Column(name="projtype")
	private String projtype;
	
	@Column(name="projname")
	private String projname;
	
	@Column(name="compname")
	private String compname;
	
	@Column(name="compprod")
	private String compprod;
	
	@Column(name="shipvol")
	private String shipvol;
	
	@Column(name="massproddate")
	private String massproddate;
	
	@Column(name="active")
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
	
	@Column(name="remark")
	private String remark;
	
	
	@Column(name="updatedate")
	@ModelField(dateFormat = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date updatedate;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="cpid",nullable = false, updatable = false, insertable = false)
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

	public String getMaterialid() {
		return materialid;
	}

	public void setMaterialid(String materialid) {
		this.materialid = materialid;
	}

	public String getProjtype() {
		return projtype;
	}

	public void setProjtype(String projtype) {
		this.projtype = projtype;
	}

	public String getProjname() {
		return projname;
	}

	public void setProjname(String projname) {
		this.projname = projname;
	}

	public String getCompname() {
		return compname;
	}

	public void setCompname(String compname) {
		this.compname = compname;
	}

	public String getCompprod() {
		return compprod;
	}

	public void setCompprod(String compprod) {
		this.compprod = compprod;
	}

	public String getShipvol() {
		return shipvol;
	}

	public void setShipvol(String shipvol) {
		this.shipvol = shipvol;
	}

	public String getMassproddate() {
		return massproddate;
	}

	public void setMassproddate(String massproddate) {
		this.massproddate = massproddate;
	}


	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	public CustProdLine getCustprodline() {
		return custprodline;
	}

	public void setCustprodline(CustProdLine custprodline) {
		this.custprodline = custprodline;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
