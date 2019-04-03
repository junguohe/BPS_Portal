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

import com.erry.auth.model.PersonView;
import com.erry.auth.model.ProdPerson;
import com.erry.auth.repository.ProdPersonRepository;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;


@Service
@Transactional
public class ProdPersonServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private ProdPersonRepository prodpersonrepository;

	public Map<Integer, List<ProdPerson>> findLists(int page,int size,final String area,final String mgr) {
		Map<Integer, List<ProdPerson>> resultMap = new HashMap<Integer, List<ProdPerson>>();
		StringBuffer hql = new StringBuffer(" from ProdPerson pw where 1=1  ");
		
		if(!StringUtil.isEmpty(area)){
			hql.append(" and  pw.area like '%"+area+"%'");
		}
		if(!StringUtil.isEmpty(mgr)){
			hql.append(" and  pw.mgr like '%"+mgr+"%'");
		}
		
		Query queryCount = entityManager.createQuery(" select count(pw.id) "
				+ hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();

		// 查询分页数据
		Query query = entityManager.createQuery(
				" select pw " + hql.toString(), ProdPerson.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		// 保存Map 总条数 分页后的集合
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}
	


	public ProdPerson updateOrsave(String id, String area_id, String area,
			String mgr_id, String mgr, String fae_id,
			String faemgr,String area_directory_id) {
		// TODO Auto-generated method stub
		ProdPerson person=new ProdPerson();
		if(id.length()!=36){
			//新增
			person.setId(GuidIdUtil.getGuId());
			person.setArea_id(area_id);
			person.setArea(area);
			person.setMgr_id(mgr_id);
			person.setMgr(mgr);
			person.setFae_id(fae_id);
			person.setFaemgr(faemgr);
			person.setArea_directory_id(area_directory_id);
			entityManager.persist(person);
			
		}else{
			//修改
			person = prodpersonrepository.findById(id);
			person.setArea_id(area_id);
			person.setArea(area);
			person.setMgr_id(mgr_id);
			person.setMgr(mgr);
			person.setFae_id(fae_id);
			person.setFaemgr(faemgr);
			person.setArea_directory_id(area_directory_id);
			entityManager.merge(person);
		}
		
		return person;
	}



	public String delPerson(String id) {
		try {
			String sql = "delete from erp_area_v where  id='"+id+"'";
			int count =  entityManager.createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return "系统错误请联系管理员！";
		}
		return "success";
	}


	public List<ProdPerson> findList(final String area,final String mgr) {

		List<ProdPerson> pSysLog = prodpersonrepository.findAll(new Specification<ProdPerson>() {
					public Predicate toPredicate(Root<ProdPerson> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
						List<Expression<Boolean>> expressions = predicate.getExpressions();
						
						if (!StringUtil.isEmpty(area))
							expressions.add(cb.like(
									root.<String> get("area"), "%" + area
											+ "%"));
						if (!StringUtil.isEmpty(mgr))
							expressions.add(cb.like(
									root.<String> get("mgr"), "%" + mgr
											+ "%"));
						return predicate;
					}
				});
		return pSysLog;
	}
}
