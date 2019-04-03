package com.erry.auth.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;


/**
 * TAuUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "T_BPS_Cust_FileInfo")
public class CustFile implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue
    @Column(name="ID")
	private String id;
	
	
	@Column(name="FileContent")
	private String fileContent;
	
	
	@Column(name="CPID")
	private String cpId;

	@Column(name="FileID")
	private String fileId;
	
	@Column(name="Remark")
	private String remark;
	
	@Column(name="Active")
	private String active;
	


}