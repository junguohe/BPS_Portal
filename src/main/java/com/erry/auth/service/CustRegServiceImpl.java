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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import com.erry.auth.dto.CustRegDTO;
import com.erry.auth.model.Contact;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustBPS;
import com.erry.auth.model.CustProdLine;
import com.erry.auth.model.CustProjectInfo;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.Customer;
import com.erry.auth.repository.ContactRepository;
import com.erry.auth.repository.CustAddressRepository;
import com.erry.auth.repository.CustBPSRepository;
import com.erry.auth.repository.CustProdLineRepository;
import com.erry.auth.repository.CustProjectInfoRepository;
import com.erry.auth.repository.CustRegRepository;
import com.erry.auth.repository.CustomerProdLineRepository;
import com.erry.auth.repository.CustomerRepository;
import com.erry.util.DateUtil;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class CustRegServiceImpl {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CustRegRepository custRegRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private CustomerProdLineRepository customerProdRepo;
	@Autowired
	private CustProdLineRepository custProdLineRepo;
	@Autowired
	private MailService mailService;
	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private CustBPSRepository custBPSRepository;

	@Autowired
	private CustAddressRepository custAddressRepository;

	@Autowired
	private CustProjectInfoRepository custProjectInfoRepository;

	@Autowired
	private CustProdLineServiceImpl custProdService;

	// //name:name,prodid:prodid,regstatus:regstatus,dealername:dealername,startdate:startdate,enddate:enddate
	public Map<Integer, List<CustReg>> findCustRegList(int page, int size,
			final String name, final String prodid, final String approveresult,
			final String dealername, final String startdate,
			final String enddate, final String regstatus) {
		Map<Integer, List<CustReg>> resultMap = new HashMap<Integer, List<CustReg>>();
		// applyStatus 传过来的状态
		StringBuffer hql = new StringBuffer(
				" from CustReg cr where 1 = 1 and cr.active= '1'  ");

		if (!StringUtil.isEmpty(name)) {
			hql.append(" and cr.cpid in ( select cpl.cpid from  CustProdLine cpl where cpl.active=1 and cpl.custid in "
					+ "(select customer.id from Customer customer where customer.active=1  and (customer.custcode like '%"
					+ name
					+ "%' or customer.custname like'%"
					+ name
					+ "%')  ))");
		}

		if (!StringUtil.isEmpty(regstatus)) {
			hql.append(" and  cr.regstatus = '0' ");
		}

		if (!StringUtil.isEmpty(startdate)) {
			hql.append(" and cr.createdate >= '" + startdate + "'");
		}

		if (!StringUtil.isEmpty(enddate)) {
			hql.append(" and cr.createdate < '" + DateUtil.addDays(enddate, 1)
					+ "'");
		}
		if (!StringUtil.isEmpty(approveresult)) {
			hql.append(" and cr.approveresult = '" + approveresult + "'");
		}

		if (!StringUtil.isEmpty(prodid)) {
			hql.append(" and cr.cpid in  "
					+ "(select cpl.cpid from CustProdLine cpl where cpl.active=1  and cpl.prodid like '%"
					+ prodid + "%' )");
		}

		if (!StringUtil.isEmpty(dealername)) {
			hql.append(" and cr.dealerid in  "
					+ "(select dealer.dealerid from DealerInfo dealer where dealer.active=1  and dealer.dealername like '%"
					+ dealername + "%' )");
		}

		// 查询总数
		Query queryCount = entityManager.createQuery(" select count(cr.id) "
				+ hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();

		// 查询分页数据
		Query query = entityManager.createQuery(" select cr " + hql.toString()
				+ " order by cr.createdate", CustReg.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		// 保存Map 总条数 分页后的集合
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}

	// 冲突
	public Map<Integer, List<CustReg>> findCustLists(int page, int size,
			final String custname, final String taxno, final String id) {
		Map<Integer, List<CustReg>> resultMap = new HashMap<Integer, List<CustReg>>();
		// applyStatus 传过来的状态
		StringBuffer hql = new StringBuffer(
				" from CustReg cr where 1 = 1 and cr.active= 1 ");

		if (!StringUtil.isEmpty(custname) && StringUtil.isEmpty(taxno)) {
			hql.append("and  cr.cpid in(select custprod.cpid from CustProdLine custprod where custprod.custid in"
					+ "(select c.id from Customer c  where c.custname='"
					+ custname + "') )");
		}

		if (!StringUtil.isEmpty(taxno) && StringUtil.isEmpty(custname)) {
			hql.append("and  cr.cpid in(select custprod.cpid from CustProdLine custprod where custprod.custid in"
					+ "(select c.id from Customer c  where c.taxno='"
					+ taxno
					+ "') )");
		}

		if (!StringUtil.isEmpty(taxno) && !StringUtil.isEmpty(custname)) {
			hql.append("and  cr.cpid in(select custprod.cpid from CustProdLine custprod where custprod.custid in"
					+ "(select c.id from Customer c  where c.taxno='"
					+ taxno
					+ "' or c.custname='" + custname + "') )");
		}

		// 查询总数
		Query queryCount = entityManager.createQuery(" select count(cr.id) "
				+ hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();

		// 查询分页数据
		Query query = entityManager.createQuery(" select cr " + hql.toString(),
				CustReg.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		// 保存Map 总条数 分页后的集合
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}

	public Page<CustReg> findList(int page, int size, final String custname,
			final String custcode, final String startdate, final String enddate) {
		PageRequest pageable = new PageRequest(page, size, new Sort(new Order(
				Direction.ASC, "id")));
		Page<CustReg> pSysLog = custRegRepo.findAll(
				new Specification<CustReg>() {
					public Predicate toPredicate(Root<CustReg> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
						List<Expression<Boolean>> expressions = predicate
								.getExpressions();

						if (!StringUtil.isEmpty(custname)) {
							if (getProdId(custname) != null) {
								// expressions.add(cb.like(root.<String>get("cpid"),
								// "%" + custname + "%"));
								expressions.add(cb.equal(
										root.<String> get("cpid"),
										getProdId(custname).getCpid()));
							} else {
								expressions.add(cb.equal(
										root.<String> get("cpid"), custname));
							}
						}
						if (!StringUtil.isEmpty(custcode)) {
							if (getProdIds(custcode) != null) {
								// expressions.add(cb.like(root.<String>get("cpid"),
								// "%" + custname + "%"));
								expressions.add(cb.equal(
										root.<String> get("cpid"),
										getProdId(custcode).getCpid()));

							} else {
								expressions.add(cb.equal(
										root.<String> get("cpid"), custcode));
							}
						}

						if (startdate != null && !"".equals(startdate)) {
							expressions.add(cb.greaterThanOrEqualTo(
									root.<Date> get("regstartdate"),
									DateUtil.parse(startdate, "yyyy-MM-dd")));
						}

						if (enddate != null && !"".equals(enddate)) {
							expressions.add(cb.lessThan(root
									.<Date> get("regenddate"), DateUtil
									.addDays(DateUtil.parse(enddate,
											"yyyy-MM-dd"), 1)));
						}

						return predicate;
					}
				}, pageable);
		return pSysLog;
	}

	public List<CustRegDTO> converTocustRegDTOList(List<CustReg> list) {
		List<CustRegDTO> custList = new ArrayList<CustRegDTO>();
		for (CustReg cust : list) {
			CustRegDTO custRegDTO = new CustRegDTO(cust);
			custList.add(custRegDTO);
		}
		return custList;
	}

	public CustProdLine getProdId(String custname) {
		Customer cust = customerRepo.findByCustname(custname);
		String id = cust.getId();
		CustProdLine prod = customerProdRepo.findByCustid(id);
		return prod;
	}

	public CustProdLine getProdIds(String custcode) {

		Customer cust = customerRepo.findByCustcode(custcode);
		String id = cust.getId();
		CustProdLine prod = customerProdRepo.findByCustid(id);
		return prod;
	}

	public CustReg creatCustReg(CustReg c) {
		CustReg cust = new CustReg();
		cust.setId(GuidIdUtil.getGuId());
		cust.setCpid(c.getCpid());
		cust.setIsbps("1");
		cust.setDealerid(null);
		cust.setRegstatus("0");
		cust.setRegstartdate(c.getRegstartdate());
		cust.setRegenddate(c.getRegenddate());
		cust.setCreatedate(new Date());
		cust.setUpdatedate(c.getUpdatedate());
		cust.setRemark(c.getRemark());
		cust.setApprover(c.getApprover());
		cust.setApprovedate(c.getApprovedate());
		cust.setApproveresult("0");
		cust.setApproveremark(c.getApproveremark());
		cust.setCreator(CurrentUserInfo.getUid());
		cust.setUpdator(c.getUpdator());
		cust.setActive("1");
		entityManager.persist(cust);
		return cust;
	}

	public CustReg saveOrUpdate(CustReg custReg) {
		CustReg c = creatCustReg(custReg);

		return c;
	}
	
	// 取消报备
	public List<CustReg> cancelActive(List<String> ids) {
		List<CustReg> cust = new ArrayList<CustReg>();
		for (int i = 0; i < ids.size(); i++) {

			List<CustReg> c = custRegRepo.findByCpidOpen(ids.get(i));
			if (c.size() > 0) {
				for (int j = 0; j < c.size(); j++) {
					if (c.get(j) != null && c.get(j).getId() != null) {
						c.get(j).setActive("0");
						if (c.get(j).getRegstatus().equals("1")) {
							c.get(j).setRegenddate(new Date());
							c.get(j).setUpdatedate(new Date());
							c.get(j).setUpdator(CurrentUserInfo.getUid());
						} else {
							c.get(j).setRegenddate(null);
							c.get(j).setUpdatedate(new Date());
							c.get(j).setUpdator(CurrentUserInfo.getUid());
						}
						entityManager.merge(c.get(j));
						if (c.get(j) != null && c.get(j).getId() != null) {
							if (custProdLineRepo.findIds(c.get(j).getCpid()) != null) {
								custProdService
										.updateCustProdLine(custProdLineRepo
												.findIds(c.get(j).getCpid()));
							}
						}
						cust.add(c.get(j));

						Customer model = customerRepo.findCustBycpid(c.get(j)
								.getCpid());
						if (model != null) {
							// 修改客户母公司
							model.setIsparent("1");
							model.setParenetcompany(null);
							entityManager.merge(model);
							// open客户时发送邮件
							boolean en = model.getRegion().equals("海外")?true:false;
							mailService.createCustOpenMail(c.get(j).getDealerid(),model.getCustname(), "Open",en);
							// System.out.println(c.get(i).getDealerid());
						}

					}
				}
			}
		}
		return cust;
	}

	// 通过 reg
	// 1
	public CustReg updateCustregs(CustReg cust, String status) {
		cust.setRegstatus("1");
		cust.setRegstartdate(new Date());
		cust.setApprover(CurrentUserInfo.getUid());
		cust.setApprovedate(new Date());
		cust.setApproveresult("2");
		cust.setUpdatedate(new Date());
		cust.setUpdator(CurrentUserInfo.getUid());
		// cust.setRemark("审批通过");
		cust.setApproveremark("审批通过");
		entityManager.merge(cust);
		return cust;
	}

	// 通过 可多条
	// 1
	public CustReg updateCustreg(CustReg custreg, String status, String cpid,
			String custid) {
		CustReg custReg = null;
		CustProdLine line = null;
		custReg = updateCustregs(custreg, status);
		if (custReg != null && custReg.getCpid() != null
				&& custReg.getCpid().equals(cpid)) {
			line = custProdLineRepo.findById(custReg.getCpid());
			if (line != null) {
				line.setRegstatus("1");
				line.setRegstartdate(new Date());
				line.setUpdatedate(new Date());
				line.setUpdator(CurrentUserInfo.getUid());
				entityManager.merge(line);
			}
		}// 通过时 客户生成code
			// for(String id : custid){
		Customer c = customerRepo.findByCustid(custid);
		if (StringUtil.isEmpty(c.getCustcode())) {
			c.setCustcode(get9Id());
		} else {
			c.setCustcode(c.getCustcode());
		}
		entityManager.merge(c);
		// }
		return custReg;
	}

	// 驳回 审批通过时驳回
	// 1
	public CustReg updateRegStatus(CustReg cust) {
		cust.setRegstatus("2");
		cust.setApproveresult("3");
		cust.setApprovedate(new Date());
		cust.setApproveremark("您报备的客户已被其他经销商报备成功!");
		cust.setUpdatedate(new Date());
		cust.setUpdator(CurrentUserInfo.getUid());
		entityManager.merge(cust);
		updateInActive(cust.getCpid(), cust.getCreator());
		return cust;
	}

	public void updateInActive(String cpid, String creator) {
		List<CustAddress> address = custAddressRepository.findCustAddress(cpid,
				creator);
		if (address.size() > 0) {
			for (int i = 0; i < address.size(); i++) {
				CustAddress custAdd = custAddressRepository.findById(address
						.get(i).getId());
				custAdd.setActive("0");
				entityManager.merge(custAdd);
			}
		}
		List<Contact> contact = contactRepository.findContact(cpid, creator);
		if (contact.size() > 0) {
			for (int i = 0; i < contact.size(); i++) {
				Contact contacts = contactRepository.findById(contact.get(i)
						.getId());
				contacts.setActive("0");
				entityManager.merge(contacts);
			}
		}
		List<CustProjectInfo> project = custProjectInfoRepository
				.findCustProject(cpid, creator);
		if (project.size() > 0) {
			for (int i = 0; i < project.size(); i++) {
				CustProjectInfo custProjectInfo = custProjectInfoRepository
						.findById(project.get(i).getId());
				custProjectInfo.setActive("0");
				entityManager.merge(custProjectInfo);
			}
		}
		List<CustBPS> custbps = custBPSRepository.findCustBPS(cpid, creator);
		if (custbps.size() > 0) {
			for (int i = 0; i < custbps.size(); i++) {
				CustBPS cust = custBPSRepository.findById(custbps.get(i)
						.getId());
				cust.setActive("0");
				entityManager.merge(cust);
			}
		}

	}

	// 驳回
	public CustReg updateCustregStatus(CustReg cust, String remark) {
		CustProdLine line = null;
		CustReg custReg = updateRegStatut(cust, remark);
		if (custReg != null && custReg.getCpid() != null) {
			line = custProdLineRepo.findById(custReg.getCpid());
			if (line != null) {
				line.setRegstatus("2");
				line.setUpdatedate(new Date());
				line.setUpdator(CurrentUserInfo.getUid());
				entityManager.merge(line);
			}
		}
		updateInActive(cust.getCpid(), cust.getCreator());
		return custReg;
	}

	public CustReg updateRegStatut(CustReg cust, String remark) {
		cust.setRegstatus("2");
		cust.setApproveresult("3");
		cust.setApproveremark(remark);
		cust.setApprovedate(new Date());
		cust.setUpdatedate(new Date());
		cust.setUpdator(CurrentUserInfo.getUid());
		entityManager.merge(cust);
		return cust;
	}

	// 待处理
	public CustReg updateCustre(CustReg cust) {
		cust.setRegstatus("0");
		cust.setApproveresult("1");
		cust.setUpdatedate(new Date());
		cust.setUpdator(CurrentUserInfo.getUid());
		entityManager.merge(cust);
		return cust;
	}

	// 转移
	public List<CustReg> updateDealer(List<String> ids, String dealerid) {
		List<CustReg> cust = new ArrayList<CustReg>();
		CustProdLine line = null;
		for (int i = 0; i < ids.size(); i++) {
			List<CustReg> c = custRegRepo.findByCpids(ids.get(i));
			if (c.size() == 0) {
				break;
			} else {
				for (int j = 0; j < c.size(); j++) {
					if (c.get(j) != null && c.get(j).getId() != null) {
						if (c.get(j).getRegstatus() != null
								&& c.get(j).getRegstatus().equals("1")) {
							c.get(j).setRegenddate(new Date());
							c.get(j).setActive("0");
							// c.get(j).setUpdatedate(new Date());
							// c.get(j).setUpdator(CurrentUserInfo.getUid());
							entityManager.merge(c.get(j));
						} else {
							c.get(j).setActive("0");
							// c.get(j).setUpdatedate(new Date());
							// c.get(j).setUpdator(CurrentUserInfo.getUid());
							entityManager.merge(c.get(j));
						}

					} else {
						continue;
					}
				}
			}

			CustReg cReg = new CustReg();
			cReg.setId(GuidIdUtil.getGuId());
			cReg.setDealerid(dealerid);
			cReg.setCpid(ids.get(i));
			cReg.setIsbps("0");
			cReg.setCreatedate(new Date());
			cReg.setRegstartdate(new Date());
			cReg.setApprovedate(new Date());
			;
			cReg.setCreator(CurrentUserInfo.getUid());
			cReg.setUpdatedate(new Date());
			cReg.setUpdator(CurrentUserInfo.getUid());
			cReg.setActive("1");
			cReg.setRegstatus("1");
			cReg.setApproveresult("2");
			entityManager.persist(cReg);
			line = custProdLineRepo.findById(ids.get(i));
			line.setRegstatus("1");
			line.setRegstartdate(new Date());
			cust.add(cReg);

		}

		return cust;
	}

	// 添加报备信息
	// 1
	public CustReg creatCustReg(String cpid) {
		CustReg cust = new CustReg();
		cust.setId(GuidIdUtil.getGuId());
		cust.setCpid(cpid);
		cust.setDealerid(null);
		cust.setRegstatus("0");
		cust.setRegstartdate(null);
		cust.setRegenddate(null);
		cust.setRemark(null);
		cust.setApprover(null);
		cust.setApprovedate(null);
		cust.setApproveresult("0");
		cust.setApproveremark(null);
		cust.setIsbps("1");
		cust.setActive("1");
		cust.setCreatedate(new Date());
		cust.setCreator(CurrentUserInfo.getUid());
		cust.setUpdatedate(null);
		cust.setUpdator(null);
		entityManager.persist(cust);
		return cust;
	}

	// 报备驳回的客户
	public CustReg updateCustReg(CustReg reg) {
		reg.setRegstatus("0");
		reg.setApproveresult("0");
		reg.setCreatedate(new Date());
		entityManager.merge(reg);
		return reg;
	}

	public CustReg saveCustRegss(String cpid) {
		CustReg c = creatCustReg(cpid);

		return c;
	}

	public String get9Id() {

		SessionImplementor session = entityManager
				.unwrap(SessionImplementor.class);
		Connection conn = session.connection();
		String testPrint = null;
		try {
			conn.setAutoCommit(false);
			CallableStatement cstmt = conn
					.prepareCall("{ call proc_GetDealerNo(?,?) }");
			cstmt.setString(1, "9");
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.execute();
			conn.commit();
			testPrint = cstmt.getString(2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return testPrint;
	}

	public CustReg UploadRegDate(CustReg model) {
		Date regstartdate = model.getRegstartdate();
		if (regstartdate.getMonth() <= 9) {
			regstartdate.setMonth(regstartdate.getMonth() + 3);
		} else {
			regstartdate.setMonth(regstartdate.getMonth() + 3 - 12);
			regstartdate.setYear(regstartdate.getYear() + 1);
		}
		model.setRegstartdate(regstartdate);
		entityManager.merge(model);

		CustProdLine prod = new CustProdLine();
		if (model != null && model.getCpid() != null) {
			prod = custProdLineRepo.findById(model.getCpid());
			if (prod != null) {
				prod.setRegstartdate(regstartdate);
				entityManager.merge(prod);
			}
		}
		return model;
	}

}
