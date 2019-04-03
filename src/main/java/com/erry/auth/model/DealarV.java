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
@Table(name = "erp_dealar_v")
public class DealarV implements java.io.Serializable {


	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="id")
	private String id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="salesnumber")
	private String salesnumber;
	
	@Column(name="femail")
	private String femail;
	
	@Column(name="active")
	private String active;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSalesnumber() {
		return salesnumber;
	}

	public void setSalesnumber(String salesnumber) {
		this.salesnumber = salesnumber;
	}

	public String getFemail() {
		return femail;
	}

	public void setFemail(String femail) {
		this.femail = femail;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}



	

	


}