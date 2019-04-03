package com.erry.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import com.erry.auth.dto.CustProjectInfoDTO;
import com.erry.auth.model.CustProjectInfo;
import com.erry.auth.repository.CustProjectInfoRepository;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class CustProjectInfoServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CustProjectInfoRepository repository;

	public Page<CustProjectInfo> fingList(int page, int size, final String cpid,final String name,final String creator) {
		PageRequest pageable = new PageRequest(page, size, new Sort(new Order(
				Direction.ASC, "id")));
		Page<CustProjectInfo> pSysLog = repository.findAll(
				new Specification<CustProjectInfo>() {
					public Predicate toPredicate(Root<CustProjectInfo> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
						List<Expression<Boolean>> expressions = predicate
								.getExpressions();
						expressions.add(cb.like(root.<String> get("active"),
								"1"));
						if (!StringUtil.isEmpty(creator)){
							expressions.add(cb.like(root.<String> get("creator"),
									"%" + creator + "%"));
						}
						if (!StringUtil.isEmpty(cpid)){
							expressions.add(cb.like(root.<String> get("cpid"),
									"%" + cpid + "%"));
						}
								
						
						if (!StringUtil.isEmpty(name)){
							expressions.add(cb.like(root.<String> get("projname"),
									"%" + name + "%"));
						}
							
						return predicate;
					}
				}, pageable);
		return pSysLog;
	}
	public List<CustProjectInfo> findProjecttype(String name){
		String sql = "select * from T_BPS_Cust_ProjectInfo where Active='1'";
		if(!StringUtil.isEmpty(name)){
			sql = " select * from T_BPS_Cust_ProjectInfo where Active='1' and ProjName like '%"+name+"%'";
		}
		Query query = entityManager.createQuery(sql.toString(), CustProjectInfo.class);
		return query.getResultList();
	}
	public List<CustProjectInfoDTO> converTocustomerDTOList(
			List<CustProjectInfo> list) {
		List<CustProjectInfoDTO> custList = new ArrayList<CustProjectInfoDTO>();
		for (CustProjectInfo cust : list) {
			CustProjectInfoDTO custRegDTO = new CustProjectInfoDTO(cust);
			custList.add(custRegDTO);
		}
		return custList;
	}

	public CustProjectInfo saveCustProjectInfo(CustProjectInfo info) {
		return repository.save(info);
	}

	public CustProjectInfo saveProject(CustProjectInfo c, String cpid) {
		CustProjectInfo cust = new CustProjectInfo();
		if (c.getId().length() < 36) {

			cust.setId(GuidIdUtil.getGuId());
			cust.setCpid(cpid);
			cust.setMaterialid(c.getMaterialid());
			cust.setProjtype(c.getProjtype());
			cust.setProjname(c.getProjname());
			cust.setCompname(c.getCompname());
			cust.setCompprod(c.getCompprod());
			cust.setShipvol(c.getShipvol());
			cust.setMassproddate(c.getMassproddate());
			cust.setRemark(c.getRemark());
			cust.setActive("1");
			cust.setRemark(c.getRemark());
			cust.setCreator(CurrentUserInfo.getUid());
			cust.setCreatedate(new Date());
			cust.setUpdator(c.getUpdator());
			cust.setUpdatedate(c.getUpdatedate());
			entityManager.persist(cust);
		} else {

			cust = repository.findById(c.getId());
			cust.setCpid(cpid);
			cust.setMaterialid(c.getMaterialid());
			cust.setProjtype(c.getProjtype());
			cust.setProjname(c.getProjname());
			cust.setCompname(c.getCompname());
			cust.setCompprod(c.getCompprod());
			cust.setShipvol(c.getShipvol());
			cust.setMassproddate(c.getMassproddate());
			cust.setRemark(c.getRemark());
			cust.setActive("1");
			
			cust.setUpdator(CurrentUserInfo.getUid());
			cust.setUpdatedate(new Date());
			entityManager.merge(cust);
		}

		return cust;
	}

	public CustProjectInfo deleteAdd(CustProjectInfo cust, String active) {
		cust.setActive(active);
		entityManager.merge(cust);
		return cust;
	}

	public List<CustProjectInfo> cancelActive(List<String> ids) {
		List<CustProjectInfo> cust = new ArrayList<CustProjectInfo>();
		for (int i = 0; i < ids.size(); i++) {
			List<CustProjectInfo> c = repository.findByCpids(ids.get(i));
			if (c.size() == 0) {
				break;
			} else {
				for (int j = 0; j < c.size(); j++) {
					c.get(j).setActive("0");
					c.get(j).setUpdatedate(new Date());
					c.get(j).setUpdator(CurrentUserInfo.getUid());
					entityManager.merge(c.get(j));
					cust.add(c.get(j));
				}
			}

		}
		return cust;
	}

}
