package com.erry.auth.dto;

import javax.persistence.Column;

import com.erry.auth.model.MaterialInfo;


public class MaterialInfoDTO {
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String materialcode;
	private String materialname;
	private String lifecycle;
	private String remark;
	private String prodid;
	private String prodname;
	private String asstype;
	private String assembly;
	private String active;
	private String listprice;
	
	public String getListprice() {
		return listprice;
	}
	public void setListprice(String listprice) {
		this.listprice = listprice;
	}
	public MaterialInfoDTO(){}
	
	public MaterialInfoDTO(MaterialInfo m){
		this.lifecycle = m.getLifecycle();
		this.materialcode = m.getMaterialcode();
		this.id = m.getId();
		this.materialname = m.getMaterialname();
		this.prodid = m.getProdid();
		this.remark = m.getRemark();
		this.asstype = m.getAsstype();
		this.assembly = m.getAssembly();
		this.active = m.getActive();
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
	public String getLifecycle() {
		return lifecycle;
	}
	public void setLifecycle(String lifecycle) {
		this.lifecycle = lifecycle;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProdid() {
		return prodid;
	}
	public void setProdid(String prodid) {
		this.prodid = prodid;
	}
	public String getAsstype() {
		return asstype;
	}
	public void setAsstype(String asstype) {
		this.asstype = asstype;
	}
	public String getAssembly() {
		return assembly;
	}
	public void setAssembly(String assembly) {
		this.assembly = assembly;
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
	
}
