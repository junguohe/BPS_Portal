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

import org.apache.poi.xslf.model.geom.Guide;
import org.hibernate.id.GUIDGenerator;
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
import com.erry.auth.dto.CustProdDTO;
import com.erry.auth.dto.CustomerDTO;
import com.erry.auth.model.CustProdLine;
import com.erry.auth.model.Customer;
import com.erry.auth.repository.CustProdLineRepository;
import com.erry.auth.repository.CustomerProdLineRepository;
import com.erry.auth.repository.CustomerRepository;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class CustProdLineServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;

	public CustProdLine saveCustProdLine(String prodid, String custid) {
		CustProdLine cust = new CustProdLine();

		cust.setCpid(GuidIdUtil.getGuId());
		cust.setProdid(prodid);
		cust.setCustid(custid);
		cust.setRegstatus("2");
		cust.setActive("1");
		cust.setCreatedate(new Date());
		entityManager.persist(cust);
		return cust;
	}

	public void updateCustProdLine(CustProdLine cust) {
		cust.setRegstatus("2");
		cust.setRegstartdate(null);
		entityManager.merge(cust);
	}
	
	public void updateCustProdLines(CustProdLine cust, String regstatus) {
		cust.setActive("0");
		entityManager.merge(cust);
	}
	
	public List<CustProdDTO> getCustProd(String custname){
		String sql = null;
		
		List<CustProdDTO> list = new ArrayList<CustProdDTO>();
		if(!StringUtil.isEmpty(custname)){
			sql = "select p.CPID,c.CustName,pl.ProdName from T_BPS_Customer_ProdLine p left join T_BPS_Customer_Info c on c.CustID = p.CustID left join T_BPS_ProductLine pl  on pl.ProdID = p.ProdID left join dbo.T_BPS_Cust_RegInfo reg on p.cpid=reg.cpid where p.Active='1' and reg.RegStatus='1'  and reg.isbps='1' and reg.active='1' and (c.CustCode like '%"+custname+"%' or c.CustName like '%"+custname+"%')  ";
		}else{
			sql = "select p.CPID,c.CustName,pl.ProdName from T_BPS_Customer_ProdLine p left join T_BPS_Customer_Info c on c.CustID = p.CustID left join T_BPS_ProductLine pl  on pl.ProdID = p.ProdID left join dbo.T_BPS_Cust_RegInfo reg on p.cpid=reg.cpid where p.Active='1' and reg.RegStatus='1'  and reg.isbps='1' and reg.active='1' ";
		}
		Query query = entityManager.createNativeQuery(sql);
		List<Object[]> objArrList = query.getResultList();
		for(Object[] objArr : objArrList)
		{
			CustProdDTO dto = new CustProdDTO();
			dto.setCpid(String.valueOf(objArr[0]));
			dto.setCustname(String.valueOf(objArr[1]));
			dto.setProdname(String.valueOf(objArr[2]));
			list.add(dto);
		}
		return list;
	}
}
