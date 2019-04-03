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
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.component.CurrentUserInfo;
import com.erry.auth.dto.ContactDTO;
import com.erry.auth.dto.CustAddressDTO;
import com.erry.auth.dto.CustRegDTO;
import com.erry.auth.dto.CustomerDTO;
import com.erry.auth.model.Contact;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustProjectInfo;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.Customer;
import com.erry.auth.repository.ContactRepository;
import com.erry.auth.repository.CustAddressRepository;
import com.erry.auth.repository.CustomerRepository;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class ContactServiceImpl {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ContactRepository contactRepo;

	public Page<Contact> findList(int page, int size, final String cpid,final String creator) {
		PageRequest pageable = new PageRequest(page, size, new Sort(new Order(
				Direction.ASC, "id")));
		Page<Contact> pSysLog = contactRepo.findAll(
				new Specification<Contact>() {
					public Predicate toPredicate(Root<Contact> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
						List<Expression<Boolean>> expressions = predicate
								.getExpressions();
						expressions.add(cb.like(root.<String> get("active"),
								"1"));
						if (!StringUtil.isEmpty(cpid))
							expressions.add(cb.like(root.<String> get("cpid"),
									"%" + cpid + "%"));
						if (!StringUtil.isEmpty(creator))
							expressions.add(cb.like(root.<String> get("creator"),
									"%" + creator + "%"));
						return predicate;
					}
				}, pageable);
		return pSysLog;
	}

	public List<ContactDTO> converTocustomerDTOList(List<Contact> list) {
		List<ContactDTO> custList = new ArrayList<ContactDTO>();
		for (Contact contact : list) {
			ContactDTO custRegDTO = new ContactDTO(contact);
			custList.add(custRegDTO);
		}
		return custList;
	}

	public Contact saveContect(Contact contact) {
		return contactRepo.save(contact);
	}

	public Contact saveContact(Contact c, String cpid) {
		Contact cust = new Contact();
		if (c.getId().length() < 36) {

			cust.setId(GuidIdUtil.getGuId());
			cust.setCpid(cpid);
			cust.setTitle(c.getTitle());
			cust.setName(c.getName());
			cust.setMobile(c.getMobile());
			cust.setTelno(c.getTelno());
			cust.setEmail(c.getEmail());
			cust.setIm(c.getIm());
			cust.setRemark(c.getRemark());
			cust.setActive("1");
			cust.setCreator(CurrentUserInfo.getUid());
			cust.setCreatedate(new Date());
			cust.setUpdator(c.getUpdator());
			cust.setUpdatedate(c.getUpdatedate());
			entityManager.persist(cust);
		} else {
			cust = contactRepo.findById(c.getId());
			cust.setCpid(cpid);
			cust.setTitle(c.getTitle());
			cust.setName(c.getName());
			cust.setMobile(c.getMobile());
			cust.setTelno(c.getTelno());
			cust.setEmail(c.getEmail());
			cust.setIm(c.getIm());
			cust.setRemark(c.getRemark());
			cust.setActive("1");
			cust.setUpdator(CurrentUserInfo.getUid());
			cust.setUpdatedate(new Date());
			entityManager.merge(cust);
		}
		return cust;
	}

	public Contact deleteAdd(Contact cust, String active) {
		cust.setActive(active);
		entityManager.merge(cust);
		return cust;
	}

	public List<Contact> cancelActive(List<String> ids) {
		List<Contact> cust = new ArrayList<Contact>();
		for (int i = 0; i < ids.size(); i++) {
			List<Contact> c = contactRepo.findByCpid(ids.get(i));
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
