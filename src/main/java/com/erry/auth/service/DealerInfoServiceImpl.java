package com.erry.auth.service;

import java.util.ArrayList;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.component.CurrentUserInfo;
import com.erry.auth.dto.CustRegDTO;
import com.erry.auth.dto.DealerInfoDTO;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.DealerInfo;
import com.erry.auth.repository.AuUserRepository;
import com.erry.auth.repository.DealarVRepository;
import com.erry.auth.repository.DealerInfoRepository;
import com.erry.util.DateUtil;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class DealerInfoServiceImpl {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private DealerInfoRepository dealerRepo; 
	
	@Autowired
	private AuUserRepository auuserrepository; 
	
	@Autowired
	private DealarVRepository dealarVRepository;

	public Page<DealerInfo> findList(int page, int size, final String dealerid) {
		PageRequest pageable = new PageRequest(page, size, new Sort(new Order(
				Direction.ASC, "id")));
		Page<DealerInfo> pSysLog = dealerRepo.findAll(
				new Specification<DealerInfo>() {
					public Predicate toPredicate(Root<DealerInfo> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
						List<Expression<Boolean>> expressions = predicate
								.getExpressions();
						expressions.add(cb.like(
								root.<String> get("active"), "1"));
						if (!StringUtil.isEmpty(dealerid))
							expressions.add(cb.like(
									root.<String> get("dealerid"), "%"
											+ dealerid + "%"));
						return predicate;
					}
				}, pageable);
		return pSysLog;
	}
//联想控件
	
	public List<DealerInfo> findDealerByname(final String name) {

		List<DealerInfo> pSysLog = dealerRepo
				.findAll(new Specification<DealerInfo>() {
					public Predicate toPredicate(Root<DealerInfo> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
						List<Expression<Boolean>> expressions = predicate
								.getExpressions();;
						if (!StringUtil.isEmpty(name))
							expressions.add(cb.like(
									root.<String> get("dealername"), "%" + name
											+ "%"));
						return predicate;
					}
				});
		return pSysLog;
	}
	public List<DealerInfoDTO> converTocustomerDTOList(List<DealerInfo> list) {
		List<DealerInfoDTO> custList = new ArrayList<DealerInfoDTO>();
		for (DealerInfo contact : list) {
			DealerInfoDTO custRegDTO = new DealerInfoDTO(contact);
			custList.add(custRegDTO);
		}
		return custList;
	}

	public Map<Integer, List<CustReg>> findCustRegList(int page, int size,
			final String cpid,final String dealerid) {
		Map<Integer, List<CustReg>> resultMap = new HashMap<Integer, List<CustReg>>();
		// applyStatus 传过来的状态
		StringBuffer hql = new StringBuffer(" from CustReg cr where 1 = 1 and cr.regstartdate!=null   ");
		if(!StringUtil.isEmpty(cpid)){
			hql.append(" and cr.cpid ='"+cpid+"'  ");
		}
//		if(!StringUtil.isEmpty(dealerid)){
//			hql.append(" and cr.dealerid ='"+dealerid+"' ");
//		}
		
		// 查询总数
		Query queryCount = entityManager.createQuery(" select count(cr.id) "
				+ hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();

		// 查询分页数据
		Query query = entityManager.createQuery(" select cr " + hql.toString(),
				CustReg.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		// 保存Map 总条数 分页后的集合
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}

	public List<CustRegDTO> converTocustRegDTOList(List<CustReg> list) {
		List<CustRegDTO> custList = new ArrayList<CustRegDTO>();
		for (CustReg cust : list) {
			CustRegDTO custRegDTO = new CustRegDTO(cust);
			custList.add(custRegDTO);
		}
		return custList;
	}
	public List<CustRegDTO> converTocustRegDTOLists(List<CustReg> list) {
		List<CustRegDTO> custList = new ArrayList<CustRegDTO>();
		for (CustReg cust : list) {
			CustRegDTO custRegDTO = new CustRegDTO(cust);
			custRegDTO.setProdname(cust.getCustprodline().getProd().getProdname());
			if(cust.getDealer()!=null){
				custRegDTO.setDealername(cust.getDealer().getDealername());
			}else{
				custRegDTO.setDealername(null);
			}
			if(cust.getRegstartdate()!=null){
				custRegDTO.setRegstartdate (DateUtil.format(cust.getRegstartdate(),DateUtil.PATTERN_DATE_TIME));
			}else{
				custRegDTO.setRegstartdate(null);
			}
			if(cust.getRegenddate()!=null){
				custRegDTO.setRegenddate (DateUtil.format(cust.getRegenddate(),DateUtil.PATTERN_DATE_TIME));
			}else{
				custRegDTO.setRegenddate(null);
			}
			if(cust.getUpdatedate()!=null){
				custRegDTO.setUpdatedate(DateUtil.format(cust.getUpdatedate(),DateUtil.PATTERN_DATE_TIME));
			}else{
				custRegDTO.setUpdatedate(null);
			}
			if(cust.getUpdator()!=null){
				custRegDTO.setUpdator(auuserrepository.findUserById(cust.getUpdator()).getUsername());
			}else{
				custRegDTO.setUpdator(null);
			}
			custList.add(custRegDTO);
		}
		return custList;
	}
	/**
	 * 根据name查询dealer
	 * @param dealerName
	 * @return dealerinfo
	 */
	public DealerInfo findDealerInfoByDealerName(String dealerName){
		return dealerRepo.findDealerInfoByDealerName(dealerName);
	}
	
	public DealerInfo createDealerInfo(String dealername,String dealercode,String ename){
		/*DealarV dealerv = dealarVRepository.findById(dealercode);*/
		DealerInfo info = new DealerInfo();
		info.setActive("1");
		info.setCreatedate(new Date());
		info.setCreator(CurrentUserInfo.getUid());
		info.setDealercode(dealercode);
		info.setDealerid(GuidIdUtil.getGuId());
		info.setDealername(dealername);
		info.setEname(ename);
		/*info.setMaxregno(Integer.valueOf(dealerv.getSalesnumber())); 在产品线权限维护 默认0*/
		info.setMaxregno(0); 
		info.setUpdatedate(new Date());
		info.setUpdator(CurrentUserInfo.getUid());
		return dealerRepo.save(info);
	}
}
