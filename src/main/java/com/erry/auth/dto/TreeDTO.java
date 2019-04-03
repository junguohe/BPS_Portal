package com.erry.auth.dto;




import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.erry.auth.model.Contact;
import com.erry.util.DateUtil;
public class TreeDTO {
	
@SuppressWarnings("unused")
private static final long serialVersionUID = 1L;

private String id;

private String text;

private String remark;

private String code;

private boolean expanded;

private boolean leaf;

//private String icon;

private String path;
	
private boolean checked;

private Long parentId;

private Set<TreeDTO> children = new LinkedHashSet<TreeDTO>();

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}


public String getPath() {
	return path;
}

public void setPath(String path) {
	this.path = path;
}

public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}

public String getCode() {
	return code;
}

public void setCode(String code) {
	this.code = code;
}

public boolean isExpanded() {
	return expanded;
}

public void setExpanded(boolean expanded) {
	this.expanded = expanded;
}

public boolean isLeaf() {
	return leaf;
}

public void setLeaf(boolean leaf) {
	this.leaf = leaf;
}	

public Long getParentId() {
	return parentId;
}

public boolean isChecked() {
	return checked;
}

public void setChecked(boolean checked) {
	this.checked = checked;
}

public void setParentId(Long parentId) {
	this.parentId = parentId;
}

public Set<TreeDTO> getChildren() {
	return children;
}

public void setChildren(Set<TreeDTO> children) {
	this.children = children;
}
public void appendChild(TreeDTO childToAppend){
	this.children.add(childToAppend);
	this.setLeaf(false);
}

public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
}

public String getRemark() {
	return remark;
}

public void setRemark(String remark) {
	this.remark = remark;
}

//public String getIcon() {
//	return icon;
//}
//
//public void setIcon(String icon) {
//	this.icon = icon;
//}
}

