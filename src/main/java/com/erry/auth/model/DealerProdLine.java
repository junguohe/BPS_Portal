package com.erry.auth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TAuUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bps_dealer_prodline")
public class DealerProdLine implements java.io.Serializable {


	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="id")
	private String id;
	
	@Column(name="dealerid")
	private String dealerid;
	
	@Column(name="prodid")
	private String prodid;
	
	@Column(name="regmax")
	private String regmax;
	
	@Column(name="active")
	private String active;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getDealerid() {
		return dealerid;
	}


	public void setDealerid(String dealerid) {
		this.dealerid = dealerid;
	}


	public String getProdid() {
		return prodid;
	}


	public void setProdid(String prodid) {
		this.prodid = prodid;
	}


	public String getActive() {
		return active;
	}


	public void setActive(String active) {
		this.active = active;
	}


	public String getRegmax() {
		return regmax;
	}


	public void setRegmax(String regmax) {
		this.regmax = regmax;
	}





	

	


}