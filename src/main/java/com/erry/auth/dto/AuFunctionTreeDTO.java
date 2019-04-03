package com.erry.auth.dto;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class AuFunctionTreeDTO {


	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private Long id;

	private String text;
	
	private String code;
	
	private boolean expanded;

	private boolean leaf;
	
	private String icon;
	
	private String path;
	
//	private boolean selected;
	
	private boolean checked;
	
	private Long parentId;
	
	private Set<AuFunctionTreeDTO> children = new LinkedHashSet<AuFunctionTreeDTO>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	/*public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}*/
	

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

	public Set<AuFunctionTreeDTO> getChildren() {
		return children;
	}

	public void setChildren(Set<AuFunctionTreeDTO> children) {
		this.children = children;
	}
	public void appendChild(AuFunctionTreeDTO childToAppend){
		this.children.add(childToAppend);
		this.setLeaf(false);
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	

	
}
