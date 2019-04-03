package com.erry.auth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_bps_user_dealer")
public class UserDealer {
	@Id
	// @GeneratedValue
	@Column(name = "id")
	private String id;

	@Column(name = "username")
	private String username;

	@Column(name = "telno")
	private String telno;

	@Column(name = "email")
	private String email;

	@Column(name = "active")
	private String active;
	
	@Column(name = "dealerid")
	private String dealerid;
	
	@Column(name = "userid")
	private String userid;


	public String getDealerid() {
		return dealerid;
	}

	public void setDealerid(String dealerid) {
		this.dealerid = dealerid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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


//	public AuUser getAuuser() {
//		return auuser;
//	}
//
//	public void setAuuser(AuUser auUser) {
//		this.auuser = auUser;
//	}
//
//	public DealerInfo getDealerInfo() {
//		return dealerInfo;
//	}
//
//	public void setDealerInfo(DealerInfo dealerInfo) {
//		this.dealerInfo = dealerInfo;
//	}
	
	
}
