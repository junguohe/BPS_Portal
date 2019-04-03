package com.erry.auth.service;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.component.CurrentUserInfo;
import com.erry.auth.model.AuUser;
import com.erry.auth.model.DealerInfo;
import com.erry.auth.model.Json;
import com.erry.auth.model.UserDealer;
import com.erry.auth.repository.AuUserRepository;
import com.erry.auth.repository.DealerInfoRepository;
import com.erry.auth.repository.UserDealerRepostory;
import com.erry.util.DateUtil;
import com.erry.util.EncryptDecryptUtil;
import com.erry.util.GuidIdUtil;

@Service
@Transactional
public class AuUserSeviceImpl {
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	private AuUserRepository auUserRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private UserDealerRepostory dealerRepostory;

	@Autowired
	private DealerInfoRepository dealerinforepository;
	
	
	
	
	public Json endUser(String userid){
		Json json = new Json();
		String msg = "用户已失效！";
		boolean flg = true;
		AuUser user=auUserRepository.findByUid(userid);
		if(user!=null){
			user.setExpiredate(new Date());
		}
	    UserDealer userdealer=dealerRepostory.findByUserId(user.getUid());
	    DealerInfo dealer =dealerinforepository.findByid(userdealer.getDealerid());
	    if(dealer!=null){
	    	dealer.setActive("0");
	    	entityManager.merge(dealer);
	    }
		entityManager.merge(user);
		json.setData(user);
		json.setMsg(msg);
		json.setSuccess(flg);
		return json;
	}
	
	public Json updatePasswordNew(String userid){
		Json json = new Json();
		String msg = "密码重置成功！";
		boolean flg = true;
		AuUser user=auUserRepository.findByUid(userid);
		if(user!=null){
			try {
				user.setPassword(EncryptDecryptUtil.encryptByAES(GuidIdUtil.getPassword()));
				UserDealer dealer = dealerRepostory.findByUserId(userid);
				mailService.createFlushUserEmail(dealer, user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		entityManager.merge(user);
		json.setData(user);
		json.setMsg(msg);
		json.setSuccess(flg);
		return json;
	}
	
	/**
	 *修改密码
	 */
	public Json updateAuUserPassWord(String password,String newpassword) throws Exception {
		Json json = new Json();
		String msg = "修改成功！请重新登陆";
		boolean flg = true;
		AuUser user=CurrentUserInfo.getUser();
		if(user!=null){
				if (user.getPassword().equals(EncryptDecryptUtil.encryptByAES(password))) {
					user.setPassword("");
				} else { 
					flg = false;
					msg ="用户原始密码错误，请重新输入。";
				}
		}
		user.setPassword(EncryptDecryptUtil.encryptByAES(newpassword));
		user.setUpdator(CurrentUserInfo.getUid());
		user.setUpdatedate(new Date());
		entityManager.merge(user);
		json.setData(user);
		json.setMsg(msg);
		json.setSuccess(flg);
		return json;
	}
	
	public Json saveAuUser(AuUser au){
		Json json = new Json();
		String result = "新增成功！";
		boolean flag = true;
		AuUser user = auUserRepository.save(au);
		//user.getUserDealer();
		if(user==null){
			result = "新增失败！";
			flag= false;
		}
		json.setData(user);
		json.setMsg(result);
		json.setSuccess(flag);
		return json;
	}
	
	public AuUser createLoginUser(String loginname,String name) throws Exception{
		AuUser au = new AuUser();
		au.setActive("1");
		au.setCreatedate(new Date());
		au.setCreator(CurrentUserInfo.getUid());
		au.setPassword(EncryptDecryptUtil.encryptByAES(GuidIdUtil.getPassword()));
		au.setUid(GuidIdUtil.getGuId());
		au.setUpdatedate(new Date());
		au.setUpdator(CurrentUserInfo.getUid());
		au.setUsercode(loginname);
		au.setUsername(name);
		au.setExpiredate(null);
		au.setUsertype("1");
		au=auUserRepository.save(au);
		return au;
	}
	
}
