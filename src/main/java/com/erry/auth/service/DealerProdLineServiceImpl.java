package com.erry.auth.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.dto.DealerRegNumDTO;
import com.erry.auth.model.DealerCurrency;
import com.erry.auth.model.DealerProdLine;
import com.erry.auth.model.Dictionary;
import com.erry.auth.model.ProdLine;
import com.erry.auth.repository.DealerCurrencyRepository;
import com.erry.auth.repository.DealerProdLineRepository;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class DealerProdLineServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private DealerProdLineRepository dealerprodlinerepository;

	public List<ProdLine> findList(String dealerid) {
		StringBuffer sql =new StringBuffer(" ");
		sql.append(" SELECT  p.*, 'true' checked FROM dbo.T_BPS_ProductLine p LEFT JOIN BPS_dealer_prodline dp ON p.ProdID=dp.ProdID ");
		sql.append("  WHERE   p.Active='1'  ");
		if(!StringUtil.isEmpty(dealerid)){
			sql.append(" AND dp.DealerID='"+dealerid+"' AND dp.Active='1'    ");
		}
		sql.append(" UNION ");
		
		sql.append(" SELECT p.*,'false'checked  FROM dbo.T_BPS_ProductLine p WHERE p.Active='1' ");
		if(!StringUtil.isEmpty(dealerid)){
			sql.append("  AND p.ProdID NOT IN (SELECT ProdID FROM dbo.BPS_dealer_prodline WHERE DealerID ='"+dealerid+"' AND Active='1' )  ");
		}
		Query query = entityManager.createNativeQuery(sql.toString());
		List<ProdLine> r = new ArrayList<ProdLine>();
		List<Object[]> objArrList = query.getResultList();
		for(Object[] objArr : objArrList)
		{
			ProdLine dto = new ProdLine();
			
			dto.setProdid(String.valueOf(objArr[0]));
			dto.setProdcode(String.valueOf(objArr[1]));
			dto.setProdname(String.valueOf(objArr[2]));
			dto.setRemark(String.valueOf(objArr[3]));
			dto.setActive(String.valueOf(objArr[4]));
			if(objArr[5].equals("true")){
				dto.setChecked(true);
			}else{
				dto.setChecked(false);
			}
			
			r.add(dto);
		}
		return r;
	}

	public void SaveOrUpdate(String dealerid, List<String> prodids) {
		// TODO Auto-generated method stub
		List<DealerProdLine> dps = dealerprodlinerepository.findByDealerid(dealerid);
		DealerProdLine dc=new DealerProdLine();
		if(dps.size()>0){
			UpdateActive(dps);
			for (int i = 0; i < prodids.size(); i++) {
				dc=dealerprodlinerepository.findByDPId(dealerid, prodids.get(i));
				if(dc!=null){
					dc.setActive("1");
					entityManager.merge(dc);
				}else{
					AddInfo(dealerid, prodids.get(i));
				}
			}
			
		}else{
			for (int i = 0; i < prodids.size(); i++) {
					AddInfo(dealerid, prodids.get(i));
			}
		}
		
		
	}
	public void AddInfo(String dealerid, String prodid){
		DealerProdLine dc=new DealerProdLine();
		dc.setId(GuidIdUtil.getGuId());
		dc.setDealerid(dealerid);
		dc.setProdid(prodid);
		dc.setRegmax("0");
		dc.setActive("1");
		entityManager.persist(dc);
		
	}
	public void UpdateActive(List<DealerProdLine> dps){
		for (DealerProdLine dp:dps) {
			dp.setActive("0");
			entityManager.merge(dp);
		}
		
	}

	public List<DealerRegNumDTO> findDealerRegList(String dealerid) {
		StringBuffer sql =new StringBuffer(" ");
		sql.append(" SELECT dp.DealerID,dp.ProdID,p.ProdName,dp.regmax FROM dbo.T_BPS_ProductLine p LEFT JOIN BPS_dealer_prodline dp ON p.ProdID=dp.ProdID  ");
		sql.append(" WHERE p.Active='1'  ");
		if(!StringUtil.isEmpty(dealerid)){
			sql.append(" AND dp.DealerID='"+dealerid+"' AND dp.Active='1'    ");
		}
		Query query = entityManager.createNativeQuery(sql.toString());
		List<DealerRegNumDTO> list = new ArrayList<DealerRegNumDTO>();
		List<Object[]> objArrList = query.getResultList();
		for(Object[] objArr : objArrList)
		{
			DealerRegNumDTO dto = new DealerRegNumDTO();
			dto.setDealerid(String.valueOf(objArr[0]));
			dto.setProdid(String.valueOf(objArr[1]));
			dto.setProdname(String.valueOf(objArr[2]));
			dto.setRegmax(String.valueOf(objArr[3]));
			list.add(dto);
		}
		return list;
	}

	public void SaveOrUpdateRegNum(List<DealerProdLine> dealerprodline) {
		// TODO Auto-generated method stub
		if(dealerprodline.size()>0){
			for (int i = 0; i < dealerprodline.size(); i++) {
				DealerProdLine dc=new DealerProdLine();
				dc=dealerprodlinerepository.findByDPId(dealerprodline.get(i).getDealerid(),dealerprodline.get(i).getProdid());
				if(dc.getId()!=null){
					dc.setActive("1");
					dc.setRegmax(dealerprodline.get(i).getRegmax());
					entityManager.merge(dc);
				}
			}
			
		}
		
		
	}

}
