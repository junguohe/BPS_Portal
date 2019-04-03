package com.erry.auth.dto;



import java.util.Date;

import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustProdLine;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.Customer;
import com.erry.util.DateUtil;


public class CustomerDTO {

	private String custid;
	private String custcode;
	private String custname;
	private String isparent;
	private String parenetcompany;
	private String taxno;
	private String active;
	private String creator;
	private Date createdate;
	private String updator;
	private Date updatedate;
	private String prodname;
	private String prodid;
	private String regstatus;
	private String cpid;
	private String region;
	private String dealername;
	private String dealerid;
	private String regstartdate;
	private String address;
	private String custregStatus;
	private String isbps;
	private String regcreator;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public CustomerDTO(){}

	public CustomerDTO(Customer cust){
		this.custid = cust.getId();
		this.custcode = cust.getCustcode();
		this.custname = cust.getCustname();
		this.isparent = cust.getIsparent(); 
		this.parenetcompany = cust.getParenetcompany();
		this.taxno = cust.getTaxno();
		this.creator = cust.getCreator();
		this.createdate = cust.getCreatedate();
		this.updator = cust.getUpdator();
		this.updatedate = cust.getUpdatedate();
		this.region=cust.getRegion();
		for(CustProdLine s: cust.getChildren()){
			this.regstatus = s.getRegstatus();
			this.prodname = s.getProd().getProdname();
			this.prodid = s.getProd().getProdid();
			this.cpid = s.getCpid();	
			for(CustAddress add:s.getAddress()){
				this.address=add.getAddress();
			}
			if(s.getChildren().size()>0){
			for(int i=0 ; i<s.getChildren().size();i++){

				CustReg custReg = s.getChildren().get(i);
				if((custReg.getActive().equals("1")&&custReg.getRegstatus()!=null&&custReg.getRegstatus().equals("1"))){
					this.isbps=custReg.getIsbps();
					this.active=custReg.getActive();
					if(custReg.getDealer()!=null){
						this.dealername=custReg.getDealer().getDealername();
					}else{
						this.dealername=null;
					}
					if(custReg.getDealer()!=null){
						this.dealerid=custReg.getDealer().getDealerid();
					}else{
						this.dealerid=null;
					}
					this.custregStatus=custReg.getRegstatus();
					
					if(custReg.getCreator()!=null){
						this.regcreator=custReg.getCreator();
					}else{
						this.regcreator=null;
					}
					
					if(custReg.getRegstatus().equals("1")){
						if(custReg.getRegstartdate()!=null){
							this.regstartdate=DateUtil.format(custReg.getRegstartdate(),DateUtil.PATTERN_DATE_TIME);
						}else{
							this.regstartdate=null;
						}
					}
				}
			
			}
			}
		}
	}
	
	public String getRegcreator() {
		return regcreator;
	}

	public void setRegcreator(String regcreator) {
		this.regcreator = regcreator;
	}

	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getCustcode() {
		return custcode;
	}
	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	public String getIsparent() {
		return isparent;
	}
	public void setIsparent(String isparent) {
		this.isparent = isparent;
	}
	public String getTaxno() {
		return taxno;
	}
	public void setTaxno(String taxno) {
		this.taxno = taxno;
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
	public String getProdname() {
		return prodname;
	}
	public void setProdname(String prodname) {
		this.prodname = prodname;
	}
	public String getRegstatus() {
		return regstatus;
	}
	public void setRegstatus(String regstatus) {
		this.regstatus = regstatus;
	}

	public String getCpid() {
		return cpid;
	}

	public void setCpid(String cpid) {
		this.cpid = cpid;
	}

	public String getProdid() {
		return prodid;
	}

	public void setProdid(String prodid) {
		this.prodid = prodid;
	}

	public String getParenetcompany() {
		return parenetcompany;
	}

	public void setParenetcompany(String parenetcompany) {
		this.parenetcompany = parenetcompany;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDealername() {
		return dealername;
	}

	public void setDealername(String dealername) {
		this.dealername = dealername;
	}

	public String getRegstartdate() {
		return regstartdate;
	}

	public void setRegstartdate(String regstartdate) {
		this.regstartdate = regstartdate;
	}

	public String getCustregStatus() {
		return custregStatus;
	}

	public void setCustregStatus(String custregStatus) {
		this.custregStatus = custregStatus;
	}

	public String getDealerid() {
		return dealerid;
	}

	public void setDealerid(String dealerid) {
		this.dealerid = dealerid;
	}

	public String getIsbps() {
		return isbps;
	}

	public void setIsbps(String isbps) {
		this.isbps = isbps;
	}




}
