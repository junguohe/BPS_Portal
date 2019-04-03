package com.erry.auth.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import ch.ralscha.extdirectspring.generator.ModelField;

import com.erry.ext.serializer.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * TAuUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_BPS_Customer_Info")
public class Customer implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="custid")
	private String id;
	
	@Column(name="custcode")
	private String custcode;

	@Column(name="custname")
	private String custname;
	
	@Column(name="isparent")
	private String isparent;
	
	@Column(name="Parenetcompany")
	private String parenetcompany;
	
	@Column(name="Taxno")
	private String taxno;
	
	@Column(name="Active")
	private String active;
	
	@Column(name="Creator")
	private String creator;
	
	@Column(name="region")
	private String region;

	//	@Column(name="Creator")
//	private String creator;
//	
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

	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="custid")
	//private List<CustProdLine> subOperUnits = new ArrayList<CustProdLine>();
	private List<CustProdLine> children = new ArrayList<CustProdLine>();
	
//	@JoinColumn(name="prodid")
//	private String prodid;
	
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "street")
//	public Set<House> getHouses() {
//		return this.houses;
//	}
//
//	public void setHouses(Set<House> houses) {
//		this.houses = houses;
//	}
//
//}
	
	
	
	
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustcode() {
		return custcode;
	}

	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getIsparent() {
		return isparent;
	}

	public void setIsparent(String isparent) {
		this.isparent = isparent;
	}
	public String getTaxno() {
		return taxno;
	}

	public void setTaxno(String taxno) {
		this.taxno = taxno;
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

	public List<CustProdLine> getChildren() {
		return children;
	}

	public void setChildren(List<CustProdLine> children) {
		this.children = children;
	}

	public String getParenetcompany() {
		return parenetcompany;
	}

	public void setParenetcompany(String parenetcompany) {
		this.parenetcompany = parenetcompany;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

//	public String getProdid() {
//		return prodid;
//	}
//
//	public void setProdid(String prodid) {
//		this.prodid = prodid;
//	}


	


	
	


}