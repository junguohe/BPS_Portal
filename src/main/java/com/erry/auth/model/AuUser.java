package com.erry.auth.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


/**
 * TAuUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_bps_user")
public class AuUser implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue
    @Column(name="userid")
	private String uid;
	
	@Column(name="usercode")
	private String usercode;

	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="usertype")
	private String usertype;
	
	@Column(name="expiredate")
	private Date expiredate;
	
	@Column(name="active")
	private String active;
	
	@Column(name="creator")
	private String creator;
	
	@Column(name="createdate")
	private Date createdate;
	
	@Column(name="updator")
	private String updator;
	
	@Column(name="updatedate")
	private Date updatedate;
	
//	@OneToOne (fetch = FetchType.LAZY, mappedBy = "auuser")
//	@PrimaryKeyJoinColumn 
//	private UserDealer userdealer;

//	@OneToOne (fetch = FetchType.LAZY, mappedBy = "auuser")
//	@PrimaryKeyJoinColumn 
//	private UserBPS userbps;

//	public UserBPS getUserbps() {
//		return userbps;
//	}
//
//	public void setUserbps(UserBPS userbps) {
//		this.userbps = userbps;
//	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public Date getExpiredate() {
		return expiredate;
	}

	public void setExpiredate(Date expiredate) {
		this.expiredate = expiredate;
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

//	public UserDealer getUserDealer() {
//		return userdealer;
//	}
//
//	public void setUserDealer(UserDealer userDealer) {
//		this.userdealer = userDealer;
//	}
//
//	public UserDealer getUserdealer() {
//		return userdealer;
//	}
//
//	public void setUserdealer(UserDealer userdealer) {
//		this.userdealer = userdealer;
//	}
}