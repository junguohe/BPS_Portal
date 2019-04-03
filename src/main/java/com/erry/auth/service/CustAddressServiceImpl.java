package com.erry.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.erry.auth.dto.CustAddressDTO;
import com.erry.auth.model.Contact;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustProdLine;
import com.erry.auth.repository.CustAddressRepository;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class CustAddressServiceImpl {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CustAddressRepository custAddressRepo;

	public Page<CustAddress> findList(int page, int size, final String cpid,final String creator) {
		PageRequest pageable = new PageRequest(page, size, new Sort(new Order(
				Direction.ASC, "id")));
		Page<CustAddress> pSysLog = custAddressRepo.findAll(
				new Specification<CustAddress>() {
					public Predicate toPredicate(Root<CustAddress> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
						List<Expression<Boolean>> expressions = predicate
								.getExpressions();

						expressions.add(cb.like(root.<String> get("active"),
								"%1%"));
						if (!StringUtil.isEmpty(creator))
							expressions.add(cb.like(root.<String> get("creator"),
									"%" + creator + "%"));
						
						
						if (!StringUtil.isEmpty(cpid))
							expressions.add(cb.like(root.<String> get("cpid"),
									"%" + cpid + "%"));
						
						return predicate;
					}
				}, pageable);
		return pSysLog;
	}

	public List<CustAddressDTO> converTocustomerDTOList(List<CustAddress> list) {
		List<CustAddressDTO> custList = new ArrayList<CustAddressDTO>();
		for (CustAddress cust : list) {
			CustAddressDTO custRegDTO = new CustAddressDTO(cust);
			custList.add(custRegDTO);
		}
		return custList;
	}

	public CustAddress saveCustAddress(CustAddress address) {
		return custAddressRepo.save(address);
	}

	public CustAddress saveAddress(CustAddress c, String cpid) {
		CustAddress cust = new CustAddress();

		if (c.getId().length() < 36) {
			// 新增
			cust.setId(GuidIdUtil.getGuId());
			cust.setCreator(CurrentUserInfo.getUid());
			cust.setCreatedate(new Date());

			cust.setUpdator(c.getUpdator());
			cust.setUpdatedate(c.getUpdatedate());
			cust.setCpid(cpid);
			cust.setAddtype(c.getAddtype());
			cust.setProvince(c.getProvince());
			cust.setCity(c.getCity());
			cust.setAddress(c.getAddress());
			cust.setIsdefault(c.getIsdefault());
			cust.setRemark(c.getRemark());
			cust.setActive("1");
			entityManager.persist(cust);
		} else {
			// 修改
			cust = custAddressRepo.findById(c.getId());
			cust.setUpdator(CurrentUserInfo.getUid());
			cust.setUpdatedate(new Date());
			cust.setCpid(cpid);
			cust.setAddtype(c.getAddtype());
			cust.setProvince(c.getProvince());
			cust.setCity(c.getCity());
			cust.setAddress(c.getAddress());
			cust.setIsdefault(c.getIsdefault());
			cust.setRemark(c.getRemark());
			cust.setActive("1");
			entityManager.merge(cust);
		}

		return cust;
	}

	public CustAddress deleteAdd(CustAddress cust, String active) {
		cust.setActive(active);
		entityManager.merge(cust);
		return cust;
	}

	
	public List<CustAddress> cancelActive(List<String> ids) {
		List<CustAddress> cust = new ArrayList<CustAddress>();
		for (int i = 0; i < ids.size(); i++) {
			List<CustAddress> c = custAddressRepo.findByCpid(ids.get(i));
			for (int j = 0; j < c.size(); j++) {
				if (c.get(j) == null) {
					break;
				} else {
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
