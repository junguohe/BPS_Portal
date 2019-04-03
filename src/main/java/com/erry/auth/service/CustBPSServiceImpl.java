package com.erry.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import com.erry.auth.dto.CustBPSDTO;
import com.erry.auth.model.CustBPS;
import com.erry.auth.repository.CustBPSRepository;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class CustBPSServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CustBPSRepository repository;

	public Page<CustBPS> fingList(int page, int size, final String cpid) {
		PageRequest pageable = new PageRequest(page, size, new Sort(new Order(
				Direction.ASC, "id")));
		Page<CustBPS> pSysLog = repository.findAll(
				new Specification<CustBPS>() {
					public Predicate toPredicate(Root<CustBPS> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
						List<Expression<Boolean>> expressions = predicate
								.getExpressions();
						expressions.add(cb.like(root.<String> get("active"),
								"1"));
						if (!StringUtil.isEmpty(cpid))
							expressions.add(cb.like(root.<String> get("cpid"),
									"%" + cpid + "%"));
						return predicate;
					}
				}, pageable);
		return pSysLog;
	}

	public List<CustBPSDTO> converTocustomerDTOList(List<CustBPS> list) {
		List<CustBPSDTO> custList = new ArrayList<CustBPSDTO>();
		for (CustBPS cust : list) {
			CustBPSDTO custRegDTO = new CustBPSDTO(cust);
			custList.add(custRegDTO);
		}
		return custList;
	}
	public CustBPS deleteAdd(CustBPS cust,String active) {
		cust.setActive(active);
		entityManager.merge(cust);
		return cust;
	}
	public CustBPS saveCustBPS(CustBPS c,String cpid){
		CustBPS cust = new CustBPS();
		
		if(c.getId().length()<36){
			//新增
			cust.setId(GuidIdUtil.getGuId());	
			cust.setCpid(cpid);
			cust.setIsshare(c.getIsshare());
			cust.setCusttype(c.getCusttype());
			cust.setCustcategory1(c.getCustcategory1());
			cust.setCustcategory2(c.getCustcategory2());
			cust.setCustcategory3(c.getCustcategory3());
			cust.setDealersales(c.getDealersales());
			cust.setBpssales(c.getBpssales());
			cust.setBpsfae(c.getBpsfae());
			cust.setActive("1");
			cust.setUpdator(c.getUpdator());
			cust.setUpdatedate(c.getUpdatedate());
			cust.setCreator(CurrentUserInfo.getUid());
			cust.setCreatedate(new Date());
			entityManager.persist(cust);
		}
		else{
			//修改
			cust=repository.findById(c.getId());
			cust.setCpid(cpid);
			cust.setIsshare(c.getIsshare());
			cust.setCusttype(c.getCusttype());
			cust.setCustcategory1(c.getCustcategory1());
			cust.setCustcategory2(c.getCustcategory2());
			cust.setCustcategory3(c.getCustcategory3());
			cust.setDealersales(c.getDealersales());
			cust.setBpssales(c.getBpssales());
			cust.setBpsfae(c.getBpsfae());
			cust.setActive("1");
			cust.setUpdator(CurrentUserInfo.getUid());
			cust.setUpdatedate(new Date());
			entityManager.merge(cust);
		}
		
		
	return cust;
	}
	public List<CustBPS> updateInfo(List<String>ids,String bpsid,String faeid ) {
		List<CustBPS> cust = new ArrayList<CustBPS>();
		//CustBPS c=new CustBPS();
		 for (int i = 0; i < ids.size(); i++)
         {
			 List<CustBPS>c=repository.findByCpids(ids.get(i));
			 if(c.size()!=0){
				 for(int j = 0; j < c.size(); j++){
					 
					 if(c.get(j)!=null&&c.get(j).getId()!=null){
						 c.get(j).setActive("0");
						 c.get(j).setUpdatedate(new Date());
					     c.get(j).setUpdator(CurrentUserInfo.getUid());
						 entityManager.merge(c.get(j));
					 }else{
						 continue;
					 }
					 
				 }
			 }
			
				 CustBPS bps=new CustBPS();
				 bps.setId(GuidIdUtil.getGuId());
				 bps.setCpid(ids.get(i));
				 bps.setBpssales(bpsid);
				 bps.setBpsfae(faeid);
				 bps.setCreatedate(new Date());
				 bps.setActive("1");
				 bps.setUpdatedate(new Date());
				 bps.setUpdator(CurrentUserInfo.getUid());
				 bps.setCreator(CurrentUserInfo.getUid());
				 entityManager.persist(bps);
				 cust.add(bps);
         }
		
		return cust;
	}
	public List<CustBPS> cancelActive(List<String> ids) {
		List<CustBPS> cust = new ArrayList<CustBPS>();
		for (int i = 0; i < ids.size(); i++)
        {
			List<CustBPS> c=repository.findByCpids(ids.get(i));
			 if(c.size()==0){
				 break;
			 }else{
				for(int j = 0; j < c.size(); j++){
					if(c.get(j)!=null&&c.get(j).getId()!=null){
						c.get(j).setActive("0");
						c.get(j).setUpdatedate(new Date());
						c.get(j).setUpdator(CurrentUserInfo.getUid());
						 entityManager.merge(c.get(j));
						 cust.add(c.get(j));
					 }else{
						 continue;
					 }
				}
			 }
			
        }
		return cust;
	}
	
	public Map<String, String> findBPSfalesAndFae(String emid){
		Map<String, String> map = new HashMap<String, String>();
		
		return map;
	}
}
