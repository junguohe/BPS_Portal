package com.erry.auth.dto;



import java.math.BigDecimal;

import com.erry.auth.model.PriceSpecialAudit;
import com.erry.auth.model.PriceSpecialDetail;
import com.erry.util.DateUtil;
import com.erry.util.NumberUtil;
import com.erry.util.StringUtil;
public class PriceSpecialAuditDTO2 {
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
		private String currencys;
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
		private String sugcustsplinc;
		private String approveresult;
		private String approver;
		private String activedate;
		private String splno;
		private String applydate;
		private String bpssales;//销售
		private String lastspl;//上次特价
		private String lastsplinc;//上次特价含税
		private String lastdate;//上次审批执行时间
		private String lastsugdealerprofit;//上次利润
		private String lastcustspl;//上次执行价格
		private String lastcustsplinc;//上次执行价格含税
		private String lastcustdate;//上次执行出货日期
		private String lastquantity;//上次执行数量
		//修改审批信息
		private String auditremark;//审批意见
		private String isrebate;//特价返货
		private String isspl;//特价出货
		private String sugdealerprofit;//经销商利润
		private String sugdealerspl;//建议经销商成本价（未税）
		private String sugdealersplinc;//建议经销商成本价（含税）
		private String sugcustspl;//建议客户  出货价格（未税
		private String istax;  //是否含税
		private String execqty;//一次性特价的执行数量
		
