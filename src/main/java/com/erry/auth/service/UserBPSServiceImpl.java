package com.erry.auth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.model.UserBPS;
import com.erry.auth.repository.UserBPSRepository;
import com.erry.util.StringUtil;

@Service
@Transactional
public class UserBPSServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private UserBPSRepository userBPSRepository;
	
	public List<UserBPS> readAll(){
		return userBPSRepository.findAll();
	}


	public Map<Integer, List<UserBPS>> findUserBPSList(int page,int size,String name){
		Map<Integer, List<UserBPS>> resultMap = new HashMap<Integer, List<UserBPS>>();
		StringBuffer hql = new StringBuffer("from UserBPS user where user.active='1' ");
		if(!StringUtil.isEmpty(name)){
			hql.append(" and user.personalname like '%"+name+"%'");
		}
		Query queryCount = entityManager.createQuery(" select count(*) "
				+ hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();
		
		Query query = entityManager.createQuery(
				" select user " + hql.toString(), UserBPS.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}
}
