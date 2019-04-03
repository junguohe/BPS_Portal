package com.erry.auth.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
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
import com.erry.auth.dto.UploadTaskInfoDTO;
import com.erry.auth.model.Contact;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustProjectInfo;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.Customer;
import com.erry.auth.model.DealerInfo;
import com.erry.auth.model.DealerUploadReSale;
import com.erry.auth.model.UploadInventory;
import com.erry.auth.model.UploadInventoryAdjustment;
import com.erry.auth.model.UploadTaskInfo;
import com.erry.auth.model.UserDealer;
import com.erry.auth.repository.AuUserRepository;
import com.erry.auth.repository.ContactRepository;
import com.erry.auth.repository.CustAddressRepository;
import com.erry.auth.repository.CustomerRepository;
import com.erry.auth.repository.UploadInventoryAdjustmentRepository;
import com.erry.auth.repository.UploadInventoryRepository;
import com.erry.auth.repository.UploadReSaleRepository;
import com.erry.util.DateUtil;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;
import com.erry.auth.repository.UploadTaskInfoRepository;

@Service
@Transactional
public class UploadTaskInfoServiceImpl {

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private UploadTaskInfoRepository uploadtaskinforepository;
	@Autowired
	private UploadReSaleRepository uploadresalerepository;
	@Autowired
	private UploadInventoryRepository uploadinventoryrepository;
	@Autowired
	private UploadInventoryAdjustmentRepository uploadinventoryadjustmentrepository;
	@Autowired
	private UploadReSaleService uploadresaleservice;
	@Autowired
	private UploadInventoryAdjustmentService uploadinventoryadjustmentservice;
	@Autowired
	private UploadInventoryService uploadinventoryservice;

	@Autowired
	private AuUserRepository auuserrepository;

	public Map<Integer, List<UploadTaskInfo>> findList(int page, int size,
													   final String period, final String dealername, String remark,
													   final String taskconfirm, final String taskcontent) {

		Map<Integer, List<UploadTaskInfo>> resultMap = new HashMap<Integer, List<UploadTaskInfo>>();
		StringBuffer hql = new StringBuffer(
				" from UploadTaskInfo t where  t.active= '1'  ");

		if (!StringUtil.isEmpty(period)) {
			hql.append("and t.period like '%" + period + "%' ");
		}
		if (!StringUtil.isEmpty(dealername)) {
			hql.append(" and t.creator in(select dealer.dealerid from DealerInfo dealer where dealer.dealername like '%"
					+ dealername + "%' )  ");
		}
		if (!StringUtil.isEmpty(remark)) {
			hql.append("and t.remark like '%" + remark + "%' ");
		}

		if (!StringUtil.isEmpty(taskconfirm)) {
			hql.append(" and t.taskconfirm like '%" + taskconfirm + "%' ");
		}
		if (!StringUtil.isEmpty(taskcontent)) {
			hql.append(" and t.taskcontent = '" + taskcontent + "' ");
		}
		// 查询总数
		Query queryCount = entityManager.createQuery(
				" select count(*) " + hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();

		// 查询分页数据
		Query query = entityManager.createQuery(" select t " + hql.toString(),
				UploadTaskInfo.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		// 保存Map 总条数 分页后的集合
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}

	public List<UploadTaskInfoDTO> converToUploadtaskinfoList(
			List<UploadTaskInfo> list) {
		List<UploadTaskInfoDTO> custList = new ArrayList<UploadTaskInfoDTO>();
		for (UploadTaskInfo uploadtaskinfo : list) {
			UploadTaskInfoDTO uploadtaskinfodto = new UploadTaskInfoDTO(uploadtaskinfo);
			if (!uploadtaskinfo.getTaskconfirm().equals("9") && !uploadtaskinfo.getTaskconfirm().equals("0")) {
				if (!StringUtil.isEmpty(uploadtaskinfo.getUpdator())) {
					uploadtaskinfodto.setUpdator(auuserrepository.findUserById(uploadtaskinfo.getUpdator()).getUsername());
				}

			}

			custList.add(uploadtaskinfodto);
		}
		return custList;
	}

	public UploadTaskInfo UpdateTaskInfo(String tid, String taskconfirm) {
		// TODO Auto-generated method stub
		UploadTaskInfo uploadtaskinfo = new UploadTaskInfo();
		uploadtaskinfo = uploadtaskinforepository.findById(tid);
		uploadtaskinfo.setTaskconfirm(taskconfirm);
		uploadtaskinfo.setUpdatedate(new Date());
		uploadtaskinfo.setUpdator(CurrentUserInfo.getUid());
		entityManager.merge(uploadtaskinfo);
		return uploadtaskinfo;
	}

	public UploadTaskInfo deleteTask(String tid, String type, String remark) {
		if (type.equals("resale")) {
			List<DealerUploadReSale> list = new ArrayList<DealerUploadReSale>();
			list = uploadresalerepository.fingByTid(tid);
			if (list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					uploadresaleservice.deleteSale(list.get(j).getId());
				}
			}
		} else if (type.equals("inventory")) {
			List<UploadInventory> list = new ArrayList<UploadInventory>();
			list = uploadinventoryrepository.fingByTid(tid);
			if (list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					uploadinventoryservice.deleteSale(list.get(j).getId());
				}
			}
		} else {
			List<UploadInventoryAdjustment> list = new ArrayList<UploadInventoryAdjustment>();
			list = uploadinventoryadjustmentrepository.fingByTid(tid);
			if (list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					uploadinventoryadjustmentservice.deleteSale(list.get(j).getId());
				}
			}
		}

