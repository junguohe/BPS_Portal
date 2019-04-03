package com.erry.auth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.model.CustProjectInfo;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.DealarV;
import com.erry.auth.model.Dictionary;
import com.erry.auth.model.Person;
import com.erry.auth.model.PersonView;
import com.erry.auth.repository.DealarVRepository;
import com.erry.auth.repository.PersonRepository;
import com.erry.util.DateUtil;
import com.erry.util.StringUtil;


@Service
@Transactional
public class DealarVServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private DealarVRepository dealarvrepository;
	
//	public List<DealarV> findList() {
//		List<DealarV> pSysLog = dealarvrepository.findAll(new Specification<DealarV>() {
//					public Predicate toPredicate(Root<DealarV> root,
//							CriteriaQuery<?> query, CriteriaBuilder cb) {
//						Predicate predicate = cb.conjunction();
//						List<Expression<Boolean>> expressions = predicate
//								.getExpressions();
//						expressions.add(cb.like(
//								root.<String> get("active"), "Y"));
//
//						return predicate;
//					}
//				});
//		return pSysLog;
//	}
	
	
	
	public  Map<Integer,List<DealarV>> findList(){
		Map<Integer,List<DealarV>> resultMap = new HashMap<Integer,List<DealarV>>();
		StringBuffer hql = new StringBuffer(" from DealarV v where 1 = 1 and v.active= 'Y'  ");
		hql.append(" and v.id not in (select dealer.dealercode from DealerInfo dealer)");
		Query queryCount = entityManager.createQuery(" select count(v.id) " + hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();
		Query query = entityManager.createQuery(" select v " + hql.toString(), DealarV.class);
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}
}
