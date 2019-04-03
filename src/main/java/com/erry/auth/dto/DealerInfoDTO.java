package com.erry.auth.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.erry.auth.model.CustReg;
import com.erry.auth.model.DealerInfo;
import com.erry.util.DateUtil;
public class DealerInfoDTO {

	

	private String dealerid;
	
	private String dealercode;

	private String dealername;

	private Integer maxregno;

	private String prodname;
	
	private String regstartdate;
	
	private String regenddate;
	
	
	
 public DealerInfoDTO(DealerInfo dealer) {
	 	this.dealerid=dealer.getDealerid();
	 	this.dealercode=dealer.getDealercode();
	 	this.dealername=dealer.getDealername();
	 	this.maxregno=dealer.getMaxregno();
	 	
	 	
	 	
	 	
	 	
	 	
	 	for(CustReg custReg:dealer.getCustReg()){
			this.prodname=custReg.getCustprodline().getProd().getProdname();
			if(custReg.getRegstartdate()!=null){
				this.regstartdate = DateUtil.format(custReg.getRegstartdate(), DateUtil.PATTERN_DATE); 
			}else{
				this.regstartdate=null;
			}
			if(custReg.getRegenddate()!=null){
				this.regenddate = DateUtil.format(custReg.getRegenddate(), DateUtil.PATTERN_DATE); 
			}else{
				this.regenddate=null;
			}
			
			
		}
	 
	 
	}


public String getDealerid() {
	return dealerid;
}


public void setDealerid(String dealerid) {
	this.dealerid = dealerid;
}


public String getDealercode() {
	return dealercode;
}


public void setDealercode(String dealercode) {
	this.dealercode = dealercode;
}


public String getDealername() {
	return dealername;
}


public void setDealername(String dealername) {
	this.dealername = dealername;
}


public Integer getMaxregno() {
	return maxregno;
}


public void setMaxregno(Integer maxregno) {
	this.maxregno = maxregno;
}


public String getProdname() {
	return prodname;
}


public void setProdname(String prodname) {
	this.prodname = prodname;
}


public String getRegstartdate() {
	return regstartdate;
}


public void setRegstartdate(String regstartdate) {
	this.regstartdate = regstartdate;
}


public String getRegenddate() {
	return regenddate;
}


public void setRegenddate(String regenddate) {
	this.regenddate = regenddate;
}
	
}

