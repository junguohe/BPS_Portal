package com.erry.auth.dto;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserRelateDTO {

	private Long id;
	
	private String username;
	
	private String realname;
	
	private String password;
	
	private Long positionId;
	
	private String orginalName;
	
	private Boolean valid;
	
	private Long groupId;
	
	private Integer userType;
	
	private List<Long> departments;
	
	private List<Long> bos;
	
	private List<Long> ros;
	
	private Date createdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}
	
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public List<Long> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Long> departments) {
		this.departments = departments;
	}

	public List<Long> getBos() {
		return bos;
	}

	public void setBos(List<Long> bos) {
		this.bos = bos;
	}

	public List<Long> getRos() {
		return ros;
	}

	public void setRos(List<Long> ros) {
		this.ros = ros;
	}
	
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getOrginalName() {
		return orginalName;
	}

	public void setOrginalName(String orginalName) {
		this.orginalName = orginalName;
	}
	
}
