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
@Table(name = "T_BPS_Customer_Prodline")
public class CustProdLine implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue
    @Column(name="cpid")
	private String cpid;
	
	@Column(name="prodid")
	private String prodid;

	@Column(name="custid")
	private String custid;
	
	@Column(name="regstatus")
	private String regstatus;
	
	@Column(name="regstartdate")
	@ModelField(dateFormat = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date regstartdate;
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
	
	@Column(name="active")
	private String active;
	
	@JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="custid",nullable = false, updatable = false, insertable = false)
    private Customer cust;
	
	@JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="prodid",nullable = false, updatable = false, insertable = false)
    private ProdLine prod;
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="cpid")
	//private List<CustProdLine> subOperUnits = new ArrayList<CustProdLine>();
	private List<CustReg> children = new ArrayList<CustReg>();
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="cpid")
	//private List<CustProdLine> subOperUnits = new ArrayList<CustProdLine>();
	private List<CustAddress> address = new ArrayList<CustAddress>();
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="cpid")
	//private List<CustProdLine> subOperUnits = new ArrayList<CustProdLine>();
	private List<Contact> contact = new ArrayList<Contact>();
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="cpid")
	//private List<CustProdLine> subOperUnits = new ArrayList<CustProdLine>();
	private List<CustBPS> custbps = new ArrayList<CustBPS>();


	public List<Contact> getContact() {
		return contact;
	}

	public void setContact(List<Contact> contact) {
		this.contact = contact;
	}

	public List<CustAddress> getAddress() {
		return address;
	}

	public void setAddress(List<CustAddress> address) {
		this.address = address;
	}

	public ProdLine getProd() {
		return prod;
	}

	public void setProd(ProdLine prod) {
		this.prod = prod;
	}

	public String getCpid() {
		return cpid;
	}

	public void setCpid(String cpid) {
		this.cpid = cpid;
	}

	public String getProdid() {
		return prodid;
	}

	public void setProdid(String prodid) {
		this.prodid = prodid;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
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

	public Customer getCust() {
		return cust;
	}

	public void setCust(Customer cust) {
		this.cust = cust;
	}

	public List<CustReg> getChildren() {
		return children;
	}

	public void setChildren(List<CustReg> children) {
		this.children = children;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public List<CustBPS> getCustbps() {
		return custbps;
	}

	public void setCustbps(List<CustBPS> custbps) {
		this.custbps = custbps;
	}

}