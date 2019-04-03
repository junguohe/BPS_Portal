package com.erry.auth.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.erry.ext.serializer.JsonDateTimeSerializer;
import com.erry.util.DateUtil;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * TAuUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "erp_person_v")
public class PersonView implements java.io.Serializable {


	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="id")
	private String id;
	
	@Column(name="per_id")
	private String per_id;
	
	@Column(name="per_name")
	private String per_name;
	
	@Column(name="departmentid")
	private String departmentid;
	
	@Column(name="departmentname")
	private String departmentname;

	@Column(name="empid")
	private String empid;
	
	@Column(name="mangerid")
	private String mangerid;
	
	@Column(name="mangername")
	private String mangername;
	
	@Column(name="account")
	private String account;
	
	@Column(name="peremail")
	private String peremail;
	
	@Column(name="fhiredate")
	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	private Date fhiredate;
	
	@Column(name="fempgroup")
	private String fempgroup;
	
	public String getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}

	public String getDepartmentname() {
		return departmentname;
	}

	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}

	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid2) {
		this.empid = empid2;
	}

	public String getMangerid() {
		return mangerid;
	}

	public void setMangerid(String mangerid) {
		this.mangerid = mangerid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getFhiredate() {
			return fhiredate;
	}

	public void setFhiredate(Date fhiredate) {
		this.fhiredate = fhiredate;
	}

	public String getFempgroup() {
		return fempgroup;
	}

	public void setFempgroup(String fempgroup) {
		this.fempgroup = fempgroup;
	}

	@Column(name="active")
	private String active;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getPer_name() {
		return per_name;
	}

	public void setPer_name(String per_name) {
		this.per_name = per_name;
	}

	public String getPer_id() {
		return per_id;
	}

	public void setPer_id(String per_id) {
		this.per_id = per_id;
	}

	public String getPeremail() {
		return peremail;
	}

	public void setPeremail(String peremail) {
		this.peremail = peremail;
	}

	public String getMangername() {
		return mangername;
	}

	public void setMangername(String mangername) {
		this.mangername = mangername;
	}

	

	


}