package com.erry.auth.service;

import java.util.Date;
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

import com.erry.auth.component.CurrentUserInfo;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.Dictionary;
import com.erry.auth.model.ProdLine;
import com.erry.auth.repository.ProductLineRepository;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class ProductLineServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ProductLineRepository repository;

	public List<ProdLine> findAll() {
		return repository.findAll();
	}
	
	
	public Map<Integer, List<ProdLine>> findList(int page, int size,
			final String name ,final String code) {
		Map<Integer, List<ProdLine>> resultMap = new HashMap<Integer, List<ProdLine>>();
		StringBuffer hql = new StringBuffer(" from ProdLine prodline where 1 = 1  ");
		hql.append(" and prodline.active = '1'");
		
		if (!StringUtil.isEmpty(name) && !StringUtil.isEmpty(name)) {
			hql.append(" and prodline.prodname like '%"+name+"%'   ");
		}
		
		if (!StringUtil.isEmpty(code) && !StringUtil.isEmpty(code)) {
			hql.append(" and prodline.prodcode like '%"+code+"%'   ");
		}
		
		Query queryCount = entityManager.createQuery(" select count(prodline.prodid) "+ hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();
		Query query = entityManager.createQuery(" select prodline " + hql.toString(),ProdLine.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}


	public ProdLine updateOrsave(String prodid, String prodname,
			String prodcode, String remark, String active) {
		// TODO Auto-generated method stub
		ProdLine prodline=new ProdLine();
		if(prodid.length()!=36){
			//新增
			prodline.setProdid(GuidIdUtil.getGuId());
			prodline.setProdname(prodname);
			prodline.setProdcode(prodcode);
			prodline.setRemark(remark);
			prodline.setActive(active);
			entityManager.persist(prodline);
			
		}else{
			//修改
			prodline = repository.findByProdid(prodid);
			prodline.setProdname(prodname);
			prodline.setProdcode(prodcode);
			prodline.setRemark(remark);
			prodline.setActive(active);
			entityManager.merge(prodline);
		}
		
		return prodline;
	}
}
