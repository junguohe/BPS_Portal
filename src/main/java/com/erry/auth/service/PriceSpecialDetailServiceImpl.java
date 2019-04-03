package com.erry.auth.service;




import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.model.PriceSpecialDetail;
import com.erry.auth.repository.PriceSpecialDetailRepository;
import com.erry.util.GuidIdUtil;
import com.erry.util.NumberUtil;
import com.erry.util.StringUtil;


@Service
@Transactional
public class PriceSpecialDetailServiceImpl {
	

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PriceSpecialDetailRepository priceSpecialDetailRepository;
	
	public String getNumber(String num){
		String sjiachun =num;
		BigDecimal db = new BigDecimal(sjiachun);
		String ii = db.toPlainString();
		return ii;
	}
	public PriceSpecialDetail savepPricespecialDetail(PriceSpecialDetail priceSpecialDetail, String spid) {
		// TODO Auto-generated method stub
		PriceSpecialDetail priceSpecialDetails=new PriceSpecialDetail();
		if(priceSpecialDetail.getDid().length()<36){
			priceSpecialDetails.setDid(GuidIdUtil.getGuId());
			priceSpecialDetails.setSpid(spid);
			priceSpecialDetails.setSeqno(priceSpecialDetail.getSeqno());
			priceSpecialDetails.setApplytype(priceSpecialDetail.getApplytype());
			priceSpecialDetails.setCpid(priceSpecialDetail.getCpid());
			priceSpecialDetails.setMaterialid(priceSpecialDetail.getMaterialid());
			priceSpecialDetails.setProjname(priceSpecialDetail.getProjname());
			priceSpecialDetails.setProjstatus(priceSpecialDetail.getProjstatus());
			priceSpecialDetails.setVolyear(priceSpecialDetail.getVolyear());
			priceSpecialDetails.setCompmaterial(priceSpecialDetail.getCompmaterial());
			priceSpecialDetails.setCurrency(priceSpecialDetail.getCurrency());
			priceSpecialDetails.setComppriceinc(getNumber(priceSpecialDetail.getComppriceinc()));
			priceSpecialDetails.setApplypriceinc(getNumber(priceSpecialDetail.getApplypriceinc()));
			priceSpecialDetails.setApplyprice(getNumber(priceSpecialDetail.getApplyprice()));
			priceSpecialDetails.setRemark(priceSpecialDetail.getRemark());
			priceSpecialDetails.setIsnotrebate(priceSpecialDetail.getIsnotrebate());
			priceSpecialDetails.setActive("1");

			entityManager.persist(priceSpecialDetails);
			
		}else{
			priceSpecialDetails=priceSpecialDetailRepository.findByDid(priceSpecialDetail.getDid());
			priceSpecialDetails.setSpid(spid);
			priceSpecialDetails.setSeqno(priceSpecialDetail.getSeqno());
			priceSpecialDetails.setApplytype(priceSpecialDetail.getApplytype());
			priceSpecialDetails.setCpid(priceSpecialDetail.getCpid());
			priceSpecialDetails.setMaterialid(priceSpecialDetail.getMaterialid());
			priceSpecialDetails.setProjname(priceSpecialDetail.getProjname());
			priceSpecialDetails.setProjstatus(priceSpecialDetail.getProjstatus());
			priceSpecialDetails.setVolyear(priceSpecialDetail.getVolyear());
			priceSpecialDetails.setCompmaterial(priceSpecialDetail.getCompmaterial());
			priceSpecialDetails.setCurrency(priceSpecialDetail.getCurrency());
			priceSpecialDetails.setComppriceinc(getNumber(priceSpecialDetail.getComppriceinc()));
			priceSpecialDetails.setApplypriceinc(getNumber(priceSpecialDetail.getApplypriceinc()));
			priceSpecialDetails.setApplyprice(getNumber(priceSpecialDetail.getApplyprice()));
			priceSpecialDetails.setRemark(priceSpecialDetail.getRemark());
			priceSpecialDetails.setIsnotrebate(priceSpecialDetail.getIsnotrebate());
			priceSpecialDetails.setActive("1");
			entityManager.merge(priceSpecialDetails);
		}
		return priceSpecialDetails;
	}

	public void updatePricespecialDetail(String did,String isnotrebate,String currency,String Materialid){
		PriceSpecialDetail priceSpecialDetails = priceSpecialDetailRepository.findByDid(did);
		if(priceSpecialDetails!=null){
			priceSpecialDetails.setSpid(priceSpecialDetails.getSpid()); 
			priceSpecialDetails.setSeqno(priceSpecialDetails.getSeqno());
			priceSpecialDetails.setApplytype(priceSpecialDetails.getApplytype());
			priceSpecialDetails.setCpid(priceSpecialDetails.getCpid());
			priceSpecialDetails.setMaterialid(Materialid);
			priceSpecialDetails.setProjname(priceSpecialDetails.getProjname());
			priceSpecialDetails.setProjstatus(priceSpecialDetails.getProjstatus());
			priceSpecialDetails.setVolyear(priceSpecialDetails.getVolyear());
			priceSpecialDetails.setCompmaterial(priceSpecialDetails.getCompmaterial());
			priceSpecialDetails.setCurrency(currency);
			
			int number = 4;
			
			if(!StringUtil.isEmpty(priceSpecialDetails.getComppriceinc())){
				
				String sjiachun = priceSpecialDetails.getComppriceinc();
				BigDecimal db = new BigDecimal(sjiachun);
				String ii = db.toPlainString();
				
				priceSpecialDetails.setComppriceinc(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(ii)),number).toString());
				
			}else{
				priceSpecialDetails.setComppriceinc(null);
			}
			if(!StringUtil.isEmpty(priceSpecialDetails.getApplypriceinc())){
				String sjiachun = priceSpecialDetails.getApplypriceinc();
				BigDecimal db = new BigDecimal(sjiachun);
				String ii = db.toPlainString();
				
				
				priceSpecialDetails.setApplypriceinc(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(ii)),number).toString());
			}else{
				priceSpecialDetails.setApplypriceinc(null);
			}
			if(!StringUtil.isEmpty(priceSpecialDetails.getApplyprice())){
				String sjiachun = priceSpecialDetails.getApplyprice();
				BigDecimal db = new BigDecimal(sjiachun);
				String ii = db.toPlainString();
				
				priceSpecialDetails.setApplyprice(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(ii)),number).toString());
			}else{
				priceSpecialDetails.setApplyprice(null);
			}
			priceSpecialDetails.setRemark(priceSpecialDetails.getRemark());
			priceSpecialDetails.setIsnotrebate(isnotrebate);
			priceSpecialDetails.setActive("1");
			entityManager.merge(priceSpecialDetails);
		}
	}
	
}
