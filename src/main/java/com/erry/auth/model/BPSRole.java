package com.erry.auth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="t_bps_role")
public class BPSRole implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name="roleid")
	private String roleid;
	
	@Column(name="rolecode")
	private String rolecode;
	
	@Column(name="rolename")
	private String rolename;
	
	@Column(name="roletype")
	private String roletype;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="active")
	private String active;
	
	@Transient
	private boolean  checked;

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRoletype() {
		return roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	
	
}
