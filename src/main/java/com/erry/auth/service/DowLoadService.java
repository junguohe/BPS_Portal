package com.erry.auth.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.dto.CustDowload;
import com.erry.auth.dto.PriceDowLoad;
import com.erry.util.DateUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class DowLoadService {
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<CustDowload> dowloadCustList(String name,String region,String parenetcompany,String custtype,String taxno,String prodid,String regstatus,String isshare,String bpsfae,String bpssales,String telno,String dealername,String address){
		List<CustDowload> list = new ArrayList<CustDowload>();
		StringBuffer sql = new StringBuffer(" SELECT cust.CustName,cust.CustCode,cust.TaxNo,cust1.CustName as parentname,productLine.ProdName,addinfo.AddType,addinfo.Province,addinfo.City,addinfo.Address,addinfo.Remark,contact.Title,contact.Name,contact.Mobile,contact.TelNo, contact.EMail,contact.IM,bps.IsShare,bps.CustType,epv.per_name as bpssales,epv1.per_name as bpsfae,bps.CustCategory1,bps.CustCategory2,bps.CustCategory3,bps.DealerSales,dealer.DealerName,reg.RegStartDate,cust.Region "
				+" FROM  dbo.T_BPS_Customer_ProdLine prodline  "
				+" left join T_BPS_ProductLine productLine on productLine.ProdID=prodline.ProdID "
				+" left JOIN  dbo.T_BPS_Customer_Info cust ON cust.CustID = prodline.CustID and cust.Active='1' "
				 +" left JOIN  dbo.T_BPS_Customer_Info cust1 ON cust.ParenetCompany = cust1.CustID  "
				+" left join dbo.T_BPS_Cust_AddInfo addinfo ON addinfo.CPID = prodline.CPID and addinfo.Active='1' "
				+" left join dbo.T_BPS_Cust_ContactInfo contact ON contact.CPID = prodline.CPID and contact.Active='1' "
				+"left join dbo.T_BPS_Cust_BPSInfo bps ON bps.CPID = prodline.CPID and bps.Active='1' "
				+" left join dbo.T_BPS_Cust_ProjectInfo project ON project.CPID = prodline.CPID  and project.Active='1' "
				+"LEFT JOIN  dbo.T_BPS_Cust_RegInfo reg ON reg.CPID = prodline.CPID  and reg.Active='1'  "
				+"left join [dbo].[erp_Person_v] epv on epv.EmpID = bps.BPSSales  and epv.fempgroup='sales' and epv.Active='1' "
				+"left join [dbo].[erp_Person_v] epv1 on epv1.EmpID = bps.BPSFAE  and epv1.fempgroup='FAE' and epv1.Active='1' "
				+"left join T_BPS_DealerInfo dealer on dealer.DealerID=reg.DealerID and dealer.Active='1'  where 1=1  and (reg.RegStatus<>'2' and reg.ApproveResult<>'3' or (reg.RegStatus is null and cust.CustID is not null)) "
				);
		
		if (!name.equals("null")) {
			sql.append(" and (cust.CustCode like '%"+name+"%' or cust.CustName like '%"+name+"%') ");
		}

		if (!region.equals("null")) {
			sql.append(" and cust.Region like '%" + region + "%'");
		}

		if (!parenetcompany.equals("null")) {
			sql.append(" and cust.ParenetCompany like '%" + parenetcompany+"%' ");
		}
	
		if(!custtype.equals("null")){
			sql.append(" and bps.CustType ='"+custtype+"'");
		}
		if(!taxno.equals("null")){
			sql.append(" and cust.taxno like '%" + taxno + "%'");
		}
		if (!prodid.equals("null")) {
			sql.append(" and prodline.ProdID like '%" + prodid + "%' ");
		}

		if (!regstatus.equals("null")) {
			sql.append(" and prodline.regstatus like '%"+ regstatus + "%' " );
//			if(regstatus.equals("2")){
////				sql.append("   and prodline.CPID not in (select r.CPID from T_BPS_Cust_RegInfo r where r.RegStatus ='2') ");
//			}else{
//				sql.append("and reg.CPID is not null ");
//			}
		}

		if (!isshare.equals("null")) {
			sql.append(" and  bps.IsShare LIKE '%"+ isshare + "%'");
		}

		if (!telno.equals("null") ) {
			sql.append(" and  (contact.TelNo LIKE '%"+ telno + "%' or contact.mobile like '%"+telno+"%' )" );
		}

		if (!bpssales.equals("null")) {
			sql.append(" and bps.BPSSales in(select person.empid from dbo.erp_Person_v person where person.per_name like '%"+bpssales+"%')");
		}

		if (!bpsfae.equals("null")) {
			sql.append(" and bps.BPSFAE in(select person.empid from dbo.erp_Person_v person where person.per_name like '%"+bpsfae+"%')");
		}
		
		if (!dealername.equals("null")) {
			sql.append(" and reg.DealerID in (select dealer.dealerid from dbo.T_BPS_DealerInfo dealer where dealer.active=1 and dealer.dealername LIKE '%"+dealername+"%')");
		}
//		if (!StringUtil.isEmpty(bestlatertime)) {
//			sql.append("and cpl.RegStatus = '1' and prodline.RegStartDate <='"
//				+  DateUtil.addDays(bestlatertime, 1) + "')");
//		}

		if (!address.equals("null")) {
			sql.append(" and addinfo.Address like '%"+address+"%'");
		}

		Query query = entityManager.createNativeQuery(sql.toString());
		List<Object[]> objArrList = query.getResultList();
		for(Object[] objArr : objArrList)
		{
			CustDowload c = new CustDowload();
			c.setCust(String.valueOf(objArr[0]));
			c.setCustcode(String.valueOf(objArr[1]));
			c.setTaxno(String.valueOf(objArr[2]));
			c.setParen(String.valueOf(objArr[3]));
			c.setProdid(String.valueOf(objArr[4]));
			c.setAddtype(String.valueOf(objArr[5]));
			c.setProvince(String.valueOf(objArr[6]));
			c.setCity(String.valueOf(objArr[7]));
			c.setAddress(String.valueOf(objArr[8]));
			c.setRemark(String.valueOf(objArr[9]));
			c.setTitle(String.valueOf(objArr[10]));
			c.setName(String.valueOf(objArr[11]));
			c.setMobile(String.valueOf(objArr[12]));
			c.setTelno(String.valueOf(objArr[13]));
			c.setEmial(String.valueOf(objArr[14]));
			c.setIm(String.valueOf(objArr[15]));
			c.setIsshare(String.valueOf(objArr[16]));
			c.setCusttype(String.valueOf(objArr[17]));
			c.setBpssales(String.valueOf(objArr[18]));
			c.setBpsfae(String.valueOf(objArr[19]));
			c.setC1(String.valueOf(objArr[20]));
			c.setC2(String.valueOf(objArr[21]));
			c.setC3(String.valueOf(objArr[22]));
			c.setDealersales(String.valueOf(objArr[23]));
			c.setDealername(String.valueOf(objArr[24]));
			if(!StringUtil.isEmpty(String.valueOf(objArr[25]))&&!String.valueOf(objArr[25]).equals("null")){
				c.setRegstartdate(DateUtil.format(DateUtil.parse(String.valueOf(objArr[25])),DateUtil.PATTERN_DATE));
			}
			c.setRegion(String.valueOf(objArr[26]));
			list.add(c);
		}
		return list;
	}
	public List<PriceDowLoad> dowloadPriceList(String materialcode,String materialname,String isAccuracy,String prodid,String name,String dealername,String startdate,String enddate,String isRelease,String isMain,String istax){
		List<PriceDowLoad> list = new ArrayList<PriceDowLoad>();
		StringBuffer sql  = new StringBuffer(" SELECT  dealer.DealerName,c.CustName,c.CustCode,detail.ApplyType,m.MaterialCode ,m.MaterialName,detail.ProjName,detail.ProjStatus,detail.VolYear, "
				+"detail.CompMaterial,detail.CompPriceInc,detail.ApplyPrice,detail.ApplyPriceInc,detail.Remark,detail.IsnotRebate,audit.SugCustSPL,audit.SugCustSPLInc,audit.SugDealerSPL,"
				+"audit.SugDealerSPLInc,audit.SugDealerProfit,audit.IsSPL,audit.IsRebate,special.ApplyDate,audit.SPLNo,audit.AuditRemark,us.UserName,audit.ActiveDate,audit.approvedate ,audit.currency,audit.id,special.istax  "
				+"FROM dbo.T_BPS_Price_Special_Audit audit "
				+"left JOIN  dbo.T_BPS_Price_Special_Detail detail ON detail.DID = audit.DID and detail.Active='1'  and audit.Active='1' "
				+"left JOIN  dbo.T_BPS_Price_Special special ON special.SPID = audit.SPID  and special.Active='1' "
				+"left join dbo.T_BPS_Customer_ProdLine cp on cp.CPID=detail.CPID and cp.Active='1' "
				+"left join dbo.T_BPS_DealerInfo dealer on dealer.DealerID = special.ApplyDealer and dealer.Active='1' "
				+"left join T_BPS_Customer_Info c on c.CustID=cp.CustID and c.Active='1'  "
				+"left join dbo.T_BPS_MaterialInfo m on m.MaterialID=detail.MaterialID and m.Active='1' "
				+"left join T_BPS_User us on us.UserID=audit.Approver  and us.Active='1' ");
		sql.append(" where special.BillStatus='2' ");
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz", Locale.ENGLISH);
		//SimpleDateFormat sdf = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
		String newStrat=null;
		String newEnd=null;
		try {
			if(startdate!=null&&!startdate.equals("null")){
				Date s = sdf.parse(startdate);
				SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
				newStrat=sdf1.format(s);
			}
			if(enddate!=null&&!enddate.equals("null")){
				Date ed = sdf.parse(enddate);
				SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
				newEnd=sdf1.format(ed);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!materialcode.equals("null")) {
			sql.append(" and detail.MaterialID in (select MaterialID from T_BPS_MaterialInfo where MaterialCode like '%"+materialcode+"%') ");
		}
		if (isAccuracy.equals("1")) {
			if (!materialname.equals("null")) {
				sql.append(" and detail.MaterialID in (select MaterialID from T_BPS_MaterialInfo where MaterialName like '%"+materialname+"%') ");
			}
		} else {
			if (!materialname.equals("null")) {
				sql.append(" and detail.MaterialID in (select MaterialID from T_BPS_MaterialInfo where MaterialName like '%"+materialname+"%') ");
			}
		}

		if (!prodid.equals("null")) {
			sql.append(" and detail.CPID in (select cp.CPID from T_BPS_Customer_ProdLine cp where cp.ProdID ='"+prodid+"') ");
		}
		if(!dealername.equals("null")){
			sql.append(" and special.ApplyDealer in (select DealerID from T_BPS_DealerInfo where DealerCode like '%"+dealername+"%' or DealerName like '%"+dealername+"%') ");
		} 
		if (!StringUtil.isEmpty(newStrat) && !StringUtil.isEmpty(newEnd)) {
			sql.append(" and ( "
					+ "(audit.activedate between '"+newStrat+"' and '"+newEnd+"') "
					+ "or("
					+ "(audit.activedate<='"+newStrat+"')"
					+ "and("
					+ "((select top 1 activedate from T_BPS_Price_Special_Audit where did in(select did from T_BPS_Price_Special_Detail where cpid=detail.cpid and  materialid=detail.materialid ) and activedate>audit.activedate)>'"+newStrat+"')"
					+ "or"
					+ "((select top 1 activedate from T_BPS_Price_Special_Audit where did in(select did from T_BPS_Price_Special_Detail where cpid=detail.cpid and  materialid=detail.materialid )and activedate>audit.activedate )is null)"
					+ ")))" );
		}
		if (StringUtil.isEmpty(newStrat) && !StringUtil.isEmpty(newEnd)) {
			sql.append("and ( ");
			sql.append("(audit.activedate<'"+newEnd+"') ");
			sql.append("and( ");
			sql.append("((select top 1 activedate from T_BPS_Price_Special_Audit where did in(select did from T_BPS_Price_Special_Detail where cpid=detail.cpid and  materialid=detail.materialid ) and activedate>audit.activedate)>'"+newEnd+"') ");
			sql.append("or ");
			sql.append("((select top 1 activedate from T_BPS_Price_Special_Audit where did in(select did from T_BPS_Price_Special_Detail where cpid=detail.cpid and  materialid=detail.materialid )and activedate>audit.activedate )is null))) ");
		}
		if (!StringUtil.isEmpty(newStrat) && StringUtil.isEmpty(newEnd)) {
			sql.append(" and (audit.activedate>'"+newStrat+"') ");
		}
//		if (!StringUtil.isEmpty(isMain)) {
//			sql.append(" and  psd.ismain ='" + isMain + "' ");
//		}
		if(!name.equals("null")){
			sql.append(" and detail.CPID in (select cp.CPID from T_BPS_Customer_ProdLine cp where cp.CustID in (select c.CustID from T_BPS_Customer_Info c where c.CustName like '%"+name+"%' or c.CustCode like '%"+name+"%'))");
		}

		if(!istax.equals("null")){
			sql.append(" and special.istax='"+istax+"'");
		}
		Query query = entityManager.createNativeQuery(sql.toString());
		List<Object[]> objArrList = query.getResultList();
		for(Object[] objArr : objArrList)
		{
			PriceDowLoad p = new PriceDowLoad();
			p.setDealername(String.valueOf(objArr[0]));
			p.setCust(String.valueOf(objArr[1]));
			p.setCustcode(String.valueOf(objArr[2]));
			p.setApplytype(String.valueOf(objArr[3]));
			p.setMaterialcode(String.valueOf(objArr[4]));
			p.setMaterialname(String.valueOf(objArr[5]));
			p.setProjname(String.valueOf(objArr[6]));
			p.setProjstatus(String.valueOf(objArr[7]));
			p.setVolyear(String.valueOf(objArr[8]));
			p.setCompmaterial(String.valueOf(objArr[9]));
			p.setComppriceinc(String.valueOf(objArr[10]));
			p.setApplyprice(String.valueOf(objArr[11]));
			p.setApplypriceinc(String.valueOf(objArr[12]));
			p.setRemark(String.valueOf(objArr[13]));
			p.setIsnotrebate(String.valueOf(objArr[14]));
			p.setSugcustspl(String.valueOf(objArr[15]));
			p.setSugcustsplinc(String.valueOf(objArr[16]));
			p.setSugdealerspl(String.valueOf(objArr[17]));
			p.setSugdealersplinc(String.valueOf(objArr[18]));
			p.setSugdealerprofit(String.valueOf(objArr[19]));
			p.setIsspl(String.valueOf(objArr[20]));
			p.setIsrebate(String.valueOf(objArr[21]));
			p.setApplydate(String.valueOf(objArr[22]));
			p.setBillno(String.valueOf(objArr[23]));
			p.setAuditremark(String.valueOf(objArr[24]));
			p.setApprover(String.valueOf(objArr[25]));
			p.setActivedate(String.valueOf(objArr[26]));
			p.setApprovedate(String.valueOf(objArr[27]));
			p.setCurrency(String.valueOf(objArr[28]));
			p.setId(String.valueOf(objArr[29]));
			p.setIstax(String.valueOf(objArr[30]));
			list.add(p);
		}
		return list;
	}
}
