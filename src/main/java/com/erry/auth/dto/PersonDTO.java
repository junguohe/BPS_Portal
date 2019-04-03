package com.erry.auth.dto;

import com.erry.auth.model.PersonView;
import com.erry.util.DateUtil;


public class PersonDTO {
	private String id;
	private String per_id;
	private String per_name;
	private String departmentid;
	private String departmentname;
	private String empid;
	private String mangerid;
	private String account;
	private String fhiredate;
	private String fempgroup;
	private String active;
	private String peremail;
	private String mangername;
	public PersonDTO(){}
	
	public PersonDTO(PersonView p){
		this.id = p.getId();
		this.per_id = p.getPer_id();
		this.per_name = p.getPer_name();
		this.departmentid = p.getDepartmentid();
		this.departmentname = p.getDepartmentname();
		this.empid = p.getEmpid();
		this.mangerid = p.getMangerid();
		this.account = p.getAccount();
		this.fhiredate = p.getFhiredate()==null?"":DateUtil.format(p.getFhiredate(),DateUtil.PATTERN_DATE);
		this.fempgroup = p.getFempgroup();
		this.active= p.getActive();
		this.peremail=p.getPeremail();
		this.mangername=p.getMangername();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPer_id() {
		return per_id;
	}
	public void setPer_id(String per_id) {
		this.per_id = per_id;
	}
	public String getPer_name() {
		return per_name;
	}
	public void setPer_name(String per_name) {
		this.per_name = per_name;
	}
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
	public void setEmpid(String empid) {
		this.empid = empid;
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
	public String getFhiredate() {
		return fhiredate;
	}
	public void setFhiredate(String fhiredate) {
		this.fhiredate = fhiredate;
	}
	public String getFempgroup() {
		return fempgroup;
	}
	public void setFempgroup(String fempgroup) {
		this.fempgroup = fempgroup;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
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
