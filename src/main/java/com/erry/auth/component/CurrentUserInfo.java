package com.erry.auth.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.erry.auth.model.AuUser;
import com.erry.common.GlobalConstant;
import com.erry.common.SessionTimeOutException;
import com.erry.util.MessageContextHolder;

public class CurrentUserInfo {
	
	public static UserDetails getUserDetails() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal == null || principal instanceof String)
			throw new SessionTimeOutException(MessageContextHolder.getMessage(GlobalConstant.TimeOut_MessageCode, null , Locale.CHINA));
		return (UserDetails) principal;
	}
	
	public static AuUser getCurUser() {
		AppUserDetails userDetails = (AppUserDetails) getUserDetails();
		return userDetails.getUser();
	}
	
	public static AuUser getUser() {
		return getCurUser();
	}
	
	public static String findUserId() {
		AppUserDetails s = ((AppUserDetails) getUserDetails());
		return s.getId();
	}
	
//	public static String getDealerid() {
//		if(getUser().getUsertype().equals("1")){
//			return getUser().getUserDealer().getDealerid();
//		}else{
//			return getUser().getUserbps().getId();
//		}
//		
//	}
//	public static String getBPSid() {
//		return getUser().getUserbps().getId();
//	}
//	
	public static String getUid() {
//		if(getUser().getUsertype().equals("0")){
			return getUser().getUid();
//		}
//		else{
//			return null;
			//return getUser().getUserbps().getId();
//		}
		
	}
	public static String getUsercode() {
		return getUser().getUsercode();
	}
	
	public static String getUserName() {
		return getUser().getUsername();
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getAuthorities() {
		Collection<GrantedAuthority> gaList = (Collection<GrantedAuthority>) getUserDetails().getAuthorities();
		List<String> list = new ArrayList<String>();
		for (GrantedAuthority ga : gaList)
			list.add(ga.getAuthority());		
		return list;
	}
	
}
