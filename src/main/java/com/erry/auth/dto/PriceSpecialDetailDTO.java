package com.erry.auth.dto;


import java.math.BigDecimal;

import com.erry.auth.model.PriceSpecialAudit;
import com.erry.auth.model.PriceSpecialDetail;
import com.erry.util.DateUtil;
import com.erry.util.NumberUtil;
import com.erry.util.StringUtil;

public class PriceSpecialDetailDTO {
	private String did;
	private String spid;
	private String seqno;
	private String applytype;
	private String cpid;
	private String materialid;
	private String projname;
	private String projstatus;
	private String volyear;
	private String compmaterial;
	private String currency;
	private String comppriceinc;
	private String applypriceinc;
	private String applyprice;
	private String remark;
	private String isnotrebate;
	private String active;
	private String billno;
	private String custcode;
	private String custname;
	private String materialcode;
	private String materialname;
	private String billstatus;
	private String prodname;
	private String region;
	private String dealername;
	private String isspl;
	private String sugcustsplinc;
	private String sugcustspl;
	private String activedate;
	private String assembly;
	private String lifecycle;
	private String applypriceinc1;
	private String applyprice1;
	private String applydate;
	private String lastspl;//上次特价
	private String lastsplinc;//上次特价含税
	private String lastdate;//上次审批执行时间
	private String lastsugdealerprofit;//上次利润
	private String lastcustspl;//上次执行价格
	private String lastcustsplinc;//上次执行价格含税
	private String lastcustdate;//上次执行出货日期
	private String lastquantity;//上次执行数量
	private String sugdealerspl;
	private String sugdealersplinc;
	private String sugdealerprofit;
	private String auditremark;//批复意见
	private String applycator;
	private String approver;//审批人
	private String approvedate;//审批时间
	private String istax;
	private String execqty;

