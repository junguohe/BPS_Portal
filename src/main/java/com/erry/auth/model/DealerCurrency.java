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
@Table(name = "bps_dealer_currency")
public class DealerCurrency implements java.io.Serializable {


	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="id")
	private String id;
	
	@Column(name="dealerid")
	private String dealerid;
	
	@Column(name="currentyid")
	private String currentyid;
	
	
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


	public String getCurrentyid() {
		return currentyid;
	}


	public void setCurrentyid(String currentyid) {
		this.currentyid = currentyid;
	}


	public String getActive() {
		return active;
	}


	public void setActive(String active) {
		this.active = active;
	}



	

	


}