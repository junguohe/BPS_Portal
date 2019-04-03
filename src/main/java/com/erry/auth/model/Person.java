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
@Table(name = "erp_person")
public class Person implements java.io.Serializable {


	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="per_id")
	private String id;
	
	@Column(name="per_name")
	private String per_name;
	
	@Column(name="name")
	private String name;
	
	
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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getPer_name() {
		return per_name;
	}

	public void setPer_name(String per_name) {
		this.per_name = per_name;
	}

	

	


}