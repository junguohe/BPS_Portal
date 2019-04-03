package com.erry.auth.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import ch.ralscha.extdirectspring.generator.ModelField;

import com.erry.ext.serializer.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "t_bps_user_bps")
public class UserBPS implements Serializable{
	@Id
//	@GeneratedValue
    @Column(name="id")
	private String id;
	
	@Column(name="personalid")
	private String personalid;
	
	@Column(name="personalname")
	private String personalname;
	
	@Column(name="personalemail")
	private String personalemail;
	
	@Column(name="deptid")
	private String deptid;
	
	@Column(name="deptname")
	private String deptname;
	
	@Column(name="empid")
	private String empid;
	
	@Column(name="managerid")
	private String managerid;
	
	@Column(name="adaccount")
	private String adaccount;
	
	@Column(name="hiredate")
	@ModelField(dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date hiredate;
	
	@Column(name="lastupdtime")
	@ModelField(dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date lastupdtime;
	
	@Column(name="active")
	private String active;
	
	@Column(name="userid")
	private String userid;
	


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPersonalid() {
		return personalid;
	}

	public void setPersonalid(String personalid) {
		this.personalid = personalid;
	}

	public String getPersonalname() {
		return personalname;
	}

	public void setPersonalname(String personalname) {
		this.personalname = personalname;
	}

	public String getPersonalemail() {
		return personalemail;
	}

	public void setPersonalemail(String personalemail) {
		this.personalemail = personalemail;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public String getManagerid() {
		return managerid;
	}

	public void setManagerid(String managerid) {
		this.managerid = managerid;
	}

	public String getAdaccount() {
		return adaccount;
	}

	public void setAdaccount(String adaccount) {
		this.adaccount = adaccount;
	}

	public Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public Date getLastupdtime() {
		return lastupdtime;
	}

	public void setLastupdtime(Date lastupdtime) {
		this.lastupdtime = lastupdtime;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

//	public UserDealer getUserDealer() {
//		return userDealer;
//	}
//
//	public void setUserDealer(UserDealer userDealer) {
//		this.userDealer = userDealer;
//	}

//	public AuUser getAuuser() {
//		return auuser;
//	}
//
//	public void setAuuser(AuUser auuser) {
//		this.auuser = auuser;
//	}
	
	
	
	
}
