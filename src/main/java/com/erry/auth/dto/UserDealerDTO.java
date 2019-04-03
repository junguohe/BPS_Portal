package com.erry.auth.dto;



public class UserDealerDTO {
	private String dealerid;
	private String userid;
	private String username;
	private String dealername;
	private String email;
	private String telno;
	private String loginname;
	private String expiredate;
	private String ename;
	private String dealercode;
	
	
//	public UserDealerDTO(UserDealer user){
//		this.dealerid=user.getDealerid();
//		this.userid=user.getUserid();
//		this.username=user.getUsername();
//		this.dealername=user.getDealerInfo().getDealername();
//		this.email=user.getEmail();
//		this.telno=user.getTelno();
//		this.loginname=user.getAuuser().getUsername();
//	//	this.expiredate=user.getAuuser().getExpiredate();
//		if(user.getAuuser().getExpiredate()!=null){
//			this.expiredate = DateUtil.format(user.getAuuser().getExpiredate(),DateUtil.PATTERN_DATE_TIME);
//			}
//		
//	}
	
	public String getDealerid() {
		return dealerid;
	}
	public void setDealerid(String dealerid) {
		this.dealerid = dealerid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDealername() {
		return dealername;
	}
	public void setDealername(String dealername) {
		this.dealername = dealername;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getExpiredate() {
		return expiredate;
	}
	public void setExpiredate(String expiredate) {
		this.expiredate = expiredate;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getDealercode() {
		return dealercode;
	}
	public void setDealercode(String dealercode) {
		this.dealercode = dealercode;
	}
	
}
