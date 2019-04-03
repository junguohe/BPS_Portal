package com.erry.auth.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.component.CurrentUserInfo;
import com.erry.auth.model.UploadInventory;
import com.erry.auth.model.UploadInventoryAdjustment;
import com.erry.auth.model.UploadTaskInfo;
import com.erry.auth.repository.UploadInventoryAdjustmentRepository;
import com.erry.util.StringUtil;

@Service
@Transactional
public class UploadInventoryAdjustmentService {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private UploadInventoryAdjustmentRepository uploadinventoryadjustmentrepository;
	@Autowired
	private UploadTaskInfoServiceImpl uploadTaskInfoServiceImpl;
	public  Map<Integer,List<UploadInventoryAdjustment>> findlist(int page, int size,final String tid){
		Map<Integer,List<UploadInventoryAdjustment>> resultMap = new HashMap<Integer,List<UploadInventoryAdjustment>>();
		StringBuffer hql = new StringBuffer(" from UploadInventoryAdjustment ui where  ui.active = '1' ");
		if(!StringUtil.isEmpty(tid)){
			hql.append(" and ui.tid ='"+tid+"'");
		}
		//查询总数
		Query queryCount = entityManager.createQuery(" select count(ui.id) " + hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();
		//查询分页数据
		Query query = entityManager.createQuery(" select ui " + hql.toString()+"ORDER BY ui.errormsg desc", UploadInventoryAdjustment.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		//保存Map 总条数 分页后的集合
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}
	public void deleteSale(String id) {
		// TODO Auto-generated method stub
		UploadInventoryAdjustment u=new UploadInventoryAdjustment();
		u=uploadinventoryadjustmentrepository.findById(id);
		u.setActive("0");
		entityManager.merge(u);
	}
	public UploadInventoryAdjustment SaveOrCommit(UploadInventoryAdjustment uploadInventoryAdjustment, String value) {
		// TODO Auto-generated method stub
		UploadInventoryAdjustment u =new UploadInventoryAdjustment();
		u=uploadinventoryadjustmentrepository.findById(uploadInventoryAdjustment.getId());
		u.setTid(uploadInventoryAdjustment.getTid());
		u.setDealername(uploadInventoryAdjustment.getDealername());
		u.setPeriod(uploadInventoryAdjustment.getPeriod());
		u.setMaterialcode(uploadInventoryAdjustment.getMaterialcode());
		u.setMaterialname(uploadInventoryAdjustment.getMaterialname());
		u.setRefdealer(uploadInventoryAdjustment.getRefdealer());
		u.setAdjtype(uploadInventoryAdjustment.getAdjtype());
		u.setAdjdate(uploadInventoryAdjustment.getAdjdate());
		if(uploadInventoryAdjustment.getQty()!=null){
			String sjiachun = uploadInventoryAdjustment.getQty();
			BigDecimal db = new BigDecimal(sjiachun);
			String ii = db.toPlainString();
			
				u.setQty(ii);
		}
		u.setRemark(uploadInventoryAdjustment.getRemark());
		u.setErrormsg(uploadInventoryAdjustment.getErrormsg());
		u.setActive("1");
		u.setUpdator(CurrentUserInfo.getUid());
		u.setUpdatedate(new Date());
		entityManager.merge(u);
		UpdateTask(u.getTid(),value);
		return u;
	}
	public void UpdateTask(String tid,String value){
		String taskconfirm="1";
		UploadTaskInfo uploadTaskInfo=uploadTaskInfoServiceImpl.UpdateTaskInfo(tid,taskconfirm);
		
		
	}
		//导出
				public List<UploadInventoryAdjustment> UploadDownload(HttpServletRequest request,
						HttpServletResponse response, String tid,
						String filename
						) throws IOException {
					StringBuffer sql = new StringBuffer(
							"SELECT inventory  FROM UploadInventoryAdjustment inventory  WHERE inventory.active=1");
					if (!StringUtil.isEmpty(tid)&&tid!=null) {
						sql.append(" and inventory.tid = '"
								+ tid + "' ");
					}
					Query query = entityManager.createQuery(sql.toString()+"ORDER BY inventory.errormsg desc",UploadInventoryAdjustment.class);
					return query.getResultList();
				}
				

}
