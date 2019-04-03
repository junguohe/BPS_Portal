package com.erry.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.component.CurrentUserInfo;
import com.erry.auth.dto.CustomerDTO;
import com.erry.auth.model.BPSMonthly;
import com.erry.auth.model.Customer;
import com.erry.auth.model.Json;
import com.erry.auth.repository.AuUserRepository;
import com.erry.auth.repository.BPSMonthlyRepository;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class BPSMonthlyService {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private AuUserRepository auuserrepository;
	@Autowired
	private BPSMonthlyRepository bpsMonthlyRepository;
	
	public Json saveOrUpdateMonth(String period,String active){
		String msg = "月结成功！";
		boolean success = true;
		BPSMonthly bpsmonthly=new BPSMonthly();
		BPSMonthly bpsMonth = bpsMonthlyRepository.findBpsMonthByPeriod(period);
		if(bpsMonth!=null){
			bpsmonthly=UpdateMonth(bpsMonth.getId(),active);
		}else{
			bpsmonthly=AddMonth(period,active);
		}
		
		Json json = new Json();
		json.setData(bpsmonthly);
		json.setMsg(msg);
		json.setSuccess(success);
		return json;
	}
	//新增
	public BPSMonthly AddMonth(String period,String active){
		BPSMonthly bpsmonthly=new BPSMonthly();
		bpsmonthly.setId(GuidIdUtil.getGuId());
		bpsmonthly.setPeriod(period);
		bpsmonthly.setActive(active);
		bpsmonthly.setCreatedate(new Date());
		bpsmonthly.setCreator(CurrentUserInfo.getUid());
		bpsmonthly.setUpdatedate(new Date());
		bpsmonthly.setUpdator(CurrentUserInfo.getUid());
		entityManager.persist(bpsmonthly);
		return bpsmonthly;
	}
	
	
	//修改
	public BPSMonthly UpdateMonth(String id,String active){
		BPSMonthly bpsMonth = bpsMonthlyRepository.findBpsMonthById(id);
		bpsMonth.setActive(active);
		bpsMonth.setUpdatedate(new Date());
		bpsMonth.setUpdator(CurrentUserInfo.getUid());
		entityManager.merge(bpsMonth);
		return bpsMonth;
	}
	public Map<Integer, List<BPSMonthly>> findLists(int page, int size,String period, String creator, String creatordate) {
		Map<Integer, List<BPSMonthly>> resultMap = new HashMap<Integer, List<BPSMonthly>>();
		// applyStatus 传过来的状态
		StringBuffer hql = new StringBuffer(
				" from BPSMonthly mon where 1=1");

		if (!StringUtil.isEmpty(period) && StringUtil.isEmpty(period)) {
			hql.append("and mon.period = '" + period + "' ");
		}

		if (!StringUtil.isEmpty(creator) && StringUtil.isEmpty(creator)) {
			hql.append("and mon.creator = '" + creator + "' ");
		}

		if (!StringUtil.isEmpty(creatordate) && !StringUtil.isEmpty(creatordate)) {
			hql.append("and mon.creatordate = '"+creatordate+"'");
		}

		Query queryCount = entityManager.createQuery(" select count(*) "
				+ hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();
		Query query = entityManager.createQuery(
				" select mon " + hql.toString()+"order by mon.updatedate desc ", BPSMonthly.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}
	public List<BPSMonthly> converToList(List<BPSMonthly> monthlist) {
		List<BPSMonthly> lists = new ArrayList<BPSMonthly>();
		for (BPSMonthly list : monthlist) {
			BPSMonthly months = new BPSMonthly();
			months.setId(list.getId());
			months.setPeriod(list.getPeriod());
			months.setUpdatedate(list.getUpdatedate());
			months.setUpdator(auuserrepository.findByUid(list.getUpdator()).getUsername());
			months.setActive(list.getActive());
			
			lists.add(months);
		}
		return lists;
	}
		public List<BPSMonthly> findLists(String period) {
		// applyStatus 传过来的状态 
		StringBuffer hql = new StringBuffer(
				" from BPSMonthly mon where 1=1");

		if (!StringUtil.isEmpty(period)) {
			hql.append("and mon.period = '" + period + "' ");
		}
		Query query = entityManager.createQuery(
				" select mon " + hql.toString(), BPSMonthly.class);
		return query.getResultList();
	}
	

}
