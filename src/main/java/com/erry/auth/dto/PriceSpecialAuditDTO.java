package com.erry.auth.dto;

import com.erry.auth.model.PriceSpecialAudit;
import com.erry.util.DateUtil;

public class PriceSpecialAuditDTO {
private String CustCode;
private String CustName;
private String BillStatus;
private String MaterialCode;
private String MaterialName;
private String ProjName;
private String ProjStatus;
private String VolYear;
private String CompMaterial;
private String CompPriceInc;
private String ApplyPriceInc;
private String ApplyPrice;
//private String Remark;
private String auditremark;
private String ApproveResult;
private String Approver;
private String ApproveDate;
private String BillNo;
private String ActiveDate;
public PriceSpecialAuditDTO(){}
public PriceSpecialAuditDTO(PriceSpecialAudit p){
	if(p.getPriceSpecialDetail()!=null){
		if(p.getPriceSpecialDetail().getCustProdLine()!=null){
			if(p.getPriceSpecialDetail().getCustProdLine().getCust()!=null){
				this.CustCode = p.getPriceSpecialDetail().getCustProdLine().getCust().getCustcode();
				this.CustName = p.getPriceSpecialDetail().getCustProdLine().getCust().getCustname();
			}
		}
		if(p.getPriceSpecialDetail().getMaterialInfo()!=null){
			this.MaterialCode = p.getPriceSpecialDetail().getMaterialInfo().getMaterialcode();
			this.MaterialName = p.getPriceSpecialDetail().getMaterialInfo().getMaterialname();
		}
		this.CompMaterial = p.getPriceSpecialDetail().getCompmaterial();
		this.CompPriceInc = p.getPriceSpecialDetail().getComppriceinc();
		this.ApplyPriceInc = p.getPriceSpecialDetail().getApplypriceinc();
		this.ApplyPrice = p.getPriceSpecialDetail().getActive();
	}
	if(p.getPriceSpecial()!=null){
		this.BillStatus = p.getPriceSpecial().getBillstatus();
		this.BillNo = p.getPriceSpecial().getBillno();
	}
	this.auditremark = p.getAuditremark();
	this.ApproveResult = p.getApproveresult();
	this.Approver = p.getApprover();
	this.ApproveDate = DateUtil.format(p.getActivedate());
	this.ProjName = p.getProjname();
	this.ProjStatus = p.getProjstatus();
	this.VolYear = p.getVolyear();
	this.ActiveDate = DateUtil.format(p.getActivedate());
	 
}
public String getCustCode() {
	return CustCode;
}
public void setCustCode(String custCode) {
	CustCode = custCode;
}
public String getCustName() {
	return CustName;
}
public void setCustName(String custName) {
	CustName = custName;
}
public String getBillStatus() {
	return BillStatus;
}
public void setBillStatus(String billStatus) {
	BillStatus = billStatus;
}
public String getMaterialCode() {
	return MaterialCode;
}
public void setMaterialCode(String materialCode) {
	MaterialCode = materialCode;
}
public String getMaterialName() {
	return MaterialName;
}
public void setMaterialName(String materialName) {
	MaterialName = materialName;
}
public String getProjName() {
	return ProjName;
}
public void setProjName(String projName) {
	ProjName = projName;
}
public String getProjStatus() {
	return ProjStatus;
}
public void setProjStatus(String projStatus) {
	ProjStatus = projStatus;
}
public String getVolYear() {
	return VolYear;
}
public void setVolYear(String volYear) {
	VolYear = volYear;
}
public String getCompMaterial() {
	return CompMaterial;
}
public void setCompMaterial(String compMaterial) {
	CompMaterial = compMaterial;
}
public String getCompPriceInc() {
	return CompPriceInc;
}
public void setCompPriceInc(String compPriceInc) {
	CompPriceInc = compPriceInc;
}
public String getApplyPriceInc() {
	return ApplyPriceInc;
}
public void setApplyPriceInc(String applyPriceInc) {
	ApplyPriceInc = applyPriceInc;
}
public String getApplyPrice() {
	return ApplyPrice;
}
public void setApplyPrice(String applyPrice) {
	ApplyPrice = applyPrice;
}
//public String getRemark() {
//	return Remark;
//}
//public void setRemark(String remark) {
//	Remark = remark;
//}
public String getApproveResult() {
	return ApproveResult;
}
public void setApproveResult(String approveResult) {
	ApproveResult = approveResult;
}
public String getApprover() {
	return Approver;
}
public void setApprover(String approver) {
	Approver = approver;
}
public String getApproveDate() {
	return ApproveDate;
}
public void setApproveDate(String approveDate) {
	ApproveDate = approveDate;
}
public String getBillNo() {
	return BillNo;
}
public void setBillNo(String billNo) {
	BillNo = billNo;
}
public String getAuditremark() {
	return auditremark;
}
public void setAuditremark(String auditremark) {
	this.auditremark = auditremark;
}
public String getActiveDate() {
	return ActiveDate;
}
public void setActiveDate(String activeDate) {
	ActiveDate = activeDate;
}


}
