package com.erry.auth.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.erry.auth.model.AuUser;

public class AppUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String usercode;
	
	private String username;
	
	private String password;
	
	private boolean isAccountNonExpired = true;
	
	private boolean isAccountNonLocked = true;
	
	private boolean isCredentialsNonExpired = true;
	
	private boolean isEnabled = true;
	
	private AuUser user;
	
	private List<AppGrantedAuthority> authorities = new ArrayList<AppGrantedAuthority>();
	
	public void setId(String uid) {
		this.id = uid;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setAuthorities(List<AppGrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void addAuthorities(AppGrantedAuthority authority) {
		this.authorities.add(authority);
	}

	public String getId() {
		return id;
	}

	public String getUsercode() {
		return usercode;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	public boolean isEnabled() {
		return isEnabled;
	}


	public Collection<AppGrantedAuthority> getAuthorities() {
		return authorities;
	}

	public AuUser getUser() {
		return user;
	}

	public void setUser(AuUser user) {
		this.user = user;
	}

}
