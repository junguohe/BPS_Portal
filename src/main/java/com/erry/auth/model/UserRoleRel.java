package com.erry.auth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import java.io.Serializable;

import javax.persistence.Table;

@Entity
@Table(name = "t_bps_user_role_rel")
public class UserRoleRel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name="id")
	private String id;
	
	@Column(name="userid")
	private String userid;
	
	@Column(name="roleid")
	private String roleid;
	
	@Column(name="active")
	private String active;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	
}
