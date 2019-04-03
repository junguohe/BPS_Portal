package com.erry.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.component.AppGrantedAuthority;
import com.erry.auth.component.AppUserDetails;
import com.erry.auth.model.AuUser;
import com.erry.auth.repository.AuUserRepository;
import com.erry.util.EncryptDecryptUtil;

@Transactional
@Service("appUserDetailService")
public class AppUserDetailService implements UserDetailsService {

	@Autowired
	private AuUserRepository auUserRepo;

	private static final Logger logger = Logger
			.getLogger(AppUserDetailService.class);

	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException, DataAccessException {
		List<AuUser> auUserList = auUserRepo.findByUsercode(arg0);
		AuUser auUser = null;
		if (!auUserList.isEmpty()) {
			auUser = auUserList.get(0);
		}

		AppUserDetails ud = new AppUserDetails();
		ud.setId(auUser.getUid());
		ud.setUsercode(auUser.getUsercode());
		ud.setUsername(auUser.getUsername());
		
		try {
			ud.setPassword(EncryptDecryptUtil.detryptByAES(auUser.getPassword()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<AppGrantedAuthority> authorities = new ArrayList<AppGrantedAuthority>();
		ud.setUser(auUser);
		ud.setAuthorities(authorities);
		return ud;
	}

}
