package com.erry.auth.dto;


import java.util.Date;

import com.erry.auth.model.Contact;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustProdLine;
import com.erry.auth.model.CustReg;
import com.erry.util.DateUtil;
import com.erry.util.StringUtil;


public class CustRegDTO {

	private String id;
	private String cpid;
	private String isbps;
	private String custname;
	private String custcode;
	private String taxno;
	private String parenetcompany;
	private String dealerid;
	private String regstartdate;
	private String regenddate;
	private String remark;
	private String approver;
	private String approvedate;
	private String approveresult;
	private String approveremark;
	private String active;
	private String regstatus;
	private String prodname;
	private String prodid;
	private String region;
	private String dealername;
	private String creator;
	private String createdate;
	private String updator;
	private String updatedate;
	private String address;
	private String custid;
	private String telno;

	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRegstatus() {
		return regstatus;
	}

	public void setRegstatus(String regstatus) {
		this.regstatus = regstatus;
	}

	public CustRegDTO(CustReg cust){
		this.id = cust.getId();
		this.isbps=cust.getIsbps();
		this.cpid = cust.getCpid();
		this.dealerid = cust.getDealerid();
		this.prodid=cust.getCustprodline().getProdid();
		this.region=cust.getCustprodline().getCust().getRegion();
		this.parenetcompany=cust.getCustprodline().getCust().getParenetcompany();
		
		if(cust.getRegstartdate()!=null){
			this.regstartdate =DateUtil.format(cust.getRegstartdate(),DateUtil.PATTERN_DATE_TIME);
		}else{
			this.regstartdate=null;
		}
		if(cust.getRegenddate()!=null){
			this.regenddate =DateUtil.format(cust.getRegenddate(),DateUtil.PATTERN_DATE_TIME);
		}else{
			this.regenddate=null;
		}
		if(cust.getApprovedate()!=null){
			this.approvedate =cust.getApprovedate().toString();
		}else{
			this.approvedate=null;
		}
		this.remark = cust.getRemark();
		this.approver = cust.getApprover();
		this.approveresult = cust.getApproveresult();
		this.approveremark = cust.getApproveremark();
		this.active = cust.getActive();
		this.regstatus = cust.getRegstatus();
		this.custname = cust.getCustprodline().getCust().getCustname();
		this.custcode = cust.getCustprodline().getCust().getCustcode();
		this.custid =cust.getCustprodline().getCust().getId();
		this.taxno = cust.getCustprodline().getCust().getTaxno();
		this.prodname = cust.getCustprodline().getProd().getProdname();
		
		if(cust.getDealer()!=null){
			this.dealername=cust.getDealer().getDealername();
		}else{
			this.dealername=null;
		}
		this.creator = cust.getCreator();
		this.updator = cust.getUpdator();
		//this.updator=cust.getUid().getUsername();
		if(cust.getCreatedate()!=null){
			this.createdate = DateUtil.format(cust.getCreatedate(),DateUtil.PATTERN_DATE_TIME);
		}else{
			this.createdate=null;
		}
		if(cust.getUpdatedate()!=null){
			this.updatedate =cust.getUpdatedate().toString();
		}else{
			this.updatedate=null;
		}

			if(cust.getCustprodline().getAddress().size()>0){
				for(CustAddress add:cust.getCustprodline().getAddress()){
					if(!StringUtil.isEmpty(add.getCreator())&&!StringUtil.isEmpty(cust.getCreator())){
					if(add.getIsdefault().equals("1")&&add.getActive().equals("1")&&add.getCreator().equals(cust.getCreator())){
						this.address=add.getAddress();
					}
				}
				}
				if(StringUtil.isEmpty(this.address)){
					for(CustAddress add:cust.getCustprodline().getAddress()){
						if(!StringUtil.isEmpty(add.getCreator())&&!StringUtil.isEmpty(cust.getCreator())){
						if(add.getActive().equals("1")&&add.getCreator().equals(cust.getCreator())){
							this.address=add.getAddress();
						}
					}
					}
				}
		  //  }
		}
		for(Contact contact:cust.getCustprodline().getContact()){
			if(!StringUtil.isEmpty(contact.getCreator())&&!StringUtil.isEmpty(cust.getCreator())){
				if(contact.getCreator().equals(cust.getCreator())){
					String tempPhone="";
					if(!StringUtil.isEmpty(contact.getMobile())){
						tempPhone = contact.getMobile();
					}
					if(!StringUtil.isEmpty(tempPhone)){
						if(!StringUtil.isEmpty(contact.getTelno())){
							tempPhone =tempPhone+"/"+contact.getTelno();
						}
					}else{
						if(!StringUtil.isEmpty(contact.getTelno())){
							tempPhone =contact.getTelno();
						}
					}
					this.telno=tempPhone;
					if(contact.getActive().equals("1")) break;
				}
			}
			}
			
		}
		
		

	

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getApproveresult() {
		return approveresult;
	}
	public void setApproveresult(String approveresult) {
		this.approveresult = approveresult;
	}
	public String getApproveremark() {
		return approveremark;
	}
	public void setApproveremark(String approveremark) {
		this.approveremark = approveremark;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
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

	public String getRegenddate() {
		return regenddate;
	}

	public void setRegenddate(String regenddate) {
		this.regenddate = regenddate;
	}

	public String getApprovedate() {
		return approvedate;
	}

	public void setApprovedate(String approvedate) {
		this.approvedate = approvedate;
	}

	public String getCustcode() {
		return custcode;
	}

	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}

	public String getDealername() {
		return dealername;
	}

	public void setDealername(String dealername) {
		this.dealername = dealername;
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

	public String getTaxno() {
		return taxno;
	}

	public void setTaxno(String taxno) {
		this.taxno = taxno;
	}

	public String getProdid() {
		return prodid;
	}

	public void setProdid(String prodid) {
		this.prodid = prodid;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getParenetcompany() {
		return parenetcompany;
	}

	public void setParenetcompany(String parenetcompany) {
		this.parenetcompany = parenetcompany;
	}

	public String getIsbps() {
		return isbps;
	}

	public void setIsbps(String isbps) {
		this.isbps = isbps;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}


	
	
}
