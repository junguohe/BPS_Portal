package com.erry.auth.dto;

import java.util.List;

import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustBPS;
import com.erry.auth.model.CustProdLine;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.Customer;
import com.erry.auth.model.ProdLine;
import com.erry.util.DateUtil;
import com.erry.util.StringUtil;

public class CustProdDTO {
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
	private String approvestatus;
	private String bpssales;
	private String bpsfae;
	private String approveremark;
	public String getApprovestatus() {
		return approvestatus;
	}
	public void setApprovestatus(String approvestatus) {
		this.approvestatus = approvestatus;
	}
	public String getRegcreator() {
		return regcreator;
	}
	public void setRegcreator(String regcreator) {
		this.regcreator = regcreator;
	}
	public CustProdDTO(){}
	public CustProdDTO(CustProdLine p){
		this.cpid = p.getCpid();
		this.custid = p.getCustid();
		this.regstatus = p.getRegstatus();
		if(p.getRegstartdate()!=null){
		this.regstartdate = DateUtil.format(p.getRegstartdate(),DateUtil.PATTERN_DATE_TIME);
		}
		if(p.getCreatedate()!=null){
			this.createdate = DateUtil.format(p.getCreatedate(),DateUtil.PATTERN_DATE_TIME);
		}
		this.updator = p.getUpdator();	
		this.creator = p.getCreator();
		this.active = p.getActive();
		this.prodid = p.getProdid();
		
		if(p.getCustbps().size()>0){
			for(CustBPS bps : p.getCustbps()){
				if(!StringUtil.isEmpty(bps.getBpsfae())){
				this.bpsfae = bps.getBpsfae().trim();
				}
				if(!StringUtil.isEmpty(bps.getBpssales())){
				this.bpssales = bps.getBpssales().trim();
				}
			}
		}
		Customer ct = p.getCust();
		if(ct!=null){
			this.custcode = ct.getCustcode();
			this.custname = ct.getCustname();
			this.isparent = ct.getIsparent();
			this.parenetcompany = ct.getParenetcompany();
			this.taxno = ct.getTaxno();
			this.region = ct.getRegion();
		}
		List<CustReg> reg = p.getChildren();
		for(int i=0 ; i<reg.size();i++){
			CustReg custReg = reg.get(i);
			//this.isbps = custReg.getIsbps();
			
			if((custReg.getActive().equals("1")&&custReg.getRegstatus()!=null)){
				this.active=custReg.getActive();
				this.approveremark=custReg.getApproveremark();
				this.isbps=custReg.getIsbps();
				this.approvestatus = custReg.getApproveresult();
				if(custReg.getDealer()!=null&&custReg.getRegstatus().equals("1")){
					this.dealername=custReg.getDealer().getDealername();
					this.dealerid=custReg.getDealer().getDealerid();
				}
				if(custReg.getCreator()!=null&&(p.getRegstatus().equals("1")||custReg.getIsbps().equals("1"))){
					this.regcreator=custReg.getCreator();
				}
				this.custregStatus=custReg.getRegstatus();
				if(custReg.getRegstatus().equals("1")){
					if(custReg.getRegstartdate()!=null){
						this.regstartdate=DateUtil.format(custReg.getRegstartdate(),DateUtil.PATTERN_DATE_TIME);
					}else{
						this.regstartdate=null;
					}
				}
				if(custReg.getRegstatus().equals("1")||custReg.getIsbps().equals("1")){
					if(p.getAddress().size()>0){
						for(CustAddress add:p.getAddress()){
							if(add.getIsdefault().equals("1")&&add.getActive().equals("1")){
								this.address=add.getAddress();
							}
						}
						if(StringUtil.isEmpty(this.address)){
							for(CustAddress add:p.getAddress()){
								if(add.getActive().equals("1")){
									this.address=add.getAddress();
								}
							}
						}
				    }
				}
				
			}
			
				
		}
		ProdLine cp = p.getProd();
		if(cp!=null){
			this.prodname = cp.getProdname();
			
		}
		 
	}
	
	
	public String getApproveremark() {
		return approveremark;
	}
	public void setApproveremark(String approveremark) {
		this.approveremark = approveremark;
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
	public String getUpdator() {
		return updator;
	}
	public void setUpdator(String updator) {
		this.updator = updator;
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
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
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
	
}
