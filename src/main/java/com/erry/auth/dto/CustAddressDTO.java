package com.erry.auth.dto;


import com.erry.auth.model.CustAddress;
import com.erry.util.DateUtil;
public class CustAddressDTO {


	private String id;
	private String cpid;
	private String addtype;
	private String province;
	private String city;
	private String address;
	private String isdefault;
	private String remark;
	private String active;
	private String creator;
	private String createdate;
	private String updator;
	private String updatedate;
	private String prodname;
	
	
 public CustAddressDTO(CustAddress cust) {
	 
	 	this.id = cust.getId();
		this.cpid = cust.getCpid();
		this.addtype = cust.getAddtype();
		this.province = cust.getProvince();
		this.city = cust.getCity();
		this.address = cust.getAddress();
		this.isdefault = cust.getIsdefault();
		this.remark = cust.getRemark();
		this.active = cust.getActive();
		this.creator = cust.getCreator();

		this.updator = cust.getUpdator();
		if(cust.getCreatedate()!=null){
			this.createdate = DateUtil.format(cust.getCreatedate(), DateUtil.PATTERN_DATE); 
		}else{
			this.createdate=null;
		}
		if(cust.getUpdatedate()!=null){
			this.updatedate = DateUtil.format(cust.getUpdatedate(), DateUtil.PATTERN_DATE); 
		}else{
			this.updatedate=null;
		}
		this.prodname = cust.getCustprodline().getProd().getProdname();
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


public String getAddtype() {
	return addtype;
}


public void setAddtype(String addtype) {
	this.addtype = addtype;
}


public String getProvince() {
	return province;
}


public void setProvince(String province) {
	this.province = province;
}


public String getCity() {
	return city;
}


public void setCity(String city) {
	this.city = city;
}


public String getAddress() {
	return address;
}


public void setAddress(String address) {
	this.address = address;
}


public String getIsdefault() {
	return isdefault;
}


public void setIsdefault(String isdefault) {
	this.isdefault = isdefault;
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
	
	
}

