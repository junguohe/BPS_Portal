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

import com.erry.auth.model.BPSRole;
import com.erry.auth.repository.BPSRoleRepository;
import com.erry.util.StringUtil;

@Service
@Transactional
public class BPSRoleService {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private BPSRoleRepository bpsRoleRepository;
	
	public Map<Integer, List<BPSRole>> findBPSRoleList(int page,int size,String rolename){
		Map<Integer, List<BPSRole>> resultMap = new HashMap<Integer, List<BPSRole>>();
		StringBuffer hql = new StringBuffer("from UserRoleRel where active='1' ");
		if(!StringUtil.isEmpty(rolename)){
			hql.append(" and and UserName='"+rolename+"'");
		}
		Query queryCount = entityManager.createQuery(" select count(*) "
				+ hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();
		
		Query query = entityManager.createQuery(
				" select cust " + hql.toString(), BPSRole.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}
}
