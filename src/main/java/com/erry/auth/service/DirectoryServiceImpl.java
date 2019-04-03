package com.erry.auth.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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

import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.component.CurrentUserInfo;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.Dictionary;
import com.erry.auth.model.ProdLine;
import com.erry.auth.repository.DirectoryRepository;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class DirectoryServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private DirectoryRepository repository;

	public List<Dictionary> findList(final String module,final String function) {

		List<Dictionary> pSysLog = repository.findAll(new Specification<Dictionary>() {
					public Predicate toPredicate(Root<Dictionary> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
						List<Expression<Boolean>> expressions = predicate
								.getExpressions();
						expressions.add(cb.like(
								root.<String> get("active"), "1"));
						if (!StringUtil.isEmpty(module))
							expressions.add(cb.like(
									root.<String> get("module"),  module));
						if (!StringUtil.isEmpty(function))
							expressions.add(cb.like(
									root.<String> get("function"), function ));
						return predicate;
					}
				});
		return pSysLog;
	}

	
public List<Dictionary> findCurrency(final String module,final String function,String dealerid){
		
		StringBuffer sql =new StringBuffer("select * from t_bps_md_directory d where d.active='1' ");
		if (!StringUtil.isEmpty(module)) {
			sql.append(" AND d.module = '"+module+"'");
		}
		if (!StringUtil.isEmpty(function)) {
			if(function.equals("currency")){
				sql.append(" AND d.id in (select dc.CurrentyID from bps_dealer_currency dc where dc.active='1' and dc.dealerid ='"+dealerid+"')");
			}else{
				sql.append(" AND d.[Function] = '"+function+"'");
			}
		}
		Query query = entityManager.createNativeQuery(sql.toString());
		List<Dictionary> r = new ArrayList<Dictionary>();
		List<Object[]> objArrList = query.getResultList();
		for(Object[] objArr : objArrList)
		{
			Dictionary dto = new Dictionary();
			
			dto.setId(String.valueOf(objArr[0]));
			dto.setType(String.valueOf(objArr[1]));
			dto.setModule(String.valueOf(objArr[2]));
			dto.setFunction(String.valueOf(objArr[3]));
			dto.setCode(String.valueOf(objArr[4]));
			dto.setValue(String.valueOf(objArr[5]));
			dto.setRemark(String.valueOf(objArr[6]));
			dto.setActive(String.valueOf(objArr[7]));
			r.add(dto);
		}
		return r;
	}
	
	
	public Map<Integer, List<Dictionary>> findDictionary(int page, int size, final String code, final String value) {
		Map<Integer, List<Dictionary>> resultMap = new HashMap<Integer, List<Dictionary>>();
		
		StringBuffer hql = new StringBuffer(" from Dictionary dictionary where 1 = 1  ");
		hql.append(" and dictionary.active = '1'");
		hql.append(" and (dictionary.module='customer' AND dictionary.function='area' OR   dictionary.module='price' AND dictionary.function='currency')");
		
		
		if (!StringUtil.isEmpty(code) && !StringUtil.isEmpty(code)) {
			hql.append(" and dictionary.code like '%"+code+"%'   ");
		}
		
		if (!StringUtil.isEmpty(value) && !StringUtil.isEmpty(value)) {
			hql.append(" and dictionary.value like '%"+value+"%'   ");
		}
		
		Query queryCount = entityManager.createQuery(" select count(dictionary.id) "+ hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();
		Query query = entityManager.createQuery(" select dictionary " + hql.toString(),Dictionary.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}
	
	//id,module,functions,value,code,remark,active
	public Dictionary updateOrsave(String id, String module,
			String functions, String value, String code,String remark, String active) {
		// TODO Auto-generated method stub
		Dictionary dictionary=new Dictionary();
		if(id.length()!=36){
			//新增
			dictionary.setId(GuidIdUtil.getGuId());
			dictionary.setType("type");
			dictionary.setModule(module);
			dictionary.setFunction(functions);
			dictionary.setValue(value);
			dictionary.setCode(code);
			dictionary.setRemark(remark);
			dictionary.setActive(active);
			entityManager.persist(dictionary);
			
		}else{
			//修改
			dictionary = repository.findById(id);
			//dictionary.setType("type");
			dictionary.setModule(module);
			dictionary.setFunction(functions);
			dictionary.setValue(value);
			dictionary.setCode(code);
			dictionary.setRemark(remark);
			dictionary.setActive(active);
			entityManager.merge(dictionary);
		}
		return dictionary;
	}
	/**
	 * 修改字典表中的area 同步修改区域经理中的area
	 * @param tid
	 */
	public void updateArea() {

		SessionImplementor session = entityManager.unwrap(SessionImplementor.class);
		Connection conn = session.connection();
		String testPrint = null;
		try {
			conn.setAutoCommit(false);
			CallableStatement cstmt = conn.prepareCall("{ call [dbo].[T_BPS_Area_Update]() }");
			cstmt.execute();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

     }
}
