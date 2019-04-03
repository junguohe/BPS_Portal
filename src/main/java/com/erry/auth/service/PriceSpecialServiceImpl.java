package com.erry.auth.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.engine.spi.SessionImplementor;
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
import com.erry.auth.dto.ContactDTO;
import com.erry.auth.dto.CustAddressDTO;
import com.erry.auth.dto.CustRegDTO;
import com.erry.auth.dto.CustomerDTO;
import com.erry.auth.dto.PriceSpecialAuditDTO;
import com.erry.auth.dto.PriceSpecialAuditDTO2;
import com.erry.auth.dto.PriceSpecialDetailDTO;
import com.erry.auth.dto.PriceStrategyDetailDTO;
import com.erry.auth.model.AuUser;
import com.erry.auth.model.Contact;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustProdLine;
import com.erry.auth.model.CustProjectInfo;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.Customer;
import com.erry.auth.model.DealerInfo;
import com.erry.auth.model.PriceSpecial;
import com.erry.auth.model.PriceSpecialAudit;
import com.erry.auth.model.PriceSpecialDetail;
import com.erry.auth.repository.AuUserRepository;
import com.erry.auth.repository.ContactRepository;
import com.erry.auth.repository.CustAddressRepository;
import com.erry.auth.repository.CustRegRepository;
import com.erry.auth.repository.CustomerRepository;
import com.erry.auth.repository.DealerInfoRepository;
import com.erry.auth.repository.PriceSpecialAuditRepository;
import com.erry.auth.repository.PriceSpecialDetailRepository;
import com.erry.auth.repository.PriceSpecialRepository;
import com.erry.auth.repository.ProductLineRepository;
import com.erry.util.DateUtil;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class PriceSpecialServiceImpl {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PriceSpecialDetailServiceImpl specialDetailServiceImpl;
	
	@Autowired
	private PriceSpecialRepository priceSpecialRepository;
	
	@Autowired
	private AuUserRepository auUserRepository;
	

	@Autowired
	private PriceSpecialDetailRepository pricespecialdetailrepository;
	@Autowired
	private PriceSpecialAuditRepository  pricespecialauditrepository;
	
	
//	特价申请查询
	public  Map<Integer,List<PriceSpecialDetail>> findpriced(int page,
			int size,final String billno,final String billstatus,final String custcode,final String custname,
			final String applicator,final String applydealer,final String startdate,final String enddate,
			final String materialcode,final String materialname,final String isAccuracy,final String publicdate){
		Map<Integer, List<PriceSpecialDetail>> resultMap = new HashMap<Integer, List<PriceSpecialDetail>>();
		StringBuffer hql = new StringBuffer(" from PriceSpecialDetail psa left join psa.priceSpecial ps  where psa.active='1' ");
		hql.append("  and ps.applicator = '"+CurrentUserInfo.getUid()+"' ");
if(!StringUtil.isEmpty(billno)){
			hql.append(" and  ps.billno = '"+billno+"'");
		}
if(!StringUtil.isEmpty(billstatus)){
	hql.append(" and  ps.billstatus = '"+billstatus+"'");
}
if(!StringUtil.isEmpty(custname)){
	hql.append(" and psa.cpid in (select cp.cpid from CustProdLine cp where cp.custid in (select ci.id from Customer ci where ci.custname like '%"+custname+"%'))");
}
if(!StringUtil.isEmpty(custcode)){
	hql.append(" and psa.cpid in (select cp.cpid from CustProdLine cp where cp.custid in (select ci.id from Customer ci where ci.custcode like '%"+custcode+"%'))");
}
if(!StringUtil.isEmpty(applicator)){
	hql.append(" and ps.applicator like '%"+applicator+"%'");
}
if(!StringUtil.isEmpty(applydealer)){
	hql.append(" and ps.applydealer like '%"+applydealer+"%'");
}
if(!StringUtil.isEmpty(startdate) && !StringUtil.isEmpty(enddate)){
	hql.append(" and ps.applydate between '"+startdate+"' and '"+DateUtil.addDays(enddate, 1)+"'");
	
}
if(!StringUtil.isEmpty(startdate) && StringUtil.isEmpty(enddate)){
	hql.append(" and ps.applydate >= '"+startdate+"'");
	
}
if(StringUtil.isEmpty(startdate) && !StringUtil.isEmpty(enddate)){
	hql.append(" and  ps.applydate <='"+DateUtil.addDays(enddate, 1)+"'");
	
}
if(!StringUtil.isEmpty(materialcode)){
	hql.append(" and psa.materialid in (select mi.id from MaterialInfo mi where mi.materialcode like '%"+materialcode+"%')");
	
}
if(isAccuracy.equals("1")){
	if(!StringUtil.isEmpty(materialname)){
		hql.append(" and psa.materialid in (select mi.id from MaterialInfo mi where mi.materialname = '"+materialname+"')");
	}
}else{
	if(!StringUtil.isEmpty(materialname)){
		hql.append(" and psa.materialid in (select mi.id from MaterialInfo mi where mi.materialname like '%"+materialname+"%')");
	}
}
if(!StringUtil.isEmpty(publicdate)){
	hql.append(" and psa.did in (select au.did from PriceSpecialAudit au where au.approvedate between'"+publicdate+"' and '"+DateUtil.addDays(publicdate, 1)+"')");
}

		Query queryCount = entityManager.createQuery(" select count(*) "
				+ hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();

		// 查询分页数据
		Query query = entityManager.createQuery(
				" select psa " + hql.toString()+" order by ps.applydate desc", PriceSpecialDetail.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		// 保存Map 总条数 分页后的集合
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}
	//特价查询
	
	public  Map<Integer,List<PriceSpecialDetailDTO>>  findPriceSpeList(int page,
			int size, final String materialcode, final String materialname,
			final String isAccuracy, final String prodid,
			final String dealername, final String startdate,
			final String enddate, final String isRelease, final String name,
			final String isMain,String ispublic,String isnotrebate,String istax)
		{
		Map<Integer,List<PriceSpecialDetailDTO>> resultMap = new HashMap<Integer,List<PriceSpecialDetailDTO>>();
		StringBuffer hql = new StringBuffer("from dbo.T_BPS_Price_Special_Detail d ");
		hql.append("left join dbo.T_BPS_Price_Special s on d.SPID=s.SPID and s.Active='1' and d.Active='1' ");
		hql.append("left join dbo.T_BPS_Price_Special_Audit a on d.DID=a.DID and a.Active='1' ");
		hql.append("left join dbo.T_BPS_Customer_ProdLine cp on cp.CPID=d.CPID and cp.Active='1' ");
		hql.append("left join T_BPS_Customer_Info c on c.CustID=cp.CustID and c.Active='1' ");
		hql.append("left join T_BPS_ProductLine pl on pl.ProdID=cp.ProdID and pl.Active='1' ");
		hql.append("left join dbo.T_BPS_MaterialInfo m on m.MaterialID=d.MaterialID and m.Active='1' ");
		hql.append("where s.BillStatus='2' ");
		
	/*	hql.append("AND  a.id IN ( ");
		hql.append("SELECT TOP 1 id FROM T_BPS_Price_Special_Audit WHERE did IN ( ");
		hql.append("SELECT did from T_BPS_Price_Special_Detail where cpid=d.cpid and  materialid=d.materialid  ");
		hql.append(")ORDER BY ActiveDate  desc) ");*/

		if (!StringUtil.isEmpty(istax)) {
			hql.append(" and s.istax = '"+istax+"' ");
		}

		if (!StringUtil.isEmpty(materialcode)) {
			hql.append(" and m.materialcode like '%"+materialcode+"%' ");
		}
		if (isAccuracy.equals("1")) {
			if (!StringUtil.isEmpty(materialname)) {
				hql.append(" AND d.MaterialID IN (SELECT MaterialID FROM T_BPS_MaterialInfo WHERE materialname = '"+materialname+"') ");
			}
		} else {
			if (!StringUtil.isEmpty(materialname)) {
				hql.append(" AND d.MaterialID IN (SELECT MaterialID FROM T_BPS_MaterialInfo WHERE materialname like '%"+materialname+"%') ");
			}
		}

		if (!StringUtil.isEmpty(prodid)) {
			hql.append("  and cp.prodid ='"+prodid+"'");
		}
		if(!StringUtil.isEmpty(dealername)){
			hql.append(" and s.ApplyDealer in (select di.DealerID from T_BPS_DealerInfo di where di.DealerName like '%"+dealername+"%' or di.DealerCode like '%"+dealername+"%')");
		}
		if (!StringUtil.isEmpty(startdate) && !StringUtil.isEmpty(enddate)) {
			hql.append(" and ( "
					+ "(a.activedate between '"+startdate+"' and '"+enddate+"') "
					+ "or("
					+ "(a.activedate<='"+startdate+"')"
					+ "and("
					+ "((select top 1 activedate from T_BPS_Price_Special_Audit where did in(select did from T_BPS_Price_Special_Detail where cpid=d.cpid and  materialid=d.materialid ) and activedate>a.activedate)>'"+startdate+"')"
					+ "or"
					+ "((select top 1 activedate from T_BPS_Price_Special_Audit where did in(select did from T_BPS_Price_Special_Detail where cpid=d.cpid and  materialid=d.materialid )and activedate>a.activedate )is null)"
					+ ")))" );
		}
		if (StringUtil.isEmpty(startdate) && !StringUtil.isEmpty(enddate)) {
			hql.append("and ( ");
			hql.append("(a.activedate<='"+enddate+"') ");
			hql.append("and( ");
			hql.append("((select top 1 activedate from T_BPS_Price_Special_Audit where did in(select did from T_BPS_Price_Special_Detail where cpid=d.cpid and  materialid=d.materialid ) and activedate>a.activedate)>='"+enddate+"') ");
			hql.append("or ");
			hql.append("((select top 1 activedate from T_BPS_Price_Special_Audit where did in(select did from T_BPS_Price_Special_Detail where cpid=d.cpid and  materialid=d.materialid )and activedate>a.activedate )is null))) ");
		}
		/*查询特价  只查询生效的特价  如果有开始日期  则查这个日期之后的特价*/
		if (!StringUtil.isEmpty(startdate) && StringUtil.isEmpty(enddate)) {
			hql.append("and (a.activedate>'"+startdate+"') ");
		}
		if(!StringUtil.isEmpty(isnotrebate)){
			hql.append(" and d.isnotrebate = '"+isnotrebate+"'");
		}
		if(!StringUtil.isEmpty(name)){
			hql.append(" and c.custname like '%"+name+"%' or c.custcode like '%"+name+"%'");
		}
		
		//查询总数
		int sum=0;
		Query queryCount = entityManager.createNativeQuery(" select count(*) " + hql.toString());
		sum=Integer.valueOf(queryCount.getResultList().get(0).toString());
		
		//查询分页数据
		StringBuffer hql1 = new StringBuffer("select ");
		hql1.append("d.DID,d.SPID,d.SeqNo,d.ApplyType,d.CPID,d.MaterialID,d.ProjName,d.ProjStatus,d.VolYear,d.CompMaterial,a.Currency,d.ApplyPrice as ApplyPrice1,d.ApplyPriceInc as ApplyPriceInc1 ,d.CompPriceInc,d.Remark,d.IsnotRebate,d.Active, ");
		hql1.append("s.BillNo,s.BillStatus,s.ApplyDate, ");
		hql1.append("c.CustCode,c.CustName,c.Region, ");
		hql1.append("pl.ProdName, ");
		hql1.append("m.MaterialCode,m.MaterialName,m.Assembly,m.LifeCycle, ");
		hql1.append("a.IsSPL,a.AuditRemark,a.SugDealerSPL,a.SugDealerSPLInc,a.SugDealerProfit,a.SugCustSPL,a.ActiveDate,a.SugCustSPLInc,a.ApproveDate,a.LastSPL,a.LastSPLInc,a.LastDate,a.LastCustDate,a.LastCustSPL,a.LastCustSPLInc,a.LastQuantity,s.Applicator ");
		hql1.append(",a.Approver,s.istax,d.execqty ");
		Query query = entityManager.createNativeQuery(hql1.toString()+hql.toString()+" order by a.activedate desc");
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		List<PriceSpecialDetailDTO> list = new ArrayList<PriceSpecialDetailDTO>();
		List<Object[]> objArrList = query.getResultList();
		for(Object[] objArr : objArrList)
		{
			PriceSpecialDetailDTO dto = new PriceSpecialDetailDTO();
			dto.setDid(String.valueOf(objArr[0]));
			dto.setSpid(String.valueOf(objArr[1]));
			dto.setSeqno(String.valueOf(objArr[2]));
			dto.setApplytype(String.valueOf(objArr[3]));
			dto.setCpid(String.valueOf(objArr[4]));
			dto.setMaterialid(String.valueOf(objArr[5]));
			dto.setProjname(String.valueOf(objArr[6]));
			dto.setProjstatus(String.valueOf(objArr[7]));
			dto.setVolyear(String.valueOf(objArr[8]));
			dto.setCompmaterial(String.valueOf(objArr[9]));
			dto.setCurrency(String.valueOf(objArr[10]));
			dto.setApplyprice(String.valueOf(objArr[11]));
			dto.setApplypriceinc(String.valueOf(objArr[12]));
			dto.setComppriceinc(String.valueOf(objArr[13]));
			dto.setRemark(String.valueOf(objArr[14]));
			dto.setIsnotrebate(String.valueOf(objArr[15]));
			dto.setActive(String.valueOf(objArr[16]));
			dto.setBillno(String.valueOf(objArr[17]));
			dto.setBillstatus(String.valueOf(objArr[18]));
			dto.setApplydate(String.valueOf(objArr[19]));
			dto.setCustcode(String.valueOf(objArr[20]));
			dto.setCustname(String.valueOf(objArr[21]));
			dto.setRegion(String.valueOf(objArr[22]));
			dto.setProdname(String.valueOf(objArr[23]));
			dto.setMaterialcode(String.valueOf(objArr[24]));
			dto.setMaterialname(String.valueOf(objArr[25]));
			dto.setAssembly(String.valueOf(objArr[26]));
			dto.setLifecycle(String.valueOf(objArr[27]));
			dto.setIsspl(String.valueOf(objArr[28]));
			dto.setAuditremark(String.valueOf(objArr[29]));
			dto.setSugdealerspl(String.valueOf(objArr[30]));
			dto.setSugdealersplinc(String.valueOf(objArr[31]));
			dto.setSugdealerprofit(String.valueOf(objArr[32]));
			dto.setSugcustspl(String.valueOf(objArr[33]));
			dto.setActivedate(String.valueOf(objArr[34]));
			dto.setSugcustsplinc(String.valueOf(objArr[35]));
			dto.setApprovedate(String.valueOf(objArr[36]));
			dto.setLastspl(String.valueOf(objArr[37]));
			dto.setLastsplinc(String.valueOf(objArr[38]));
			dto.setLastdate(String.valueOf(objArr[39]));
			dto.setLastcustdate(String.valueOf(objArr[40]));
			dto.setLastcustspl(String.valueOf(objArr[41]));
			dto.setLastcustsplinc(String.valueOf(objArr[42]));
			dto.setLastquantity(String.valueOf(objArr[43]));
			dto.setApplycator(String.valueOf(objArr[44]));	
			dto.setApprover(String.valueOf(objArr[45]));
			dto.setIstax(String.valueOf(objArr[46]));
			dto.setExecqty(String.valueOf(objArr[47]));
			list.add(dto);
		}
		//保存Map 总条数 分页后的集合
		resultMap.put(sum, list);
		return resultMap;
	}
	
//新增报备
	public PriceSpecial savePriceSpecial(PriceSpecial price,List<PriceSpecialDetail> pricespecial) {
		PriceSpecial p = null;
		PriceSpecialDetail priceDetail = null;
			if (pricespecial.size() > 0) {
				for (int i = 0; i < pricespecial.size(); i++) {
					 p = savePriceSpecials(price);
					if (p != null && p.getSpid() != null) {
					priceDetail = specialDetailServiceImpl.savepPricespecialDetail(pricespecial.get(i),p.getSpid());
					PriceSpecialAudit audit = new PriceSpecialAudit();//创建特价申请审批记录
					PriceSpecial ps = priceSpecialRepository.findLastPrice(p.getApplicator(),priceDetail.getMaterialid() , priceDetail.getCpid());//查询是否有上次特价
					if(ps!=null){
						if(ps.getPriceSpecialDetails().size()>0){
							audit.setLastspl(ps.getPriceSpecialDetails().get(0).getApplyprice());
							audit.setLastsplinc(ps.getPriceSpecialDetails().get(0).getApplypriceinc());
						}
						if(ps.getPriceSpecialAudits().size()>0){
							audit.setLastcustspl(ps.getPriceSpecialAudits().get(0).getSugcustspl());
							audit.setLastcustsplinc(ps.getPriceSpecialAudits().get(0).getSugcustsplinc());
						}
					
					}
					
					audit.setId(GuidIdUtil.getGuId());	
					audit.setDid(priceDetail.getDid());
					audit.setApproveresult("0");
					audit.setSpid(p.getSpid());
					audit.setActive("1");
					entityManager.persist(audit);
				}
			}

		}
		
		return p;

	}

	private PriceSpecial savePriceSpecials(PriceSpecial price) {
		// TODO Auto-generated method stub
		PriceSpecial p=new PriceSpecial();
		if(price.getSpid().length()<36){
			p.setSpid(GuidIdUtil.getGuId());
			p.setBillno(get9Id());
			p.setBillstatus("1");
			p.setApplytype(price.getApplytype());
			p.setApplicator(CurrentUserInfo.getUid());
			p.setApplydate(new Date());
			p.setApplydealer(price.getApplydealer());
			p.setApplybps(CurrentUserInfo.getUid());
			p.setStatus("0");
			p.setActive("1");
			entityManager.persist(p);
		}else{
			p=priceSpecialRepository.findBySpid(price.getSpid());
			p.setBillno(price.getBillno());
			p.setBillstatus("1");
			p.setApplytype(price.getApplytype());
			p.setApplicator(CurrentUserInfo.getUid());
			p.setApplydate(price.getApplydate());
			p.setApplydealer(price.getApplydealer());
			p.setApplybps(CurrentUserInfo.getUid());
			p.setStatus("0");
			p.setActive("1");
			entityManager.merge(p);
		}
		return p;
	}
	public String get9Id() {

		SessionImplementor session = entityManager
				.unwrap(SessionImplementor.class);
		Connection conn = session.connection();
		String testPrint = null;
		try {
			conn.setAutoCommit(false);
			CallableStatement cstmt = conn
					.prepareCall("{ call proc_Getpriceno(?,?) }");
			cstmt.setString(1, "0");
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.execute();
			conn.commit();
			testPrint = cstmt.getString(2);
			//conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "SPL_"+testPrint;
	}


	public  Map<Integer,List<PriceSpecialAudit>> findList(int page, int size,
			final String dealername,final String billstatus,
			final String custcode,final String custname,
			final String billno,final String materialname,final String startdate,final String enddate,final String status,final String region)
		{
		Map<Integer,List<PriceSpecialAudit>> resultMap = new HashMap<Integer,List<PriceSpecialAudit>>();
		//applyStatus 传过来的状态
		StringBuffer hql = new StringBuffer(" from PriceSpecialAudit psa left join psa.priceSpecial ps left join psa.priceSpecialDetail psd where 1 = 1 and psa.active= '1' ");
		
		if(!StringUtil.isEmpty(dealername)){
			hql.append(" and ps.applydealer in"
					+"(select d.dealerid from DealerInfo d where d.dealercode like '%"+dealername+"%' or d.dealername like '%"+dealername+"%')");
		}
		
		if(!StringUtil.isEmpty(billstatus)){
			hql.append(" and ps.billstatus like'%"+billstatus+"%'");
		}
		
		if(!StringUtil.isEmpty(status)){
			hql.append(" and ps.status ='"+status+"'");
		}
		
		if(!StringUtil.isEmpty(custcode)){
			hql.append("  and psd.cpid in(select cp.cpid from CustProdLine cp where cp.custid "
					+" in(select c.id from Customer c where c.custcode like'%"+custcode+"%' ))");
		}
	
		if(!StringUtil.isEmpty(custname)){
			hql.append("  and psd.cpid in(select cp.cpid from CustProdLine cp where cp.custid "
					+" in(select c.id from Customer c where c.custname like'%"+custname+"%'))");
		}

		if(!StringUtil.isEmpty(region)){
			hql.append("and psd.cpid in(select cp.cpid from CustProdLine cp where cp.custid "
					+" in(select c.id from Customer c where c.region ='"+region+"'))");
		}
		if(!StringUtil.isEmpty(billno)){
			hql.append("  and ps.billno like'%"+billno+"%'");
		}
		
		if(!StringUtil.isEmpty(materialname)){
			hql.append("and psd.materialid in (select m.id from MaterialInfo m where m.materialname like'%"+materialname+"%' )");
		}
		if(!StringUtil.isEmpty(startdate)){
			
			hql.append(" and ps.applydate >='"+startdate+"'");
			//hql.append(" and psd.createdate >= '" + startdate + "'");
		}
		
		if(!StringUtil.isEmpty(enddate)){
			hql.append(" and ps.applydate< '"+DateUtil.addDays(enddate, 1)+"' ");
			//hql.append(" and psd.createdate < '" + DateUtil.addDays(enddate, 1) + "'");
		}
		
		
		//查询总数
		Query queryCount = entityManager.createQuery(" select count(psa.id) " + hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();
		
		//查询分页数据
		Query query = entityManager.createQuery(" select psa " + hql.toString()+" order by ps.applydate", PriceSpecialAudit.class);
		if(page!=0 && size !=0){
			query.setFirstResult(page * size);
			query.setMaxResults(size);
		}
		//保存Map 总条数 分页后的集合
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}

	public List<PriceSpecialDetailDTO> converToDTOList(
			List<PriceSpecialDetail> list) {
		List<PriceSpecialDetailDTO> priceList = new ArrayList<PriceSpecialDetailDTO>();
		for (PriceSpecialDetail price : list) {
			PriceSpecialDetailDTO custRegDTO = new PriceSpecialDetailDTO(price);
			AuUser dealer = auUserRepository.findByUid(price.getPriceSpecial().getApplicator());	
			custRegDTO.setDealername(dealer.getUsername());
			priceList.add(custRegDTO);
		}
		return priceList;
	}
	
	public List<PriceSpecialDetailDTO> converToDTOPriceList(List<PriceSpecialDetailDTO> list) {
		List<PriceSpecialDetailDTO> priceList = new ArrayList<PriceSpecialDetailDTO>();
		for (PriceSpecialDetailDTO price : list) {
			PriceSpecialDetailDTO custRegDTO = new PriceSpecialDetailDTO(price);
			AuUser dealer = auUserRepository.findByUid(price.getApplycator());	
			custRegDTO.setDealername(dealer.getUsername());
			dealer = auUserRepository.findUserById(price.getApprover());	
			custRegDTO.setApprover(dealer.getUsername());
			priceList.add(custRegDTO);
		}
		return priceList;
	}
	
	public List<PriceSpecialAuditDTO2> converToDTOList2(
			List<PriceSpecialAudit> list) {
		List<PriceSpecialAuditDTO2> priceList = new ArrayList<PriceSpecialAuditDTO2>();
		for (PriceSpecialAudit price : list) {
			PriceSpecialAuditDTO2 custRegDTO = new PriceSpecialAuditDTO2(price);
			AuUser dealer = auUserRepository.findByUid(price.getPriceSpecial().getApplicator());
			custRegDTO.setDealername(dealer.getUsername());
			AuUser dealer1 = auUserRepository.findByUid(price.getApprover());
			if(dealer1!=null){
			custRegDTO.setApprover(dealer1.getUsername());
			}
			priceList.add(custRegDTO);
		}
		return priceList;
	}



	public PriceSpecial updatePrice(String spid,String status) {
		// TODO Auto-generated method stub
		PriceSpecial p=priceSpecialRepository.findBySpid(spid);
		if(p!=null){
			p.setBillstatus(status);
			entityManager.merge(p);
		}
		return p;
	}

	public void updatePriceIsTax(String spid,String isTax) {
		PriceSpecial p=priceSpecialRepository.findBySpid(spid);
		if(p!=null){
			p.setIstax(isTax);
			entityManager.merge(p);
		}
	}

	public Boolean inActivePrice(String did, String spid) {
		// TODO Auto-generated method stub
		Boolean result=true;
		List<PriceSpecialDetail> priceDetailList = new ArrayList<PriceSpecialDetail>();
		priceDetailList=pricespecialdetailrepository.findListBySpid(spid);
		if(priceDetailList.size()==1){
			PriceSpecial price=new PriceSpecial();
			price=priceSpecialRepository.findById(spid);
			if(price!=null){
				
				String sql="update  T_BPS_Price_Special set Active=?1 where SPID= ?2";
				Query query=entityManager.createNativeQuery(sql);
				query.setParameter(1, "0");
				query.setParameter(2, price.getSpid());
				query.executeUpdate();
				
				
			}
			inActivePrices(did,spid);
			
			
		}else if(priceDetailList.size()>1){
			
			inActivePrices(did,spid);
		}else{
			result=false;
		}
		return result;
	}
	
	public void inActivePrices(String did, String spid) {
		PriceSpecialDetail detail=new PriceSpecialDetail();
		detail=pricespecialdetailrepository.findByDid(did);
		if(detail!=null){
//			detail.setActive("0");
//			entityManager.merge(detail);
			
			String sql="update  T_BPS_Price_Special_Detail set Active=?1 where DID= ?2";
			Query query=entityManager.createNativeQuery(sql);
			query.setParameter(1, "0");
			query.setParameter(2, detail.getDid());
			query.executeUpdate();
		}
		PriceSpecialAudit audit=new PriceSpecialAudit();
		audit=pricespecialauditrepository.findbysidandspid(did, spid);
		if(audit!=null){
//			audit.setActive("0");
//			entityManager.merge(audit);
			
			String sql="update  T_BPS_Price_Special_Audit set Active=?1 where SPID= ?2 and did=?3";
			Query query=entityManager.createNativeQuery(sql);
			query.setParameter(1, "0");
			query.setParameter(2, audit.getSpid());
			query.setParameter(3, audit.getDid());
			query.executeUpdate();
		}
	}
	
}
