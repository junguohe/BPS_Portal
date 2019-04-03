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
import com.erry.auth.dto.CustProdDTO;
import com.erry.auth.dto.CustomerDTO;
import com.erry.auth.model.Contact;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustBPS;
import com.erry.auth.model.CustProdLine;
import com.erry.auth.model.CustProjectInfo;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.Customer;
import com.erry.auth.repository.CustomerProdLineRepository;
import com.erry.auth.repository.CustomerRepository;
import com.erry.util.DateUtil;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class CustomerServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private CustomerProdLineRepository custProdLineRepo;

	@Autowired
	private CustProdLineServiceImpl custProdLineServiceImpl;
	@Autowired
	private CustAddressServiceImpl custAddressServiceImpl;

	@Autowired
	private ContactServiceImpl contactServiceImpl;

	@Autowired
	private CustProjectInfoServiceImpl projectServiceImpl;

	@Autowired
	private CustBPSServiceImpl custBpsServiceImpl;

	@Autowired
	private CustRegServiceImpl custRegServiceImpl;

	public Map<Integer, List<CustProdLine>> findCustList(int page, int size,
			final String name, final String prodid, final String regstatus,
			final String region, final String dealername, final String isshare,
			final String bpssales, final String bpsfae,
			final String parenetcompany, final String telno,
			final String isparent, final String bestlatertime,final String address,final String custtype,final String taxno) {
		Map<Integer, List<CustProdLine>> resultMap = new HashMap<Integer, List<CustProdLine>>();
		StringBuffer hql = new StringBuffer(" from T_BPS_Customer_ProdLine cpl " +
												"where cpl.active=1 " +
												"and cpl.custid in (" +
																	"select c.custid from T_BPS_Customer_Info c where c.custcode is not null)  " +
												"and cpl.cpid not in(" +
																	"select cr.cpid from T_BPS_Cust_RegInfo cr where (cr.regstatus='2' and cr.approveresult='3' " +
				"and cr.cpid not in( select cr.cpid from T_BPS_Cust_RegInfo cr where (cr.regstatus<>'2' and cr.approveresult<>'3')) )) ");
		if (!StringUtil.isEmpty(isparent)) {
			hql.append("and cpl.custid in (select c.custid from T_BPS_Customer_Info c where c.active=1 and c.isparent like '%" + isparent + "%' )");
		}
		if (!StringUtil.isEmpty(name)) {
		hql.append(" and cpl.custid in (select c.custid from T_BPS_Customer_Info c where c.active=1 and ( c.custcode like '%" + name
				+ "%' or c.custname like'%" + name + "%') ) ");
	}

	if (!StringUtil.isEmpty(region)) {
		hql.append(" and cpl.custid in (select c.custid from T_BPS_Customer_Info c where c.active=1 and c.region like '%" + region + "%' )");
	}

	if (!StringUtil.isEmpty(parenetcompany)) {
		hql.append(" and cpl.custid in (select c.custid from T_BPS_Customer_Info c where c.active=1 and c.parenetcompany like '%" + parenetcompany
				+ "%' )");
	}
	
	if(!StringUtil.isEmpty(custtype)){
		hql.append(" and cpl.cpid in ( select bps.cpid from T_BPS_Cust_BPSInfo bps where bps.active=1 and bps.custtype ='"+custtype+"' )");
	}
	if(!StringUtil.isEmpty(taxno)){
		hql.append(" and cpl.custid in (select c.custid from T_BPS_Customer_Info c where c.active=1 and c.taxno like '%" + taxno + "%' )");
	}
	if (!StringUtil.isEmpty(prodid)) {
		hql.append(" and cpl.prodid like '%" + prodid + "%' ");
	}

	if (!StringUtil.isEmpty(regstatus)) {
		hql.append(" and cpl.regstatus like '%"+ regstatus + "%' ");
	}

	if (!StringUtil.isEmpty(isshare)) {
		hql.append(" and cpl.cpid in (select bps.cpid from T_BPS_Cust_BPSInfo bps where bps.active=1 and bps.isshare LIKE '%"
				+ isshare + "%')");
	}

	if (!StringUtil.isEmpty(telno) ) {
		hql.append(" and  cpl.cpid in "
				+ "( select contact.cpid from T_BPS_Cust_ContactInfo contact where contact.active=1 and (contact.telno LIKE '%"
				+ telno + "%' or contact.mobile like '%"+telno+"%' ))" );
	}

	if (!StringUtil.isEmpty(bpssales) && !"".equals(bpssales)) {
		hql.append(" and cpl.cpid in "
				+ "( select bps.cpid from T_BPS_Cust_BPSInfo bps where bps.active=1 and cast(bps.bpssales as integer) in("
				+ "select person.empid from erp_person_v person where person.per_name like '%"+bpssales+"%'))");
	}

	if (!StringUtil.isEmpty(bpsfae) && !"".equals(bpsfae)) {
		hql.append(" and cpl.cpid in "
				+ "( select bps.cpid from T_BPS_Cust_BPSInfo bps where bps.active=1 and  cast(bps.bpsfae as integer) in("
				+ "select person.empid from erp_person_v person where person.per_name like '%"+bpsfae+"%'))");
	}

	if (!StringUtil.isEmpty(dealername) && !"".equals(dealername)) {
		hql.append(" and cpl.cpid in "
				+ "(select reg.cpid from T_BPS_Cust_RegInfo reg where reg.active=1 and reg.regstatus='1' and reg.approveresult='2' and reg.dealerid in "
				+ "( select dealer.dealerid from T_BPS_Dealerinfo dealer where dealer.active=1 and dealer.dealername LIKE '%"
				+ dealername + "%')" + ")");
	}
	if (!StringUtil.isEmpty(bestlatertime)) {
		hql.append("and cpl.regstatus = '1' and cpl.regstartdate <='"
				+  DateUtil.addDays(bestlatertime, 1) + "')");
	}

	if (!StringUtil.isEmpty(address)) {
		hql.append(" and cpl.cpid in "
				+ "(select add.cpid from T_BPS_Cust_Addinfo add where add.active=1 and  add.address like '%"+address+"%')");
	}


		// 查询总数
		Query queryCount = entityManager.createNativeQuery(" select count(*) "
				+ hql.toString());
		Integer totalCount = (Integer) queryCount.getSingleResult();

		// 查询分页数据
		Query query = entityManager.createNativeQuery(
				" select cpl.* " + hql.toString()+" order by cpl.regstartdate desc,cpl.regstatus,cpl.prodid,cpl.custid", CustProdLine.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		// 保存Map 总条数 分页后的集合
		resultMap.put(totalCount, query.getResultList());
		return resultMap;
	}

	public Map<Integer, List<Customer>> findCustLists(int page, int size,
			final String custname, final String taxno) {
		Map<Integer, List<Customer>> resultMap = new HashMap<Integer, List<Customer>>();
		// applyStatus 传过来的状态
		StringBuffer hql = new StringBuffer(
				" from Customer cust  where cust.active=1");

		if (!StringUtil.isEmpty(custname) && StringUtil.isEmpty(taxno)) {
			hql.append("and cust.custname = '" + custname + "' ");
		}

		if (!StringUtil.isEmpty(taxno) && StringUtil.isEmpty(custname)) {
			hql.append("and cust.taxno = '" + taxno + "' ");
		}

		if (!StringUtil.isEmpty(taxno) && !StringUtil.isEmpty(custname)) {
			hql.append("and (cust.taxno = '" + taxno + "' or cust.custname ='"
					+ custname + "')  ");
		}

		Query queryCount = entityManager.createQuery(" select count(cust.id) "
				+ hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();
		Query query = entityManager.createQuery(
				" select cust " + hql.toString(), Customer.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}

	public Page<Customer> findList(int page, int size, final String custname,
			final String custcode, final String prodid) {
		PageRequest pageable = new PageRequest(page, size, new Sort(new Order(
				Direction.ASC, "id")));
		Page<Customer> pSysLog = customerRepo.findAll(
				new Specification<Customer>() {
					public Predicate toPredicate(Root<Customer> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
						List<Expression<Boolean>> expressions = predicate
								.getExpressions();
						if (!StringUtil.isEmpty(custcode))
							expressions.add(cb.like(
									root.<String> get("custcode"), "%"
											+ custcode + "%"));
						if (!StringUtil.isEmpty(custname))
							expressions.add(cb.like(
									root.<String> get("custname"), "%"
											+ custname + "%"));
						if (!StringUtil.isEmpty(prodid))
							if (getCustomerByProdLine(prodid) != null) {
								expressions.add(cb.equal(
										root.<Customer> get("id"),
										getCustomerByProdLine(prodid).getId()));
							} else {
								expressions.add(cb.equal(
										root.<Customer> get("id"), prodid));
								;
							}
						return predicate;
					}
				}, pageable);
		return pSysLog;
	}

	public List<CustomerDTO> converTocustomerDTOList(List<Customer> list) {
		List<CustomerDTO> custList = new ArrayList<CustomerDTO>();
		for (Customer cust : list) {
			CustomerDTO custRegDTO = new CustomerDTO(cust);
			custList.add(custRegDTO);
		}
		return custList;
	}
	public List<CustProdDTO> converTocustProdDTOList(List<CustProdLine> list) {
		List<CustProdDTO> custList = new ArrayList<CustProdDTO>();
		for (CustProdLine cust : list) {
			CustProdDTO custRegDTO = new CustProdDTO(cust);
			custList.add(custRegDTO);
		}
		return custList;
	}
	public Customer getCustomerByProdLine(String prodid) {
		if (custProdLineRepo.findByProdid(prodid) == null) {
			return null;
		}
		return custProdLineRepo.findByProdid(prodid).getCust();
	}



	public Customer saveCustomers(Customer c) {
		Customer cust = new Customer();
		if (c.getId().length() != 36) {
			cust.setId(GuidIdUtil.getGuId());
			if(c.getCustcode()!=null||c.getCustcode()!=""){
			cust.setCustcode(c.getCustcode());
			}else{
				cust.setCustcode(c.getCustcode());
			}
			cust.setCustname(c.getCustname());
			cust.setIsparent(c.getIsparent());
			cust.setParenetcompany(c.getParenetcompany());
			cust.setTaxno(c.getTaxno());
			cust.setActive("1");
			cust.setCreator(CurrentUserInfo.getUid());
			cust.setCreatedate(new Date());
			cust.setUpdator(c.getUpdator());
			cust.setUpdatedate(c.getUpdatedate());
			cust.setRegion(c.getRegion());
			entityManager.persist(cust);
		} else {
			cust = customerRepo.findByCustid(c.getId());
			cust.setIsparent(c.getIsparent());
			cust.setCustname(c.getCustname());
			cust.setParenetcompany(c.getParenetcompany());
			cust.setTaxno(c.getTaxno());
			cust.setActive("1");
			cust.setUpdator(c.getUpdator());
			cust.setUpdatedate(c.getUpdatedate());
			cust.setRegion(c.getRegion());
			entityManager.merge(cust);
		}

		return cust;
	}

	//保存客户信息
		public Customer saveCustomer(Customer c,String prodid,List<CustAddress> address,List<Contact> contact,List<CustProjectInfo> project,List<CustBPS> custbps){
			Customer cust=saveCustomers(c);
			 if(cust!=null&&cust.getId()!=null){
				 //添加中间表
					CustProdLine prodLine= custProdLineServiceImpl.saveCustProdLine(prodid,cust.getId());
				 if(prodLine!=null&&prodLine.getCpid()!=null){
					 //添加报备表
					 CustReg reg=custRegServiceImpl.creatCustReg(prodLine.getCpid());
					 if(reg!=null &&reg.getId()!=null){
						 creatDetail( prodLine.getCpid(),address,contact, project,custbps);
					 }
				 }
			 }
			 
			return cust;
		}
		//修改客户信息
		public Customer saveCustomers(Customer c,String prodid,List<CustAddress> address,List<Contact> contact,List<CustProjectInfo> project,List<CustBPS> custbps){
			Customer cust=saveCustomers(c);
			 if(cust!=null&&cust.getId()!=null){
				creatDetail( prodid,address,contact, project,custbps);
			 }
			return cust;
		}

	public Customer deleteCustomer(Customer cust, String active) {
		cust.setActive(active);
		entityManager.merge(cust);
		return cust;
	}

	public List<Customer> findTransferCust(List<String> ids) {
		List<Customer> cust = new ArrayList<Customer>();
		for (int i = 0; i < ids.size(); i++) {
			Customer c = customerRepo.findByCustid(ids.get(i));
			cust.add(c);
		}
		return cust;
	}


	

	//保存明细信息
	//1
	public void creatDetail(String cpid,List<CustAddress> address,List<Contact> contact,List<CustProjectInfo> project,List<CustBPS> custbps){
			 
	 if(address.size()>0){
		 for (int i = 0; i < address.size(); i++)
         {
             CustAddress add=custAddressServiceImpl.saveAddress(address.get(i),cpid);
         }
	 }
	  if(contact.size()>0){
		  for (int i = 0; i < contact.size(); i++)
         {
			Contact  cont=contactServiceImpl.saveContact(contact.get(i),cpid);
         }  
	  }
	  if(project.size()>0){
		  for (int i = 0; i < project.size(); i++)
         {
			 CustProjectInfo proj=projectServiceImpl.saveProject(project.get(i),cpid);
         } 
	  }
	  
	  if (custbps.size() > 0) {
			for (int i = 0; i < custbps.size(); i++) {
				CustBPS	bps = custBpsServiceImpl.saveCustBPS(custbps.get(i),cpid);
			}
		}
	  

	}

	public List<Customer> getAllParent(){
		return customerRepo.findAllIsParent();
	}
	//获取母公司
	public List<Customer> findParent(final String name) {
		//StringBuffer hql = new StringBuffer(" from Customer c where c.active='1' and c.isparent ='1' and c.id in (select cp.custid from CustProdLine cp where cp.active='1' and cp.regstatus='1' and cp.cpid in (select cr.cpid from CustReg cr where cr.active='1' and cr.regstatus='1' and cr.approveresult='2')) ");
		List<Customer> resultList = new ArrayList<Customer>();
		StringBuffer sql = new StringBuffer("select c.* from dbo.T_BPS_Customer_Info c left join T_BPS_Customer_ProdLine cp on  cp.CustID=c.CustID and cp.Active='1' left join T_BPS_Cust_RegInfo cr on cr.CPID = cp.CPID and cr.Active='1' where c.Active='1' and cr.RegStatus='1' and cp.RegStatus='1' and cr.ApproveResult='2'  and c.IsParent=1 ");
		if(!StringUtil.isEmpty(name)){
			sql.append(" and c.custname like '%"+name+"%'");
		}
		Query query = entityManager.createNativeQuery(sql.toString());
		List<Object[]> arryObj = query.getResultList();
		for(Object[] c : arryObj){
			Customer dto = new Customer();
			dto.setId(String.valueOf(c[0]));
			dto.setCustcode(String.valueOf(c[1]));
			dto.setCustname(String.valueOf(c[2]));
			dto.setIsparent(String.valueOf(c[3]));
			dto.setParenetcompany(String.valueOf(c[4]));
			dto.setRegion(String.valueOf(c[5]));
			dto.setTaxno(String.valueOf(c[6]));
			dto.setActive(String.valueOf(c[7]));
			resultList.add(dto);
		}
		return resultList;  
	}

	public Customer findCustomer(String cpid) {
		// TODO Auto-generated method stub
		
		Customer model=new Customer();
		model=customerRepo.findCustByCpid(cpid);
		return model;
	}
	
}
