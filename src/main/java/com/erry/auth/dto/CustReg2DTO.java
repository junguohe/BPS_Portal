package com.erry.auth.dto;

import com.erry.auth.model.Contact;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustProdLine;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.Customer;
import com.erry.auth.model.DealerInfo;
import com.erry.auth.model.ProdLine;
import com.erry.util.DateUtil;

public class CustReg2DTO {
	private String cpid;
	private String custname;
	private String prodname;
	private String custid;
	private String custcode;
	private String isparent;
	private String parenetcompany;
	private String taxno;
	private String active;
	private String creator;
	private String createdate;
	private String updator;
	private String updatedate;
	private String prodid;
	private String regstatus;
	private String region;
	private String dealername;
	private String dealerid;
	private String regstartdate;
	private String address;
	private String custregStatus;
	private String isbps;
	private String regcreator;
	
	public CustReg2DTO(){}
	public CustReg2DTO(CustReg c){
		this.cpid = c.getCpid();
		this.active = c.getActive();
		if(c.getCreatedate()!=null){
		this.createdate = DateUtil.format(c.getCreatedate(),DateUtil.PATTERN_DATE_TIME);
		}
		this.updator = c.getUpdator();
		if(c.getUpdatedate()!=null){
		this.updatedate = DateUtil.format(c.getUpdatedate(),DateUtil.PATTERN_DATE_TIME);
		}
		this.custregStatus = c.getRegstatus();
		if(c.getRegstartdate()!=null){
		this.regstartdate = DateUtil.format(c.getRegstartdate(),DateUtil.PATTERN_DATE_TIME);
		}
		this.isbps = c.getIsbps();
		this.regcreator = c.getCreator();
		CustProdLine  cp = c.getCustprodline();
		if(cp!=null){
			if(cp.getAddress().size()>0){
				for(CustAddress add:cp.getAddress()){
					if(add.getIsdefault().equals("1")&&c.getCreator().equals(add.getCreator())&&c.getRegstatus().equals("1")){
						this.address=add.getAddress();
					}
				}
				}
			this.prodid = cp.getProdid();
			this.regstatus = cp.getRegstatus();
			Customer ct = cp.getCust();
			if(ct!=null){
				this.custname = ct.getCustname();
				this.custid = ct.getId();
				this.custcode = ct.getCustcode();
				this.isparent = ct.getIsparent();
				this.parenetcompany = ct.getParenetcompany();
				this.taxno = ct.getTaxno();
				this.region = ct.getRegion();
			}
			ProdLine pl = cp.getProd();
			if(pl!=null){
				this.prodname = pl.getProdname();
				
			}
				
			 
		}
		DealerInfo dealer = c.getDealer();
		if(dealer!=null&&c.getRegstatus().equals("1")){
			this.dealername = dealer.getDealername();
			this.dealerid = dealer.getDealerid();
		}
	}
	
	
	public String getCpid() {
		return cpid;
	}
	public void setCpid(String cpid) {
		this.cpid = cpid;
	}
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	public String getProdname() {
		return prodname;
	}
	public void setProdname(String prodname) {
		this.prodname = prodname;
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
	public String getIsparent() {
		return isparent;
	}
	public void setIsparent(String isparent) {
		this.isparent = isparent;
	}
	public String getParenetcompany() {
		return parenetcompany;
	}
	public void setParenetcompany(String parenetcompany) {
		this.parenetcompany = parenetcompany;
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
	public String getProdid() {
		return prodid;
	}
	public void setProdid(String prodid) {
		this.prodid = prodid;
	}
	public String getRegstatus() {
		return regstatus;
	}
	public void setRegstatus(String regstatus) {
		this.regstatus = regstatus;
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
	public String getDealerid() {
		return dealerid;
	}
	public void setDealerid(String dealerid) {
		this.dealerid = dealerid;
	}
	public String getRegstartdate() {
		return regstartdate;
	}
	public void setRegstartdate(String regstartdate) {
		this.regstartdate = regstartdate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCustregStatus() {
		return custregStatus;
	}
	public void setCustregStatus(String custregStatus) {
		this.custregStatus = custregStatus;
	}
	public String getIsbps() {
		return isbps;
	}
	public void setIsbps(String isbps) {
		this.isbps = isbps;
	}
	public String getRegcreator() {
		return regcreator;
	}
	public void setRegcreator(String regcreator) {
		this.regcreator = regcreator;
	}
	
	
}
