package com.erry.auth.dto;

import com.erry.auth.model.CustProjectInfo;
import com.erry.util.DateUtil;

public class CustProjectInfoDTO {
	private String id;
	private String cpid;
	private String materialid;
	private String projtype;
	private String projname;
	private String compname;
	private String compprod;
	private String shipvol;
	private String massproddate;
	private String active;
	private String creator;
	private String createdate;
	private String updator;
	private String updatedate;
	private String prodname;
	private String remark;
	
	public CustProjectInfoDTO(CustProjectInfo info){
		this.id = info.getId();
		this.cpid = info.getCpid();
		this.materialid = info.getMaterialid();
		this.projtype = info.getProjtype();
		this.projname = info.getProjname();
		this.compname = info.getCompname();
		this.shipvol = info.getShipvol();
		this.massproddate= info.getMassproddate();
		this.active= info.getActive();
		if(info.getCreatedate()!=null){
			this.createdate = DateUtil.format(info.getCreatedate(), DateUtil.PATTERN_DATE); 
		}else{
			this.createdate=null;
		}
		if(info.getUpdatedate()!=null){
			this.updatedate = DateUtil.format(info.getUpdatedate(), DateUtil.PATTERN_DATE); 
		}else{
			this.updatedate=null;
		}
		//this.createdate=DateUtil.format(info.getCreatedate(),DateUtil.PATTERN_DATE);
		this.creator= info.getCreator();
		this.updator = info.getUpdator();
	//this.updatedate= DateUtil.format(info.getUpdatedate(),DateUtil.PATTERN_DATE);
		this.prodname = info.getCustprodline().getProd().getProdname();
		this.remark=info.getRemark();
		this.compprod=info.getCompprod();
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

	public String getMaterialid() {
		return materialid;
	}

	public void setMaterialid(String materialid) {
		this.materialid = materialid;
	}

	public String getProjtype() {
		return projtype;
	}

	public void setProjtype(String projtype) {
		this.projtype = projtype;
	}

	public String getProjname() {
		return projname;
	}

	public void setProjname(String projname) {
		this.projname = projname;
	}

	public String getCompname() {
		return compname;
	}

	public void setCompname(String compname) {
		this.compname = compname;
	}

	public String getCompprod() {
		return compprod;
	}

	public void setCompprod(String compprod) {
		this.compprod = compprod;
	}

	public String getShipvol() {
		return shipvol;
	}

	public void setShipvol(String shipvol) {
		this.shipvol = shipvol;
	}

	public String getMassproddate() {
		return massproddate;
	}

	public void setMassproddate(String massproddate) {
		this.massproddate = massproddate;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
