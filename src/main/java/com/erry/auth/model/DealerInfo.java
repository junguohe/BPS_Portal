package com.erry.auth.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
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
@Table(name = "T_BPS_Dealerinfo")
public class DealerInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue
    @Column(name="dealerid")
	private String dealerid;
	
	@Column(name="dealercode")
	private String dealercode;

	@Column(name="dealername")
	private String dealername;
	
	@Column(name="ename")
	private String ename;
	
	@Column(name="maxregno")
	private Integer maxregno;
	
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
	
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="dealerid")
	private List<CustReg> custReg = new ArrayList<CustReg>();

//	@OneToOne (fetch = FetchType.LAZY, mappedBy = "dealerInfo")
//	@PrimaryKeyJoinColumn 
//	private UserDealer userDealer;
	
	public List<CustReg> getCustReg() {
		return custReg;
	}

	public void setCustReg(List<CustReg> custReg) {
		this.custReg = custReg;
	}

	public String getDealerid() {
		return dealerid;
	}

	public void setDealerid(String dealerid) {
		this.dealerid = dealerid;
	}

	public String getDealercode() {
		return dealercode;
	}

	public void setDealercode(String dealercode) {
		this.dealercode = dealercode;
	}

	public String getDealername() {
		return dealername;
	}

	public void setDealername(String dealername) {
		this.dealername = dealername;
	}

	public Integer getMaxregno() {
		return maxregno;
	}

	public void setMaxregno(Integer maxregno) {
		this.maxregno = maxregno;
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
//
//	public UserDealer getUserDealer() {
//		return userDealer;
//	}
//
//	public void setUserDealer(UserDealer userDealer) {
//		this.userDealer = userDealer;
//	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}


//	public UserDealer getUserDealer() {
//		return userDealer;
//	}
//
//	public void setUserDealer(UserDealer userDealer) {
//		this.userDealer = userDealer;
//	}

	

}