package com.erry.auth.dto;




import com.erry.auth.model.Contact;
import com.erry.util.DateUtil;
public class ContactDTO {

	private String id;
	private String cpid;
	private String title;
	private String name;
	private String mobile;
	private String telno;
	private String email;
	private String im;
	private String remark;
	private String active;
	private String creator;
	private String createdate;
	private String updator;
	private String updatedate;
	private String prodname;
	
	
 public ContactDTO(Contact contact) {
	 
	 	this.id = contact.getId();
		this.cpid = contact.getCpid();
		this.title = contact.getTitle();
		this.name = contact.getName();
		this.mobile = contact.getMobile();
		this.telno = contact.getTelno();
		this.email = contact.getEmail();
		this.im = contact.getIm();
		this.remark = contact.getRemark();
		this.active = contact.getActive();
		this.creator = contact.getCreator();
		if(contact.getCreatedate()!=null){
			this.createdate = DateUtil.format(contact.getCreatedate(), DateUtil.PATTERN_DATE); 
		}else{
			this.createdate = null; 
		}
		if(contact.getUpdatedate()!=null){
			this.updatedate = DateUtil.format(contact.getUpdatedate(), DateUtil.PATTERN_DATE); 
		}else{
			this.updatedate = null; 
		}
		this.updator = contact.getUpdator();
		
		this.prodname = contact.getCustprodline().getProd().getProdname();
	}


public String getId() {
	return id;
}


public void setId(String id) {
	this.id = id;
}


public String getCpid() {
	return cpid;
}


public void setCpid(String cpid) {
	this.cpid = cpid;
}


public String getTitle() {
	return title;
}


public void setTitle(String title) {
	this.title = title;
}


public String getName() {
	return name;
}


public void setName(String name) {
	this.name = name;
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


public String getIm() {
	return im;
}


public void setIm(String im) {
	this.im = im;
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


public String getCreator() {
	return creator;
}


public void setCreator(String creator) {
	this.creator = creator;
}


public String getCreatedate() {
	return createdate;
}


public void setCreatedate(String createdate) {
	this.createdate = createdate;
}


public String getUpdator() {
	return updator;
}


public void setUpdator(String updator) {
	this.updator = updator;
}


public String getUpdatedate() {
	return updatedate;
}


public void setUpdatedate(String updatedate) {
	this.updatedate = updatedate;
}


public String getProdname() {
	return prodname;
}


public void setProdname(String prodname) {
	this.prodname = prodname;
}


public String getMobile() {
	return mobile;
}


public void setMobile(String mobile) {
	this.mobile = mobile;
}
	
	
}

