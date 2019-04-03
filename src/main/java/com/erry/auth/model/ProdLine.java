package com.erry.auth.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import ch.ralscha.extdirectspring.generator.ModelField;

import com.erry.ext.serializer.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * TAuUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_BPS_Productline")
public class ProdLine implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue
    @Column(name="prodid")
	private String prodid;
	
	@Column(name="prodcode")
	private String prodcode;

	@Column(name="prodname")
	private String prodname;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="active")
	private String active;

	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="prodid")
	//private List<CustProdLine> subOperUnits = new ArrayList<CustProdLine>();
	private List<MaterialInfo> materialinfos = new ArrayList<MaterialInfo>();
	
	@Transient
	private boolean  checked;
	
	public String getProdid() {
		return prodid;
	}

	public void setProdid(String prodid) {
		this.prodid = prodid;
	}

	public String getProdcode() {
		return prodcode;
	}

	public void setProdcode(String prodcode) {
		this.prodcode = prodcode;
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public List<MaterialInfo> getMaterialinfos() {
		return materialinfos;
	}

	public void setMaterialinfos(List<MaterialInfo> materialinfos) {
		this.materialinfos = materialinfos;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	

}