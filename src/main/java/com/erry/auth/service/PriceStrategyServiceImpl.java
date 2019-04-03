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
import com.erry.auth.dto.PriceStrategyDTO;
import com.erry.auth.dto.PriceStrategyDetailDTO;
import com.erry.auth.model.PriceStrategy;
import com.erry.auth.model.PriceStrategyDetail;
import com.erry.auth.repository.PriceStrategyRepository;
import com.erry.util.DateUtil;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class PriceStrategyServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PriceStrategyRepository priceStrategyRepository;
	
	public List<PriceStrategy> findPriceStrategyNo(){
		return priceStrategyRepository.findpricecode();
	}
	
	public PriceStrategy findBycodeNo(String codeno){
		return priceStrategyRepository.findBycodeno(codeno);
	}
	// 价格策略查询
	public Map<Integer, List<PriceStrategyDetail>> findpriceStrategy(
			int page, int size, final String versionno, final String code,
			final String materialcode, final String materialname,
			final String isAccuracy, final String publicdatef,
			final String publicdatet, final String status, final String ismain) {
		Map<Integer, List<PriceStrategyDetail>> resultMap = new HashMap<Integer, List<PriceStrategyDetail>>();
//		StringBuffer sql = new StringBuffer(" from ");
		
		StringBuffer sql = new StringBuffer(" from PriceStrategyDetail d where d.sid in(select sid from PriceStrategy where 1=1 and active ='1' and status='1' )");
		if (!StringUtil.isEmpty(versionno)) {
			sql.append(" and d.sid in (select s.sid from PriceStrategy s where s.versionno = '" + versionno + "') ");
		}
		if (!StringUtil.isEmpty(code)) {
			sql.append(" and d.sid in (select s.sid from PriceStrategy s where  s.code = '" + code + "') ");
		}
		if (!StringUtil.isEmpty(materialcode)) {
			sql.append(" and d.mterialid in (select m.id from MaterialInfo m where m.materialcode like '%"+materialcode+"%') ");
		}
		if (isAccuracy.equals("1")) {
			if (!StringUtil.isEmpty(materialname)) {
				sql.append(" and d.mterialid in (select m.id from MaterialInfo m where m.materialname = '"+materialname+"') ");
			}
		} else {
			if (!StringUtil.isEmpty(materialname)) {
				sql.append(" and d.mterialid in (select m.id from MaterialInfo m where m.materialname like '%"+materialname+"%') ");
			}
		}

		if (!StringUtil.isEmpty(publicdatef)
				&& !StringUtil.isEmpty(publicdatet)) {
			sql.append(" and d.sid in(select s.sid from PriceStrategy s where s.publicdate  between '" + publicdatef + "' and '"
					+ publicdatet + "')");
		}
		if (!StringUtil.isEmpty(publicdatef) && StringUtil.isEmpty(publicdatet)) {
			sql.append(" and d.sid in(select s.sid from PriceStrategy s where s.publicdate >= '" + publicdatef + "')");
		}
		if (StringUtil.isEmpty(publicdatef) && !StringUtil.isEmpty(publicdatet)) {
			sql.append(" and d.sid in(select s.sid from PriceStrategy s where s.publicdate <= '" + publicdatet + "')");
		}
		if (!StringUtil.isEmpty(status)) {
			sql.append(" and d.sid in(select s.sid from PriceStrategy s where s.status ='" + status + "')");
		}else{
			sql.append(" and d.sid in(select s.sid from PriceStrategy s where s.status ='1')");
		}
		if (!StringUtil.isEmpty(ismain)) {
			sql.append(" and d.ismain='" + ismain + "'");
		}
		Query queryCount = entityManager.createQuery(" select count(*) "
				+ sql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();

		// 查询分页数据
		Query query = entityManager.createQuery(
				" select d " + sql.toString(), PriceStrategyDetail.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		// 保存Map 总条数 分页后的集合
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;

	}
	//策略失效
	//策略保存返回sid
	public Map savePrice(PriceStrategy price){
		PriceStrategy p = new PriceStrategy();
		PriceStrategy pricestr = null;
		List<PriceStrategy> lists = new ArrayList<PriceStrategy>();
		Map map = new HashMap();
		
		String sid = GuidIdUtil.getGuId();
		
		p.setActive("1");
		p.setCode(price.getCode());
		p.setPublicdate(DateUtil.roundDate(price.getPublicdate()));
		p.setStatus(price.getStatus());
		p.setValidfrom(DateUtil.roundDate(price.getValidfrom()));
		p.setValidto(DateUtil.roundDate(price.getValidto()));
		
		if(price.getStatus().equals("1")){
			pricestr = findBycodeNo(price.getCode());
			lists=priceStrategyRepository.findListByCode(price.getCode(),sid);
			if(pricestr!=null){
			p.setVersionno(String.valueOf(Integer.valueOf(pricestr.getVersionno())+1));
			map.put("oldsid", pricestr.getSid());
			if(lists.size()>0){
				for (int i = 0; i < lists.size(); i++) {
					lists.get(i).setActive("0");
					lists.get(i).setStatus("2");
					entityManager.merge(lists.get(i));
				}
			}
			
			}else{
				p.setVersionno("1");
			}
			
		}else{
			p.setVersionno("1");
		}
		p.setUpdator(CurrentUserInfo.getUid());
		p.setUpdatedate(new Date());
		p.setCreatedate(new Date());
		map.put("newsid", sid);
		p.setSid(sid);
		p.setCreator(CurrentUserInfo.getUid());
		PriceStrategy prices = priceStrategyRepository.save(p);
		
		return map;
	}
	
	public List<PriceStrategyDTO> mappingToDTO(List<PriceStrategyDetail> list){
		List<PriceStrategyDTO> lists = new ArrayList<PriceStrategyDTO>();
		for(PriceStrategyDetail p : list){
			PriceStrategyDTO dto = new PriceStrategyDTO(p);
			lists.add(dto);
		}
		return lists; 
	}
}
