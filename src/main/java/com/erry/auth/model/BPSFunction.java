package com.erry.auth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_bps_function")
public class BPSFunction implements Serializable{
	@Id
    @Column(name="funcid")
	private String funcid;
	
	@Column(name="module")
	private String module;
	
	@Column(name="funccode")
	private String funccode;
	
	@Column(name="funcname")
	private String funcname;
	
	@Column(name="funcurl")
	private String funcurl;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="active")
	private String active;

	public String getFuncid() {
		return funcid;
	}

	public void setFuncid(String funcid) {
		this.funcid = funcid;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getFunccode() {
		return funccode;
	}

	public void setFunccode(String funccode) {
		this.funccode = funccode;
	}

	public String getFuncname() {
		return funcname;
	}

	public void setFuncname(String funcname) {
		this.funcname = funcname;
	}

	public String getFuncurl() {
		return funcurl;
	}

	public void setFuncurl(String funcurl) {
		this.funcurl = funcurl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
}
