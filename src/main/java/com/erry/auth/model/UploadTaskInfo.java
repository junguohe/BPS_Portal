package com.erry.auth.model;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import ch.ralscha.extdirectspring.generator.ModelField;

import com.erry.ext.serializer.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@Entity
@Table(name = "t_bps_upload_taskinfo")
public class UploadTaskInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="tid")
	private String id;
	
	@Column(name="taskseq")
	private String taskseq;

	@Column(name="taskowner")
	private String taskowner;
	
	@Column(name="period")
	private String period;
	
	@Column(name="taskcontent")
	private String taskcontent;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="taskconfirm")
	private String taskconfirm;
	
	@Column(name="active")
	private String active;
	
	@Column(name="creator")
	private String creator;
	
	@JsonIgnore
	   @ManyToOne(fetch=FetchType.LAZY, optional = false)
	   @JoinColumn(name="creator",nullable = false, updatable = false, insertable = false)
	   private DealerInfo dealer;
	
	@Column(name="createdate")
	@ModelField(dateFormat = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date createdate;
	
	@Column(name="updator")
	private String updator;
	
	@Column(name="updatedate")
	@ModelField(dateFormat = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date updatedate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskseq() {
		return taskseq;
	}

	public void setTaskseq(String taskseq) {
		this.taskseq = taskseq;
	}

	public String getTaskowner() {
		return taskowner;
	}

	public void setTaskowner(String taskowner) {
		this.taskowner = taskowner;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getTaskcontent() {
		return taskcontent;
	}

	public void setTaskcontent(String taskcontent) {
		this.taskcontent = taskcontent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTaskconfirm() {
		return taskconfirm;
	}

	public void setTaskconfirm(String taskconfirm) {
		this.taskconfirm = taskconfirm;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public DealerInfo getDealer() {
		return dealer;
	}

	public void setDealer(DealerInfo dealer) {
		this.dealer = dealer;
	}


}