package com.erry.auth.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import ch.ralscha.extdirectspring.generator.ModelField;

import com.erry.ext.serializer.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "t_bps_email")
public class BPSMail {
	@Id
	@GeneratedValue
    @Column(name="id")
	private String id;
	
	@Column(name="mailcategory")
	private String mailcategory;
	
	@Column(name="sender")
	private String sender;
	
	@Column(name="[to]")
	private String to;
	
	@Column(name="cc")
	private String cc;
	
	@Column(name="subject")
	private String subject;
	
	@Column(name="content")
	private String content;
	
	@Column(name="createtime")
	@ModelField(dateFormat = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date createtime;
	
	@Column(name="lastsenttime")
	@ModelField(dateFormat = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = JsonDateSerializer.class)
	private Date lastsenttime;
	
	@Column(name="status")
	private String status;
	
	@Column(name="retrycount")
	private String retrycount;

	@Column(name="errrormsg")
	private String errrormsg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMailcategory() {
		return mailcategory;
	}

	public void setMailcategory(String mailcategory) {
		this.mailcategory = mailcategory;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getLastsenttime() {
		return lastsenttime;
	}

	public void setLastsenttime(Date lastsenttime) {
		this.lastsenttime = lastsenttime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRetrycount() {
		return retrycount;
	}

	public void setRetrycount(String retrycount) {
		this.retrycount = retrycount;
	}

	public String getErrrormsg() {
		return errrormsg;
	}

	public void setErrrormsg(String errrormsg) {
		this.errrormsg = errrormsg;
	}
	
	
}
