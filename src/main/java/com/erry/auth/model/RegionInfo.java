package com.erry.auth.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_BPS_MD_Region")
public class RegionInfo implements Serializable {
	@Id
//	@GeneratedValue
	@Column(name = "id")
	private String id;

	@Column(name = "Province")
	private String Province;

	@Column(name = "city")
	private String city;

	@Column(name = "active")
	private String active;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvince() {
		return Province;
	}

	public void setProvince(String province) {
		Province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}


}
