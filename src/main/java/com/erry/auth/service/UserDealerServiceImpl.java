package com.erry.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.dto.UserDealerDTO;
import com.erry.auth.model.AuUser;
import com.erry.auth.model.DealerInfo;
import com.erry.auth.model.UserDealer;
import com.erry.auth.repository.AuUserRepository;
import com.erry.auth.repository.DealerInfoRepository;
import com.erry.auth.repository.UserDealerRepostory;
import com.erry.util.DateUtil;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class UserDealerServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private UserDealerRepostory dealerRepostory;
	
	@Autowired
	private AuUserRepository auUserRepository;
	
	@Autowired
	private DealerInfoRepository dealerInfoRepository;
	
	public List<UserDealer> findByDealerId(String id){
		return dealerRepostory.findByDealerId(id);
	}
	
	public UserDealer createUserDealer(String dealerid,String userid,String name,String email,String telno){
		UserDealer d = new UserDealer();
		d.setActive("1");
		d.setDealerid(dealerid);
		d.setEmail(email);
		d.setTelno(telno);
		d.setUsername(name);
		d.setUserid(userid);
		d.setId(GuidIdUtil.getGuId());
		return dealerRepostory.save(d);
	}
	
	public Map<Integer, List<UserDealerDTO>> findUserDealerList(int page,int size,String dealerName){
		Map<Integer, List<UserDealerDTO>> resultMap = new HashMap<Integer, List<UserDealerDTO>>();
		StringBuffer sql = new StringBuffer("from T_BPS_User u   ");
		sql.append("left join T_BPS_User_Dealer ud on ud.UserID=u.UserID   ");// and ud.Active='1' and u.Active='1'
		sql.append("left join T_BPS_DealerInfo d on d.DealerID = ud.DealerID ");//and d.Active='1'
		sql.append("where UserType='1' ");
		
		if(!StringUtil.isEmpty(dealerName)){
			sql.append(" and u.username like '%"+dealerName+"%'");
		}
		
		Query queryCount = entityManager.createNativeQuery(" select count(*) "
				+ sql.toString());
		Query query = entityManager.createNativeQuery("select d.DealerID,u.UserID,d.DealerName,u.UserName,ud.EMail,ud.TelNo,u.ExpireDate,d.Ename,d.DealerCode,u.UserCode " + sql.toString());
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		List<UserDealerDTO> list = new ArrayList<UserDealerDTO>();
		List<Object[]> objArrList = query.getResultList();
		for(Object[] objArr : objArrList)
		{
			UserDealerDTO dto = new UserDealerDTO();
			dto.setDealerid(String.valueOf(objArr[0]));
			dto.setUserid(String.valueOf(objArr[1]));
			dto.setDealername(String.valueOf(objArr[2]));
			dto.setUsername(String.valueOf(objArr[3]));
			dto.setEmail(String.valueOf(objArr[4]));
			if(objArr[5]==null){
				dto.setTelno("");
			}else{
				dto.setTelno(String.valueOf(objArr[5]));
			}
			if(objArr[6]==null){
				dto.setExpiredate("");
			}else{
				dto.setExpiredate(DateUtil.format(DateUtil.parse(String.valueOf(objArr[6])),DateUtil.PATTERN_DATE));
			}
			if(objArr[7]==null){
				dto.setEname("");
			}else{
				dto.setEname(String.valueOf(objArr[7]));
			}
			dto.setDealercode(String.valueOf(objArr[8]));
			dto.setLoginname(String.valueOf(objArr[9]));
			list.add(dto);
		}
		int sum=0;
		sum=Integer.valueOf(queryCount.getResultList().get(0).toString());
		resultMap.put(sum, list);
		return resultMap;
	}
	public List<UserDealerDTO> converTouserdealerDTOList(List<UserDealer> lists){
		List<UserDealerDTO> DTO = new ArrayList<UserDealerDTO>();
		for(UserDealer u :lists){
			UserDealerDTO d = new UserDealerDTO();
			d.setUserid(u.getUserid());
			d.setDealerid(u.getDealerid());
			AuUser user =auUserRepository.findByUid(u.getUserid());
		if(user!=null){
			d.setUsername(user.getUsername());
			d.setLoginname(user.getUsercode());
			if(user.getExpiredate()!=null){
				d.setExpiredate(DateUtil.format(user.getExpiredate(),DateUtil.PATTERN_DATE_TIME));
			}
			
		}
		DealerInfo dealerinfo = dealerInfoRepository.findByid(u.getDealerid());
		if(dealerinfo!=null){
			d.setDealername(dealerinfo.getDealername());
		}
		d.setTelno(u.getTelno());
		d.setEmail(u.getEmail());	
		DTO.add(d);
		}
		return DTO;
	}
	
	public void updateUserDealer(String uid,String dealername,String dealercode,String loginname,String telno,String ename,String email,String date){
		AuUser user = auUserRepository.findByUid(uid);
		if(user!=null){
			if(!StringUtil.isEmpty(date)){
				user.setExpiredate(DateUtil.parse(date, DateUtil.PATTERN_DATE));
			}else{
				user.setExpiredate(null);
			}
			user.setUsername(loginname);
			user.setUsercode(loginname);
			entityManager.merge(user);
			UserDealer ud =dealerRepostory.findByUserId(user.getUid());
			if(ud!=null){
				ud.setUsername(dealername);
				ud.setEmail(email);
				ud.setTelno(telno);
				entityManager.merge(ud);
			}
			DealerInfo dealerinfo = dealerInfoRepository.findDealerInfo(ud.getDealerid());
			if(dealerinfo!=null){
				dealerinfo.setEname(ename);
				dealerinfo.setDealername(dealername);
				dealerinfo.setDealercode(dealercode);
				if(StringUtil.isEmpty(date)){
					dealerinfo.setActive("1");
				}
				entityManager.merge(dealerinfo);
			}
		}
	}

}
