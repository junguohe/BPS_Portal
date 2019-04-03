package com.erry.auth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "T_BPS_Materialinfo")
public class MaterialInfo implements Serializable {
	@Id
	// @GeneratedValue
	@Column(name = "materialid")
	private String id;

	@Column(name = "materialcode")
	private String materialcode;

	@Column(name = "materialname")
	private String materialname;

	@Column(name = "lifecycle")
	private String lifecycle;

	@Column(name = "remark")
	private String remark;
	
	@Column(name="prodid")
	private String prodid;
	
	@Column(name = "asstype")
	private String asstype;

	@Column(name = "assembly")
	private String assembly;

	@Column(name = "active")
	private String active;
	
	
	
	@JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="prodid",nullable = false, updatable = false, insertable = false)
    private ProdLine prod;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public ProdLine getProd() {
		return prod;
	}

	public void setProd(ProdLine prod) {
		this.prod = prod;
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

	
}
