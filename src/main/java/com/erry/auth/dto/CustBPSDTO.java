package com.erry.auth.dto;

import java.util.Date;

import com.erry.auth.model.CustBPS;
import com.erry.util.DateUtil;

public class CustBPSDTO {
	private String id;	
	private String cpid;
	private String isshare;
	private String custtype;
	private String custcategory1;
	private String custcategory2;
	private String custcategory3;
	private String dealersales;
	private String bpssales;
	private String bpsfae;
	private String active;
	private String prodname;
	private String creator;
	private String createdate;
	private String updator;
	private String updatedate;
	
	public CustBPSDTO(CustBPS cust){
		this.id = cust.getId();
		this.cpid = cust.getCpid();
		this.isshare = cust.getIsshare();
		this.custtype = cust.getCusttype();
		this.custcategory1 = cust.getCustcategory1();
		this.custcategory2 = cust.getCustcategory2();
		this.custcategory3 = cust.getCustcategory3();
		this.dealersales = cust.getDealersales();
		this.bpssales = cust.getBpssales();
		this.bpsfae = cust.getBpsfae();
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

	public String getIsshare() {
		return isshare;
	}

	public void setIsshare(String isshare) {
		this.isshare = isshare;
	}

	public String getCusttype() {
		return custtype;
	}

	public void setCusttype(String custtype) {
		this.custtype = custtype;
	}

	public String getCustcategory1() {
		return custcategory1;
	}

	public void setCustcategory1(String custcategory1) {
		this.custcategory1 = custcategory1;
	}

	public String getCustcategory2() {
		return custcategory2;
	}

	public void setCustcategory2(String custcategory2) {
		this.custcategory2 = custcategory2;
	}

	public String getCustcategory3() {
		return custcategory3;
	}

	public void setCustcategory3(String custcategory3) {
		this.custcategory3 = custcategory3;
	}

	public String getDealersales() {
		return dealersales;
	}

	public void setDealersales(String dealersales) {
		this.dealersales = dealersales;
	}

	public String getBpssales() {
		return bpssales;
	}

	public void setBpssales(String bpssales) {
		this.bpssales = bpssales;
	}

	public String getBpsfae() {
		return bpsfae;
	}

	public void setBpsfae(String bpsfae) {
		this.bpsfae = bpsfae;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getProdname() {
		return prodname;
	}

	public void setProdname(String prodname) {
		this.prodname = prodname;
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

	
	
}
