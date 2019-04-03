package com.erry.auth.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.model.DealerCurrency;
import com.erry.auth.model.Dictionary;
import com.erry.auth.repository.DealerCurrencyRepository;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class DealerCurrencyServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private DealerCurrencyRepository dealercurrencyrepository;

	public List<Dictionary> findList(String dealerid) {
		StringBuffer sql =new StringBuffer(" ");
		sql.append(" SELECT  d.*, 'true' checked FROM dbo.T_BPS_MD_Directory d LEFT JOIN BPS_dealer_currency dc ON d.ID =dc.CurrentyID ");
		sql.append(" WHERE  d.[Function]='currency' AND d.Active='1' ");
		if(!StringUtil.isEmpty(dealerid)){
			sql.append(" AND dc.dealerid='"+dealerid+"' AND dc.Active='1'  ");
		}
		sql.append(" UNION ");
		
		sql.append(" SELECT d.*,'false'checked  FROM dbo.T_BPS_MD_Directory d WHERE d.[Function]='currency' AND d.Active='1' ");
		if(!StringUtil.isEmpty(dealerid)){
			sql.append("  AND d.ID NOT IN  (SELECT CurrentyID FROM dbo.BPS_dealer_currency WHERE DealerID ='"+dealerid+"' AND Active='1' ) ");
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
			if(objArr[8].equals("true")){
				dto.setChecked(true);
			}else{
				dto.setChecked(false);
			}
			
			r.add(dto);
		}
		return r;
	}

	public void SaveOrUpdate(String dealerid, List<String> currencyids) {
		// TODO Auto-generated method stub
		List<DealerCurrency> dcs = dealercurrencyrepository.findByDealerid(dealerid);
		DealerCurrency dc=new DealerCurrency();
		if(dcs.size()>0){
			UpdateActive(dcs);
			for (int i = 0; i < currencyids.size(); i++) {
				dc=dealercurrencyrepository.findByDCId(dealerid, currencyids.get(i));
				if(dc!=null){
					dc.setActive("1");
					entityManager.merge(dc);
				}else{
					AddInfo(dealerid, currencyids.get(i));
				}
			}
			
		}else{
			for (int i = 0; i < currencyids.size(); i++) {
					AddInfo(dealerid, currencyids.get(i));
			}
		}
		
		
	}
	public void AddInfo(String dealerid, String currencyid){
		DealerCurrency dc=new DealerCurrency();
		dc.setId(GuidIdUtil.getGuId());
		dc.setDealerid(dealerid);
		dc.setCurrentyid(currencyid);
		dc.setActive("1");
		entityManager.persist(dc);
		
	}
	public void UpdateActive(List<DealerCurrency> dcs){
//		DealerCurrency dc= new DealerCurrency();
		for (DealerCurrency dc:dcs) {
//			dc=dealercurrencyrepository.findById(dcs.get(i).getId());
			dc.setActive("0");
			entityManager.merge(dc);
		}
		
	}

}