	public PriceSpecialDetailDTO(){}
	public PriceSpecialDetailDTO(PriceSpecialDetailDTO price){
		this.did=price.getDid();
		this.applycator=price.getApplycator();
		//this.spid=price.getPrice().getSpid();
		this.spid=price.getSpid();
		this.seqno=price.getSeqno();
		this.applytype=price.getApplytype();
		this.cpid=price.getCpid();
		this.materialid=price.getMaterialid();
		this.projname=price.getProjname();
		this.projstatus=price.getProjstatus();
		this.volyear=price.getVolyear();
		this.compmaterial=price.getCompmaterial();
		this.currency=price.getCurrency();
		this.istax = price.getIstax();
		this.execqty=price.getExecqty();
	
		if(price.getApplyprice()!=null&&!price.getApplyprice().equals("null")){
			this.applyprice1 = StringUtil.fmtMicrometer4(price.getApplyprice());
		}
		if(price.getApplypriceinc()!=null&&!price.getApplypriceinc().equals("null")){
			this.applypriceinc1 = StringUtil.fmtMicrometer4(price.getApplypriceinc());
		}
		if(price.getComppriceinc()!=null&&!price.getComppriceinc().equals("null")){
		this.comppriceinc=StringUtil.fmtMicrometer4(price.getComppriceinc());//price.getComppriceinc().substring(0,price.getComppriceinc().indexOf(".")+4);
		}
		
		this.remark=price.getRemark();
		this.isnotrebate=price.getIsnotrebate();
		this.active=price.getActive();
		
		this.billno=price.getBillno();
		this.billstatus=price.getBillstatus();
		if(price.getApplydate()!=null&&!price.getApplydate().equals("null")){
		this.applydate = DateUtil.format(DateUtil.parse(price.getApplydate()),DateUtil.PATTERN_DATE_TIME);

		this.custcode=price.getCustcode();
		this.custname=price.getCustname();
		this.region=price.getRegion();
		this.prodname=price.getProdname();
		
		this.materialcode=price.getMaterialcode();
		this.materialname=price.getMaterialname();
		this.assembly = price.getAssembly();
		this.lifecycle = price.getLifecycle();
	
		
		this.isspl = price.getIsspl();
		this.auditremark = price.getAuditremark();
		if(price.getSugdealersplinc()!=null&&!price.getSugdealersplinc().equals("null")){
			this.sugdealersplinc = StringUtil.fmtMicrometer4(price.getSugdealersplinc());
		}
		if(price.getSugdealerspl()!=null&&!price.getSugdealerspl().equals("null")){
			this.sugdealerspl = StringUtil.fmtMicrometer4(price.getSugdealerspl());
		}
		if(price.getSugdealerprofit()!=null&&!price.getSugdealerprofit().equals("null")){
		//	this.sugdealerprofit = StringUtil.fmtMicrometer4(price.getSugdealerprofit());
			this.sugdealerprofit=NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(price.getSugdealerprofit())), 4).toString();
		}
		if(price.getSugcustsplinc()!=null&&!price.getSugcustsplinc().equals("null")){
		this.sugcustsplinc = StringUtil.fmtMicrometer4(price.getSugcustsplinc());
		}
		if(price.getActivedate()!=null&&!price.getActivedate().equals("null")){
		this.activedate = DateUtil.format(DateUtil.parse(price.getActivedate()));
		}
		if(price.getSugcustspl()!=null&&!price.getSugcustspl().equals("null")){
		this.sugcustspl=StringUtil.fmtMicrometer4(price.getSugcustspl());
		}
		if(price.getSugdealersplinc()!=null&&!price.getSugdealersplinc().equals("null")){
			this.applypriceinc=StringUtil.fmtMicrometer4(price.getApplypriceinc());//price.getApplypriceinc().substring(0,price.getApplypriceinc().indexOf(".")+4);
			}
		if(price.getSugdealerspl()!=null&&!price.getSugdealerspl().equals("null")){
		this.applyprice=StringUtil.fmtMicrometer4(price.getApplyprice());//price.getApplyprice().substring(0,price.getApplyprice().indexOf(".")+4);
		}
		if(price.getApprovedate()!=null&&!price.getApprovedate().equals("null")){
				this.approvedate = price.getApprovedate();
		}
		if(price.getLastspl()!=null&&!price.getLastspl().equals("null")){
		this.lastspl=StringUtil.fmtMicrometer4(price.getLastspl());
		}
		if(price.getLastsplinc()!=null&&!price.getLastsplinc().equals("null")){
		this.lastsplinc=StringUtil.fmtMicrometer4(price.getLastsplinc());
		}
		if(price.getLastdate()!=null&&!price.getLastdate().equals("null")){
		this.lastdate=DateUtil.format(DateUtil.parse(price.getLastdate()),DateUtil.PATTERN_DATE_TIME);
		}
		if(price.getSugdealerprofit()!=null&&!price.getSugdealerprofit().equals("null")){
	//	this.lastsugdealerprofit=StringUtil.fmtMicrometer4(price.getSugdealerprofit());
		this.lastsugdealerprofit=NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(price.getSugdealerprofit())), 4).toString();
		}
		if(price.getLastcustdate()!=null&&!price.getLastcustdate().equals("null")){
		this.lastcustdate=DateUtil.format(DateUtil.parse(price.getLastcustdate()),DateUtil.PATTERN_DATE_TIME);
		}
	if(price.getLastcustspl()!=null&&!price.getLastcustspl().equals("null")){
		this.lastcustspl=StringUtil.fmtMicrometer4(price.getLastcustspl()); 
	}
	if(price.getLastcustsplinc()!=null&&!price.getLastcustsplinc().equals("null")){
		this.lastcustsplinc=StringUtil.fmtMicrometer4(price.getLastcustsplinc());
	}
	if(price.getLastquantity()!=null&&!price.getLastquantity().equals("null")){
		this.lastquantity=price.getLastquantity();
	}
		}
	}
	public PriceSpecialDetailDTO(PriceSpecialDetail price){
		this.did=price.getDid();
		//this.spid=price.getPrice().getSpid();
		this.spid=price.getSpid();
		this.seqno=price.getSeqno();
		this.applytype=price.getApplytype();
		this.cpid=price.getCpid();
		this.materialid=price.getMaterialid();
		this.projname=price.getProjname();
		this.projstatus=price.getProjstatus();
		this.volyear=price.getVolyear();
		this.compmaterial=price.getCompmaterial();
		this.currency=price.getCurrency();
	
		if(price.getApplyprice()!=null){
			this.applyprice1 = StringUtil.fmtMicrometer4(price.getApplyprice());
		}
		if(price.getApplypriceinc()!=null){
			this.applypriceinc1 = StringUtil.fmtMicrometer4(price.getApplypriceinc());
		}
		if(price.getComppriceinc()!=null){
		this.comppriceinc=StringUtil.fmtMicrometer4(price.getComppriceinc());//price.getComppriceinc().substring(0,price.getComppriceinc().indexOf(".")+4);
		}
		
		this.remark=price.getRemark();
		this.isnotrebate=price.getIsnotrebate();
		this.active=price.getActive();
		if(price.getPriceSpecial()==null){
			this.billno=null;
			this.billstatus=null;
		}else{
			this.billno=price.getPriceSpecial().getBillno();
			this.billstatus=price.getPriceSpecial().getBillstatus();
			if(price.getPriceSpecial().getApplydate()!=null){
			this.applydate = DateUtil.format(price.getPriceSpecial().getApplydate(),DateUtil.PATTERN_DATE_TIME);
			}
		}
		if(price.getCustProdLine()==null){
			this.custcode=null;
			this.custname=null;
			this.region=null;
			this.prodname=null;
		}else{
			this.custcode=price.getCustProdLine().getCust().getCustcode();
			this.custname=price.getCustProdLine().getCust().getCustname();
			this.region=price.getCustProdLine().getCust().getRegion();
			this.prodname=price.getCustProdLine().getProd().getProdname();
		}
		if(price.getMaterialInfo()==null){
			this.materialcode=null;
			this.materialname=null;
		}else{
			this.materialcode=price.getMaterialInfo().getMaterialcode();
			this.materialname=price.getMaterialInfo().getMaterialname();
			this.assembly = price.getMaterialInfo().getAssembly();
			this.lifecycle = price.getMaterialInfo().getLifecycle();
		}
	
		if(price.getPriceSpecialAudits().size()>0){
			for(PriceSpecialAudit a : price.getPriceSpecialAudits()){
				this.isspl = a.getIsspl();
				this.auditremark = a.getAuditremark();
				if(a.getSugdealersplinc()!=null){
					this.sugdealersplinc = StringUtil.fmtMicrometer4(a.getSugdealersplinc());
				}
				if(a.getSugdealerspl()!=null){
					this.sugdealerspl = StringUtil.fmtMicrometer4(a.getSugdealerspl());
				}
				if(a.getSugdealerprofit()!=null){
					//this.sugdealerprofit = StringUtil.fmtMicrometer4(a.getSugdealerprofit());
					this.sugdealerprofit=NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(a.getSugdealerprofit())), 4).toString();
				}
				if(a.getSugcustsplinc()!=null){
				this.sugcustsplinc = StringUtil.fmtMicrometer4(a.getSugcustsplinc());
				}
				if(a.getActivedate()!=null){
				this.activedate = DateUtil.format(a.getActivedate());
				}
				if(a.getSugcustspl()!=null){
				this.sugcustspl=StringUtil.fmtMicrometer4(a.getSugcustspl());
				}
				if(a.getSugdealersplinc()!=null){
					this.applypriceinc=StringUtil.fmtMicrometer4(price.getApplypriceinc());//price.getApplypriceinc().substring(0,price.getApplypriceinc().indexOf(".")+4);
					}
				if(a.getSugdealerspl()!=null){
				this.applyprice=StringUtil.fmtMicrometer4(price.getApplyprice());//price.getApplyprice().substring(0,price.getApplyprice().indexOf(".")+4);
				}
				if(a.getApprovedate()!=null){
						this.approvedate = String.valueOf(a.getApprovedate());
				}
				if(a.getLastspl()!=null){
				this.lastspl=StringUtil.fmtMicrometer4(a.getLastspl());
				}
				if(a.getLastsplinc()!=null){
				this.lastsplinc=StringUtil.fmtMicrometer4(a.getLastsplinc());
				}
				if(a.getLastdate()!=null){
				this.lastdate=DateUtil.format(a.getLastdate(),DateUtil.PATTERN_DATE_TIME);
				}
				if(a.getSugdealerprofit()!=null){
				//this.lastsugdealerprofit=StringUtil.fmtMicrometer4(a.getSugdealerprofit());
				this.lastsugdealerprofit=NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(a.getSugdealerprofit())), 4).toString();
				
				}
				if(a.getLastcustdate()!=null){
			this.lastcustdate=DateUtil.format(DateUtil.parse(a.getLastcustdate()),DateUtil.PATTERN_DATE_TIME);
			}
			if(a.getLastcustspl()!=null){
				this.lastcustspl=StringUtil.fmtMicrometer4(a.getLastcustspl()); 
			}
			if(a.getLastcustsplinc()!=null){
				this.lastcustsplinc=StringUtil.fmtMicrometer4(a.getLastcustsplinc());
			}
			if(a.getLastquantity()!=null){
				this.lastquantity=a.getLastquantity();
			}
			}
			}		
	}
	
	public String getProdname() {
		return prodname;
	}


	public void setProdname(String prodname) {
		this.prodname = prodname;
	}


	public String getRegion() {
		return region;
	}


	public String getApplycator() {
		return applycator;
	}
	public void setApplycator(String applycator) {
		this.applycator = applycator;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getLastspl() {
		return lastspl;
	}


	public void setLastspl(String lastspl) {
		this.lastspl = lastspl;
	}


	public String getLastsplinc() {
		return lastsplinc;
	}


	public void setLastsplinc(String lastsplinc) {
		this.lastsplinc = lastsplinc;
	}


	public String getLastdate() {
		return lastdate;
	}


	public void setLastdate(String lastdate) {
		this.lastdate = lastdate;
	}


	public String getLastsugdealerprofit() {
		return lastsugdealerprofit;
	}


	public void setLastsugdealerprofit(String lastsugdealerprofit) {
		this.lastsugdealerprofit = lastsugdealerprofit;
	}


	public String getLastcustspl() {
		return lastcustspl;
	}


	public void setLastcustspl(String lastcustspl) {
		this.lastcustspl = lastcustspl;
	}


	public String getLastcustsplinc() {
		return lastcustsplinc;
	}


	public void setLastcustsplinc(String lastcustsplinc) {
		this.lastcustsplinc = lastcustsplinc;
	}


	public String getLastcustdate() {
		return lastcustdate;
	}


	public void setLastcustdate(String lastcustdate) {
		this.lastcustdate = lastcustdate;
	}


	public String getLastquantity() {
		return lastquantity;
	}


	public void setLastquantity(String lastquantity) {
		this.lastquantity = lastquantity;
	}


	public String getApplypriceinc1() {
		return applypriceinc1;
	}


	public void setApplypriceinc1(String applypriceinc1) {
		this.applypriceinc1 = applypriceinc1;
	}


	public String getApplyprice1() {
		return applyprice1;
	}


	public void setApplyprice1(String applyprice1) {
		this.applyprice1 = applyprice1;
	}

	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public String getSpid() {
		return spid;
	}
	public void setSpid(String spid) {
		this.spid = spid;
	}
	public String getSeqno() {
		return seqno;
	}
	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}
	public String getApplytype() {
		return applytype;
	}
	public void setApplytype(String applytype) {
		this.applytype = applytype;
	}
	public String getCpid() {
		return cpid;
	}
	public void setCpid(String cpid) {
		this.cpid = cpid;
	}
	public String getMaterialid() {
		return materialid;
	}
	public void setMaterialid(String materialid) {
		this.materialid = materialid;
	}
	public String getProjname() {
		return projname;
	}
	public void setProjname(String projname) {
		this.projname = projname;
	}
	public String getProjstatus() {
		return projstatus;
	}
	public void setProjstatus(String projstatus) {
		this.projstatus = projstatus;
	}
	public String getVolyear() {
		return volyear;
	}
	public void setVolyear(String volyear) {
		this.volyear = volyear;
	}
	public String getCompmaterial() {
		return compmaterial;
	}
	public void setCompmaterial(String compmaterial) {
		this.compmaterial = compmaterial;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getComppriceinc() {
		return comppriceinc;
	}
	public void setComppriceinc(String comppriceinc) {
		this.comppriceinc = comppriceinc;
	}
	public String getApplypriceinc() {
		return applypriceinc;
	}
	public void setApplypriceinc(String applypriceinc) {
		this.applypriceinc = applypriceinc;
	}
	public String getApplyprice() {
		return applyprice;
	}
	public void setApplyprice(String applyprice) {
		this.applyprice = applyprice;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsnotrebate() {
		return isnotrebate;
	}
	public void setIsnotrebate(String isnotrebate) {
		this.isnotrebate = isnotrebate;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}


	public String getBillno() {
		return billno;
	}


	public void setBillno(String billno) {
		this.billno = billno;
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


	public String getMaterialcode() {
		return materialcode;
	}


	public void setMaterialcode(String materialcode) {
		this.materialcode = materialcode;
	}


	public String getMaterialname() {
		return materialname;
	}


	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}


	public String getBillstatus() {
		return billstatus;
	}


	public void setBillstatus(String billstatus) {
		this.billstatus = billstatus;
	}


	public String getDealername() {
		return dealername;
	}


	public void setDealername(String dealername) {
		this.dealername = dealername;
	}



	public String getSugcustsplinc() {
		return sugcustsplinc;
	}


	public void setSugcustsplinc(String sugcustsplinc) {
		this.sugcustsplinc = sugcustsplinc;
	}


	public void setIsspl(String isspl) {
		this.isspl = isspl;
	}


	public String getActivedate() {
		return activedate;
	}


	public void setActivedate(String activedate) {
		this.activedate = activedate;
	}


	public String getSugcustspl() {
		return sugcustspl;
	}


	public void setSugcustspl(String sugcustspl) {
		this.sugcustspl = sugcustspl;
	}


	public String getIsspl() {
		return isspl;
	}


	public String getAssembly() {
		return assembly;
	}


	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}


	public String getLifecycle() {
		return lifecycle;
	}


	public void setLifecycle(String lifecycle) {
		this.lifecycle = lifecycle;
	}


	public String getApplydate() {
		return applydate;
	}


	public void setApplydate(String applydate) {
		this.applydate = applydate;
	}


	public String getApprovedate() {
		return approvedate;
	}


	public void setApprovedate(String approvedate) {
		this.approvedate = approvedate;
	}


	public String getSugdealerspl() {
		return sugdealerspl;
	}


	public void setSugdealerspl(String sugdealerspl) {
		this.sugdealerspl = sugdealerspl;
	}


	public String getSugdealersplinc() {
		return sugdealersplinc;
	}


	public void setSugdealersplinc(String sugdealersplinc) {
		this.sugdealersplinc = sugdealersplinc;
	}


	public String getSugdealerprofit() {
		return sugdealerprofit;
	}


	public void setSugdealerprofit(String sugdealerprofit) {
		this.sugdealerprofit = sugdealerprofit;
	}


	public String getAuditremark() {
		return auditremark;
	}


	public void setAuditremark(String auditremark) {
		this.auditremark = auditremark;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getIstax() {
		return istax;
	}

	public void setIstax(String istax) {
		this.istax = istax;
	}


	public String getExecqty() {
		return execqty;
	}

	public void setExecqty(String execqty) {
		this.execqty = execqty;
	}
}
