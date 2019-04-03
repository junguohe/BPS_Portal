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
@Table(name = "erp_area_v")
public class ProdPerson implements java.io.Serializable {


	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="id")
	private String id;
	
	@Column(name="area_id")
	private String area_id;
	
	@Column(name="area")
	private String area;
	
	@Column(name="mgr_id")
	private String mgr_id;
	
	@Column(name="mgr")
	private String mgr;
	
	@Column(name="fae_id")
	private String fae_id;
	
	@Column(name="faemgr")
	private String faemgr;
	
	@Column(name="area_directory_id")
	private String area_directory_id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArea_id() {
		return area_id;
	}

	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getMgr_id() {
		return mgr_id;
	}

	public void setMgr_id(String mgr_id) {
		this.mgr_id = mgr_id;
	}

	public String getMgr() {
		return mgr;
	}

	public void setMgr(String mgr) {
		this.mgr = mgr;
	}

	public String getFae_id() {
		return fae_id;
	}

	public void setFae_id(String fae_id) {
		this.fae_id = fae_id;
	}

	public String getFaemgr() {
		return faemgr;
	}

	public void setFaemgr(String faemgr) {
		this.faemgr = faemgr;
	}

	public String getArea_directory_id() {
		return area_directory_id;
	}

	public void setArea_directory_id(String area_directory_id) {
		this.area_directory_id = area_directory_id;
	}

	

	

	


}