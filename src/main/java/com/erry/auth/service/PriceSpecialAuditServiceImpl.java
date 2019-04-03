package com.erry.auth.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.component.CurrentUserInfo;
import com.erry.auth.dto.PriceSpecialAuditDTO;
import com.erry.auth.model.PriceSpecial;
import com.erry.auth.model.PriceSpecialAudit;
import com.erry.auth.model.PriceSpecialDetail;
import com.erry.auth.repository.PriceSpecialAuditRepository;
import com.erry.auth.repository.PriceSpecialDetailRepository;
import com.erry.auth.repository.PriceSpecialRepository;
import com.erry.util.DateUtil;
import com.erry.util.NumberUtil;

@Service
@Transactional
public class PriceSpecialAuditServiceImpl {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private PriceSpecialRepository priceSpecialRepository;

	@Autowired
	private PriceSpecialDetailRepository detailRepository;
	
	@Autowired
	private PriceSpecialAuditRepository auditRepository;

	public List<PriceSpecialAuditDTO> converTopriceDTOList(
			List<PriceSpecialAudit> list) {
		List<PriceSpecialAuditDTO> lists = new ArrayList<PriceSpecialAuditDTO>();
		for (PriceSpecialAudit p : list) {
			PriceSpecialAuditDTO dto = new PriceSpecialAuditDTO(p);
			lists.add(dto);
		}
		return lists;

	}
	public List<PriceSpecialAudit> findByDate(String did,Date appdate,String spid){
		PriceSpecialDetail pd = detailRepository.findBydid(did);
		List<PriceSpecialAudit> list = new ArrayList<PriceSpecialAudit>();
		list = auditRepository.findbysidandspid(pd.getCpid(),pd.getMaterialid(),appdate,did,spid);
		return list;
	}
	public PriceSpecialAudit creatAudit(PriceSpecialAudit audit, String value) {
		PriceSpecialAudit p = saveOrUpdateAudit(audit, value);
		if (p != null && p.getId() != null) {
			PriceSpecial pricespecial = priceSpecialRepository.findBySpid(p.getSpid());
			if (pricespecial != null && pricespecial.getSpid() != null) {
				if (value.equals("审批通过")) {
					//updatePrice(audit);
					pricespecial.setStatus("1");
					pricespecial.setBillstatus("2");
				} else {
					pricespecial.setStatus("2");
					pricespecial.setBillstatus("3");
				}

				entityManager.merge(pricespecial);
			}
		}
		return p;
	}
//失效掉上一个特价
	public void updatePrice(PriceSpecialAudit audit){
		PriceSpecial ps = priceSpecialRepository.findBySpid(audit.getSpid());
		PriceSpecialDetail psd = detailRepository.findByDid(audit.getDid());
		PriceSpecialAudit a = auditRepository.findlastPriceAudit(ps.getApplicator(), psd.getCpid(), psd.getMaterialid());
		if(a!=null){
			PriceSpecial ps1 = a.getPriceSpecial();
			ps1.setActive("0");
			PriceSpecialDetail psd1 = a.getPriceSpecialDetail();
			psd1.setActive("0");
			a.setActive("0");
			entityManager.merge(ps1);
			entityManager.merge(psd1);
			entityManager.merge(a);
		}
	}
	public PriceSpecialAudit saveOrUpdateAudit(PriceSpecialAudit audit,
			String value) {
		PriceSpecialAudit p = auditRepository.findbysidandspid(audit.getDid(),audit.getSpid());
			int number = 4;
//			if(p.getCurrency()!=null&&p.getCurrency().equals("USD")){
//				number=4;
//			}
			if (audit.getSugcustspl() == ""||audit.getSugcustspl() == null) {
				p.setSugcustspl(null);
			} else {
				String sjiachun =audit.getSugcustspl();
				BigDecimal db = new BigDecimal(sjiachun);
				String ii = db.toPlainString();
				p.setSugcustspl(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(ii)),number).toString());
			}
			if (audit.getSugdealerspl() == ""||audit.getSugdealerspl() == null) {
				p.setSugdealerspl(null);
			} else {
				String sjiachun =audit.getSugdealerspl();
				BigDecimal db = new BigDecimal(sjiachun);
				String ii = db.toPlainString();
				p.setSugdealerspl(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(ii)),number).toString());
			}
			if (audit.getSugdealersplinc() == ""||audit.getSugdealersplinc() == null) {
				p.setSugdealersplinc(null);
			} else {
				String sjiachun =audit.getSugdealersplinc();
				BigDecimal db = new BigDecimal(sjiachun);
				String ii = db.toPlainString();
				p.setSugdealersplinc(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(ii)),number).toString());
			}
			if (audit.getSugcustsplinc() == ""||audit.getSugcustsplinc() == null) {
				p.setSugcustsplinc(null);
			} else {
				String sjiachun =audit.getSugcustsplinc();
				BigDecimal db = new BigDecimal(sjiachun);
				String ii = db.toPlainString();
				p.setSugcustsplinc(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(ii)),number).toString());
			}
			if (audit.getSugdealerprofit() == ""||audit.getSugdealerprofit() == null) {
				p.setSugdealerprofit(null);
			} else {
				String sjiachun =audit.getSugdealerprofit();
				BigDecimal db = new BigDecimal(sjiachun);
				String ii = db.toPlainString();
				p.setSugdealerprofit(ii);
			}
			if(audit.getLastcustdate()==null){
				p.setLastcustdate(null);
			}else{
				p.setLastcustdate(audit.getLastcustdate());
			}
			if(audit.getLastdate()==null){
				p.setLastdate(null);
			}else{
				p.setLastdate(audit.getLastdate());
			}
			p.setSplno(audit.getSplno());
			p.setBpssales(audit.getBpssales());
			p.setCurrency(audit.getCurrency());
			p.setIsspl(audit.getIsspl());
			p.setIsrebate(audit.getIsrebate());
			
			if (audit.getActivedate()==null) {
				p.setActivedate(null);
			} else {
				p.setActivedate(DateUtil.roundDate(audit.getActivedate()));
			}
			p.setAuditremark(audit.getAuditremark());
			p.setApprover(CurrentUserInfo.getUid());
			p.setApprovedate(new Date());
			if (value.equals("审批通过")) {
				p.setApproveresult("2");
			} else {
				p.setApproveresult("3");
			}

			p.setActive("1");
			entityManager.merge(p);
		return p;
	}
	//修改特价信息
	public PriceSpecialAudit updataPriceAudit(PriceSpecialAudit audit) {
		PriceSpecialAudit p = auditRepository.findbysidandspid(audit.getDid(),audit.getSpid());
			if(p!=null){
				p.setCurrency(audit.getCurrency());
				
				int number = 4;
//				if(audit.getCurrency()!=null&&audit.getCurrency().equals("USD")){
//					number=4;
//				}
				if (audit.getSugcustspl() == ""||audit.getSugcustspl() == null) {
					p.setSugcustspl(null);
				} else {
					String sjiachun =audit.getSugcustspl();
					BigDecimal db = new BigDecimal(sjiachun);
					String ii = db.toPlainString();
					p.setSugcustspl(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(ii)),number).toString());
				}
				if (audit.getSugdealerspl() == ""||audit.getSugdealerspl() == null) {
					p.setSugdealerspl(null);
				} else {
					String sjiachun =audit.getSugdealerspl();
					BigDecimal db = new BigDecimal(sjiachun);
					String ii = db.toPlainString();
					p.setSugdealerspl(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(ii)),number).toString());
				}
				if (audit.getSugdealersplinc() == ""||audit.getSugdealersplinc() == null) {
					p.setSugdealersplinc(null);
				} else {
					String sjiachun =audit.getSugdealersplinc();
					BigDecimal db = new BigDecimal(sjiachun);
					String ii = db.toPlainString();
					p.setSugdealersplinc(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(ii)),number).toString());
				}
				if (audit.getSugcustsplinc() == ""||audit.getSugcustsplinc() == null) {
					p.setSugcustsplinc(null);
				} else {
					String sjiachun =audit.getSugcustsplinc();
					BigDecimal db = new BigDecimal(sjiachun);
					String ii = db.toPlainString();
					p.setSugcustsplinc(NumberUtil.setScale(BigDecimal.valueOf(Double.valueOf(ii)),number).toString());
				}
				if (audit.getSugdealerprofit() == ""||audit.getSugdealerprofit() == null) {
					p.setSugdealerprofit(null);
				} else {
					String sjiachun =audit.getSugdealerprofit();
					BigDecimal db = new BigDecimal(sjiachun);
					String ii = db.toPlainString();
					p.setSugdealerprofit(ii);
				}
			if (audit.getLastcustdate()==null){
					p.setLastcustdate(null);
				}else{
					p.setLastcustdate(audit.getLastcustdate());
				}
				if(audit.getLastdate()==null){
					p.setLastdate(null);
				}else{
					p.setLastdate(audit.getLastdate());
				}
				p.setIsspl(audit.getIsspl());
				
				p.setIsrebate(audit.getIsrebate());
				p.setActivedate(DateUtil.roundDate(audit.getActivedate()));
				p.setAuditremark(audit.getAuditremark());
				p.setSplno(audit.getSplno());
				p.setApprover(CurrentUserInfo.getUid());
				p.setApprovedate(new Date());
				p.setActive("1");
			}
			entityManager.merge(p);
		return p;

	}
}
