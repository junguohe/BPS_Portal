package com.erry.auth.component;

import org.springframework.security.core.GrantedAuthority;

public class AppGrantedAuthority implements GrantedAuthority {
	
	private static final long serialVersionUID = 1L;
	
	private String authority = null;
	
	public AppGrantedAuthority(String auth) {
		authority = auth;
	}
	
	public String getAuthority() {
		return authority;
	}
	
}