		public PriceSpecialAuditDTO2(PriceSpecialAudit price){
			this.bpssales=price.getBpssales();
			this.did=price.getDid();
			this.spid=price.getSpid();
			PriceSpecialDetail p=price.getPriceSpecialDetail();
			this.projname=p.getProjname();
			this.projstatus=p.getProjstatus();
			this.volyear=p.getVolyear();
			this.splno = price.getSplno();
			this.execqty=p.getExecqty();

			this.auditremark = price.getAuditremark();
			this.isrebate = price.getIsrebate();
			this.isspl = price.getIsspl();
			if(price.getSugdealerprofit()!=null){
				this.sugdealerprofit = String.valueOf(Double.parseDouble(price.getSugdealerprofit())*100);
			}
			if(price.getSugdealerspl()!=null){
				this.sugdealerspl = price.getSugdealerspl();
			}
			if(price.getSugdealersplinc()!=null){
				this.sugdealersplinc = price.getSugdealersplinc();
			}
			if(price.getSugcustspl()!=null){
				this.sugcustspl = price.getSugcustspl();
			}
			if(price.getActivedate()!=null){
				this.activedate = DateUtil.format(price.getActivedate());
			}
			if(price.getLastspl()!=null){
				this.lastspl=StringUtil.fmtMicrometer4(price.getLastspl());
			}
			if(price.getLastsplinc()!=null){
				this.lastsplinc=StringUtil.fmtMicrometer4(price.getLastsplinc());
			}
			if(price.getLastdate()!=null){
				this.lastdate=DateUtil.format(price.getLastdate(),DateUtil.PATTERN_DATE_TIME);
			}
			if(price.getSugdealerprofit()!=null){
				double profit = 0;
				if(price.getLastcustspl()!=null&&price.getLastsplinc()!=null){
					double cust = Double.parseDouble(price.getLastcustspl());
					double dealer = Double.parseDouble(price.getLastsplinc());
					profit=((cust-dealer)/dealer)*100;
				}
			//	this.lastsugdealerprofit=StringUtil.fmtMicrometer4(String.valueOf(profit));
				this.lastsugdealerprofit =NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(profit)), 4).toString();
				
			}
			if(price.getLastcustdate()!=null){
				this.lastcustdate=DateUtil.format(DateUtil.parse(price.getLastcustdate()),DateUtil.PATTERN_DATE_TIME);
			}
			if(price.getLastcustspl()!=null){
				this.lastcustspl=StringUtil.fmtMicrometer4(price.getLastcustspl()); 
			}
			if(price.getLastcustsplinc()!=null){
				this.lastcustsplinc=StringUtil.fmtMicrometer4(price.getLastcustsplinc());
			}
			if(price.getLastquantity()!=null){
				this.lastquantity=price.getLastquantity();
			}
			this.approveresult = price.getApproveresult();
			if(price.getSugcustsplinc()!=null){
				this.sugcustsplinc=StringUtil.fmtMicrometer4(price.getSugcustsplinc());//p.getApplyprice().substring(0,p.getApplyprice().indexOf(".")+4);
			}
			if(p!=null){
				this.seqno=p.getSeqno();
				this.applytype=p.getApplytype();
				this.cpid=p.getCpid();
				this.materialid=p.getMaterialid();
				this.compmaterial=p.getCompmaterial();
				this.currency=p.getCurrency();
				if(p.getComppriceinc()!=null){
					this.comppriceinc=StringUtil.fmtMicrometer4(p.getComppriceinc());//p.getComppriceinc().substring(0,p.getComppriceinc().indexOf(".")+4);
				}
				if(p.getApplypriceinc()!=null){
					this.applypriceinc=StringUtil.fmtMicrometer4(p.getApplypriceinc());//p.getApplypriceinc().substring(0,p.getApplypriceinc().indexOf(".")+4);
				}
				if(p.getApplyprice()!=null){
					this.applyprice=StringUtil.fmtMicrometer4(p.getApplyprice());//p.getApplyprice().substring(0,p.getApplyprice().indexOf(".")+4);
				}
				
				this.remark=p.getRemark();
				this.isnotrebate=p.getIsnotrebate();
				if(p.getCustProdLine()==null){
					this.custcode=null;
					this.custname=null;
					this.region=null;
					this.prodname=null;
				}else{
					this.custcode=p.getCustProdLine().getCust().getCustcode();
					this.custname=p.getCustProdLine().getCust().getCustname();
					this.region=p.getCustProdLine().getCust().getRegion();
					this.prodname=p.getCustProdLine().getProd().getProdname();
//					if(p.getCustProdLine().getCustbps()!=null){
//						this.bpssales = "";
//					for(CustBPS bps :p.getCustProdLine().getCustbps()){
//						if(StringUtil.isEmpty(bps.getBpssales())){
//							if(!StringUtil.isEmpty(bps.getBpssales())){
//								this.bpssales = bps.getBpssales().trim();
//							}
//						}
////					}
//					}
				}
				if(p.getMaterialInfo()==null){
					this.materialcode=null;
					this.materialname=null;
				}else{
					this.materialcode=p.getMaterialInfo().getMaterialcode();
					this.materialname=p.getMaterialInfo().getMaterialname();
				}
			}
		
			this.currencys=price.getCurrency();
			this.active=price.getActive();
			if(price.getPriceSpecial()==null){
				this.billno=null;
				this.billstatus=null;
			}else{
				this.billno=price.getPriceSpecial().getBillno();
				this.billstatus=price.getPriceSpecial().getBillstatus();
				this.applydate = DateUtil.format(price.getPriceSpecial().getApplydate(),DateUtil.PATTERN_DATE_TIME);
				this.setIstax(price.getPriceSpecial().getIstax()==null?"":price.getPriceSpecial().getIstax());
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


		public void setRegion(String region) {
			this.region = region;
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


		public String getApproveresult() {
			return approveresult;
		}


		public void setApproveresult(String approveresult) {
			this.approveresult = approveresult;
		}


		public String getApprover() {
			return approver;
		}


		public void setApprover(String approver) {
			this.approver = approver;
		}


		public String getActivedate() {
			return activedate;
		}


		public void setActivedate(String activedate) {
			this.activedate = activedate;
		}


		public String getSplno() {
			return splno;
		}


		public void setSplno(String splno) {
			this.splno = splno;
		}


		public String getApplydate() {
			return applydate;
		}


		public void setApplydate(String applydate) {
			this.applydate = applydate;
		}


		public String getBpssales() {
			return bpssales;
		}


		public void setBpssales(String bpssales) {
			this.bpssales = bpssales;
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


		public String getAuditremark() {
			return auditremark;
		}


		public void setAuditremark(String auditremark) {
			this.auditremark = auditremark;
		}


		public String getIsrebate() {
			return isrebate;
		}


		public void setIsrebate(String isrebate) {
			this.isrebate = isrebate;
		}


		public String getIsspl() {
			return isspl;
		}


		public void setIsspl(String isspl) {
			this.isspl = isspl;
		}


		public String getSugdealerprofit() {
			return sugdealerprofit;
		}


		public void setSugdealerprofit(String sugdealerprofit) {
			this.sugdealerprofit = sugdealerprofit;
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


		public String getSugcustspl() {
			return sugcustspl;
		}


		public void setSugcustspl(String sugcustspl) {
			this.sugcustspl = sugcustspl;
		}


		public String getCurrencys() {
			return currencys;
		}


		public void setCurrencys(String currencys) {
			this.currencys = currencys;
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