		return deleteTasks(tid, type, remark);

	}

	public UploadTaskInfo deleteTasks(String tid, String type, String remark) {
		// TODO Auto-generated method stub
		UploadTaskInfo uploadtaskinfo = new UploadTaskInfo();
		uploadtaskinfo = uploadtaskinforepository.findById(tid);
//		 uploadtaskinfo.setActive("0");
		uploadtaskinfo.setTaskconfirm("2");
		uploadtaskinfo.setRemark(remark);
		uploadtaskinfo.setUpdatedate(new Date());
		uploadtaskinfo.setUpdator(CurrentUserInfo.getUid());
		entityManager.merge(uploadtaskinfo);
		return uploadtaskinfo;
	}

	public List<UploadTaskInfo> findTaskOwnerList(int i, Integer limit,
												  String taskowner) {
		StringBuffer hql = new StringBuffer(
				" from UploadTaskInfo t where  t.active= '1' and t.taskconfirm='9' ");

		if (!StringUtil.isEmpty(taskowner)) {
			hql.append(" and t.taskowner = '" + taskowner + "' ");
		}
		Query query = entityManager.createQuery(" select t " + hql.toString(),
				UploadTaskInfo.class);
		return query.getResultList();
	}

	public UploadTaskInfo saveUploadTaskInfo(String period, String taskcontent,
											 String type) {
		UploadTaskInfo task = new UploadTaskInfo();
		task.setId(GuidIdUtil.getGuId());
		task.setTaskseq(get9Id("BPS", type));
		task.setTaskowner("0");
		task.setPeriod(period);
		task.setTaskcontent(taskcontent);
		task.setRemark("");
		task.setTaskconfirm("9");
		task.setActive("1");
		task.setCreator(CurrentUserInfo.getUid());
		task.setCreatedate(new Date());
		task.setUpdatedate(null);
		task.setUpdator(null);
		entityManager.persist(task);
		return task;
	}

	public String get9Id(String code, String type) {

		SessionImplementor session = entityManager
				.unwrap(SessionImplementor.class);
		Connection conn = session.connection();
		String testPrint = null;
		try {
			conn.setAutoCommit(false);
			CallableStatement cstmt = conn
					.prepareCall("{ call proc_Gettaskseq(?,?) }");
			cstmt.setString(1, "0");
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.execute();
			conn.commit();
			testPrint = cstmt.getString(2);
			// conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		// 定义格式化的形式；
		String today = sf.format(new Date());
		// 将得到的当前日期按上面的格式转为String类型；
		String taskseq = code + today + testPrint;
		return taskseq;
	}

	public String DeleteByTid(String tid) {
		// TODO Auto-generated method stub
		String result = "";
		UploadTaskInfo uploadtaskinfo = new UploadTaskInfo();
		uploadtaskinfo = uploadtaskinforepository.findById(tid);
		if (uploadtaskinfo.getId() != null) {
			if (uploadtaskinfo.getTaskconfirm().equals("0")) {
				result = "数据已提交，不能删除";
			} else if (uploadtaskinfo.getTaskconfirm().equals("1")) {
				result = "数据已被确认，不能删除";
			} else {
				uploadtaskinfo.setActive("0");
				entityManager.merge(uploadtaskinfo);
				result = "操作成功!";
			}
		}
		return result;
	}

	public UploadTaskInfo UploadRemark(String id, String remark) {
		UploadTaskInfo uploadtaskinfo = new UploadTaskInfo();
		uploadtaskinfo = uploadtaskinforepository.findById(id);
		if (uploadtaskinfo != null) {
			uploadtaskinfo.setRemark(remark);
			entityManager.merge(uploadtaskinfo);
		}
		return uploadtaskinfo;
	}

	public void validateReseale(String tid) {
		SessionImplementor session = entityManager
				.unwrap(SessionImplementor.class);
		Connection conn = session.connection();
		String testPrint = null;
		try {
			conn.setAutoCommit(false);
			CallableStatement cstmt = conn
					.prepareCall("{ call proc_Upload_Resales_Valid_1(?) }");
			cstmt.setString(1, tid);
			cstmt.execute();
			conn.commit();
			testPrint = cstmt.getString(2);
			// conn.close();
		} catch (SQLException e) {
		}
	}
}
