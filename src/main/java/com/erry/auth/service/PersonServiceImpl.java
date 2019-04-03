package com.erry.auth.service;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.dto.PersonDTO;
import com.erry.auth.model.PersonView;
import com.erry.auth.repository.PersonRepository;
import com.erry.util.DateUtil;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;


@Service
@Transactional
public class PersonServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PersonRepository personRepository;
	
	public List<PersonView> findList(final String fempgroup,final String name) {

		List<PersonView> pSysLog = personRepository.findAll(new Specification<PersonView>() {
					public Predicate toPredicate(Root<PersonView> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
						List<Expression<Boolean>> expressions = predicate.getExpressions();
						
						//expressions.add(cb.like(root.<String> get("active"), "Y"));
						
						if (!StringUtil.isEmpty(fempgroup))
							expressions.add(cb.like(root.<String> get("fempgroup"), "%" + fempgroup+ "%"));
						if (!StringUtil.isEmpty(name))
							expressions.add(cb.like(root.<String> get("per_name"), "%" + name+ "%"));
						return predicate;
					}
				});
		return pSysLog;
	}
	
	
	
	public Map<Integer, List<PersonView>> findLists(int page,int size,final String fempgroup,final String name) {
		Map<Integer, List<PersonView>> resultMap = new HashMap<Integer, List<PersonView>>();
		StringBuffer hql = new StringBuffer(" from PersonView pw where pw.active='1'  ");
		
		if(!StringUtil.isEmpty(fempgroup)){
			hql.append(" and  pw.fempgroup like '%"+fempgroup+"%'");
		}
		if(!StringUtil.isEmpty(name)){
			hql.append(" and  pw.per_name like '%"+name+"%'");
		}
		
		Query queryCount = entityManager.createQuery(" select count(pw.id) "
				+ hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();

		// 查询分页数据
		Query query = entityManager.createQuery(
				" select pw " + hql.toString(), PersonView.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		// 保存Map 总条数 分页后的集合
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}
	
	public List<PersonDTO> readForDTO(List<PersonView> pv){
		List<PersonDTO> dto = new ArrayList<PersonDTO>();
		for(PersonView p : pv){
			PersonDTO d = new PersonDTO(p);
			dto.add(d);
		}
		return dto;
	}
	
	public PersonView findByEmpId(String empId){
		return personRepository.findByempid(empId);
	}



	public PersonView updateOrsave(String id, String per_id, String per_name,
			String departmentid, String departmentname, String empid,
			String mangerid, String account, String fhiredate,
			String fempgroup, String active,String peremail,String mangername) {
		// TODO Auto-generated method stub
		PersonView person=new PersonView();
		if(id.length()!=36){
			//新增
			person.setId(GuidIdUtil.getGuId());
			person.setPer_id(per_id);
			person.setPer_name(per_name);
			person.setDepartmentid(departmentid);
			person.setDepartmentname(departmentname);
			person.setEmpid(empid);
			person.setMangerid(mangerid);
			person.setAccount(account);
			person.setPeremail(peremail);
			if(!StringUtil.isEmpty(fhiredate)){
				person.setFhiredate(DateUtil.parse(fhiredate, DateUtil.PATTERN_DATE));
				
			}else{
				person.setFhiredate(null);
			}
			person.setMangername(mangername);
			person.setFempgroup(fempgroup);
			person.setActive("1");
			entityManager.persist(person);
			
		}else{
			//修改
			person = personRepository.findById(id);
			person.setPer_id(per_id);
			person.setPer_name(per_name);
			person.setDepartmentid(departmentid);
			person.setDepartmentname(departmentname);
			person.setEmpid(empid);
			person.setMangerid(mangerid);
			person.setAccount(account);
			person.setPeremail(peremail);
			if(!StringUtil.isEmpty(fhiredate)){
				person.setFhiredate(DateUtil.parse(fhiredate, DateUtil.PATTERN_DATE));
			}else{
				person.setFhiredate(null);
			}
			person.setMangername(mangername);
			person.setFempgroup(fempgroup);
			person.setActive("1");
			entityManager.merge(person);
		}
		
		return person;
	}
	/**
	 * 根据id 删除person
	 * @param id
	 * @return
	 */
	public String delPerson(String id){
		try {
			String sql = "delete from erp_person_v where active='1' and id='"+id+"'";
			int count =  entityManager.createNativeQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return "系统错误请联系管理员！";
		}
		return "success";
	}
}
