package com.erry.auth.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.erry.auth.component.CurrentUserInfo;
import com.erry.auth.dto.MaterialInfoDTO;
import com.erry.auth.model.DealerInfo;
import com.erry.auth.model.DealerUploadReSale;
import com.erry.auth.model.Dictionary;
import com.erry.auth.model.MaterialInfo;
import com.erry.auth.model.UploadTaskInfo;
import com.erry.auth.model.UserDealer;
import com.erry.auth.repository.DealerInfoRepository;
import com.erry.auth.repository.UploadReSaleRepository;
import com.erry.auth.repository.UploadTaskInfoRepository;
import com.erry.auth.repository.UserDealerRepostory;
import com.erry.common.BusinessException;
import com.erry.util.DateUtil;
import com.erry.util.GuidIdUtil;
import com.erry.util.NumberUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class UploadReSaleService {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private UploadReSaleRepository uploadresalerepository;

	@Autowired
	private UploadTaskInfoRepository uploadtaskinforepository;
	
	@Autowired
	private UploadTaskInfoServiceImpl uploadTaskInfoServiceImpl;
	@Autowired 
	private DealerInfoRepository dealerinforepository;
	@Autowired 
	private MaterialInfoServiceImpl materialinfoserviceimpl;
	
	public  Map<Integer,List<DealerUploadReSale>> findDealerUploadReSale(int page, int size,final String tid, String period, String taskseq){
		Map<Integer,List<DealerUploadReSale>> resultMap = new HashMap<Integer,List<DealerUploadReSale>>();
		StringBuffer hql = new StringBuffer(" from DealerUploadReSale ui where  ui.active = '1' ");
		if(!StringUtil.isEmpty(tid)){
			hql.append(" and ui.tid ='"+tid+"'");
		}
		if(!StringUtil.isEmpty(period)){
			hql.append(" and ui.period ='"+period+"'");
		}
		if(!StringUtil.isEmpty(taskseq)){
			hql.append(" and ui.tid in (select task.id from UploadTaskInfo task where task.id='"+taskseq+"' and  task.taskconfirm='9')");
		}
		//查询总数
		Query queryCount = entityManager.createQuery(" select count(ui.id) " + hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();
		//查询分页数据
		Query query = entityManager.createQuery(" select ui " + hql.toString()+"ORDER BY ui.errormsg desc", DealerUploadReSale.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		//保存Map 总条数 分页后的集合
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}
	public  Map<Integer,List<DealerUploadReSale>> readerUploadResales(int page, int size,final String tid, String period, String taskseq){
		Map<Integer,List<DealerUploadReSale>> resultMap = new HashMap<Integer,List<DealerUploadReSale>>();
		StringBuffer hql = new StringBuffer(" from DealerUploadReSale ui where  ui.active = '1' and ui.tid in (select task.id from UploadTaskInfo task where  task.taskconfirm='9' and task.taskowner='0') ");
		if(!StringUtil.isEmpty(tid)){
			hql.append(" and ui.tid ='"+tid+"'");
		}
		if(!StringUtil.isEmpty(period)){
			hql.append(" and ui.period ='"+period+"'");
		}
		if(!StringUtil.isEmpty(taskseq)){
			hql.append(" and ui.tid in (select task.id from UploadTaskInfo task where task.id='"+taskseq+"' )");
		}
			//查询总数
		Query queryCount = entityManager.createQuery(" select count(ui.id) " + hql.toString(), Long.class);
		Long totalCount = (Long) queryCount.getSingleResult();
		//查询分页数据
		Query query = entityManager.createQuery(" select ui " + hql.toString()+"ORDER BY ui.errormsg desc", DealerUploadReSale.class);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		//保存Map 总条数 分页后的集合
		resultMap.put(totalCount.intValue(), query.getResultList());
		return resultMap;
	}
	public void UpdateTask(String tid,String value){
		String taskconfirm="1";
		UploadTaskInfo uploadTaskInfo=uploadTaskInfoServiceImpl.UpdateTaskInfo(tid,taskconfirm);
		
		
	}
	public void UpdateTasks(String tid,String value){
		String taskconfirm="";
		if(value.equals("验证")){
			taskconfirm="9";
		}else{
			taskconfirm="0";
		}
		UploadTaskInfo uploadTaskInfo=uploadTaskInfoServiceImpl.UpdateTaskInfo(tid,taskconfirm);
		
		
	}
	
	public DealerUploadReSale SaveOrCommit(DealerUploadReSale inventory,String value){
		   DealerUploadReSale u=new DealerUploadReSale();
			u=uploadresalerepository.findById(inventory.getId());
			u.setTid(inventory.getTid());
			u.setFileid(null);
			u.setRegion(inventory.getRegion());
			u.setErrormsg(inventory.getErrormsg());
			u.setDealername(inventory.getDealername());
			u.setPeriod(inventory.getPeriod());
			u.setSeqno(inventory.getSeqno());
			u.setCustname(inventory.getCustname());
			u.setMaterialname(inventory.getMaterialname());
			u.setMaterialcode(inventory.getMaterialcode());
			u.setBatchno(inventory.getBatchno());
			u.setQty(inventory.getQty());
			u.setPaymenttype(inventory.getPaymenttype());
			u.setDeliverydate(inventory.getDeliverydate());
			if(inventory.getUnitprice()!=null){
				u.setUnitprice(inventory.getUnitprice());
			}
			
			if(inventory.getContractamount()!=null){
				u.setContractamount(inventory.getContractamount());
			}
			u.setIstax(inventory.getIstax());
			u.setIsspl(inventory.getIsspl());
			u.setCurrency(inventory.getCurrency());

			if(inventory.getDealerstdcost()!=null){
				u.setDealerstdcost(NumberUtil.setScale(inventory.getDealerstdcost(),4));
			}
			if(inventory.getDealersplcost()!=null){
				u.setDealersplcost(NumberUtil.setScale(inventory.getDealersplcost(),4));
			}
			if(inventory.getDealerstdpo()!=null){
				u.setDealerstdpo(NumberUtil.setScale(inventory.getDealerstdpo(),4));
			}
			if(inventory.getDealerstdact()!=null){
				u.setDealerstdact(NumberUtil.setScale(inventory.getDealerstdact(),4));
			}
				
			if(inventory.getBpsstdcost()!=null){
				u.setBpsstdcost(NumberUtil.setScale(inventory.getBpsstdcost(),4));
			}
			if(inventory.getBpssplcost()!=null){
				u.setBpssplcost(NumberUtil.setScale(inventory.getBpssplcost(),4));
			}
			if(inventory.getRebateamount()!=null){
				u.setRebateamount(inventory.getRebateamount());
			}
			if(inventory.getRebatediff()!=null){
				u.setRebatediff(inventory.getRebatediff());
			}
			u.setRemark(inventory.getRemark());
			u.setActive("1");
			u.setUpdator(CurrentUserInfo.getUid());
			u.setUpdatedate(new Date());
			entityManager.merge(u);
			UpdateTask(u.getTid(),value);
			return u;
	}
	
	public void deleteSale(String id) {
		// TODO Auto-generated method stub
		DealerUploadReSale u=new DealerUploadReSale();
		u=uploadresalerepository.findById(id);
		u.setTid(u.getTid());
		u.setFileid(null);
		u.setRegion(u.getRegion());
		u.setDealername(u.getDealername());
		u.setPeriod(u.getPeriod());
		u.setSeqno(u.getSeqno());
		u.setCustname(u.getCustname());
		u.setMaterialname(u.getMaterialname());
		u.setMaterialcode(u.getMaterialcode());
		u.setBatchno(u.getBatchno());
		u.setQty(u.getQty());
		u.setPaymenttype(u.getPaymenttype());
		u.setDeliverydate(u.getDeliverydate());
		if(u.getUnitprice()!=null){
			u.setUnitprice(u.getUnitprice());
		}
		if(u.getContractamount()!=null){
			u.setContractamount(u.getContractamount());
		}
		u.setIstax(u.getIstax());
		u.setIsspl(u.getIsspl());
		u.setCurrency(u.getCurrency());
		if(u.getDealerstdcost()!=null){
			u.setDealerstdcost(NumberUtil.setScale(u.getDealerstdcost(),4));
		}
		if(u.getDealersplcost()!=null){
			u.setDealersplcost(NumberUtil.setScale(u.getDealersplcost(),4));
		}
		if(u.getDealerstdpo()!=null){
			u.setDealerstdpo(NumberUtil.setScale(u.getDealerstdpo(),4));
		}
		if(u.getDealerstdact()!=null){
			u.setDealerstdact(NumberUtil.setScale(u.getDealerstdact(),4));
		}
		if(u.getBpsstdcost()!=null){
		}
		if(u.getBpssplcost()!=null){
			u.setBpssplcost(NumberUtil.setScale(u.getBpssplcost(),4));
		}

		if(u.getRebateamount()!=null){
			u.setRebateamount(u.getRebateamount());
		}
		if(u.getRebatediff()!=null){
			u.setRebatediff(u.getRebatediff());
		}
		
		u.setRemark(u.getRemark());
		u.setActive("0");
		u.setUpdator(CurrentUserInfo.getUid());
		u.setUpdatedate(new Date());
		entityManager.merge(u);
	}

	public List<DealerUploadReSale> UploadDownload(HttpServletRequest request,
						HttpServletResponse response, String tid,
						String filename
						) throws IOException {
		
					request.setCharacterEncoding("utf-8");

					String period =  request.getParameter("period");
//					String dealername =  request.getParameter("dealername");
					String dealername =new String(request.getParameter("dealername").getBytes("iso8859-1"),"utf-8");

					String taskconfirm =  request.getParameter("taskconfirm");
					String remark =  request.getParameter("remark");
					
					StringBuffer sql = new StringBuffer(
							"SELECT inventory  FROM DealerUploadReSale inventory  ");
/*					String[] tidArray=tid.split(",");
					List tidList=new ArrayList();
					for(String tidString : tidArray){
						tidList.add(tidString);
					}
					if (!StringUtil.isEmpty(tid) && tid!=null) {
						sql.append(" and inventory.tid in (:tids)");
					}
*/					
					if(!StringUtil.isEmpty(period) || !StringUtil.isEmpty(dealername) || !StringUtil.isEmpty(remark) || !StringUtil.isEmpty(taskconfirm)){
						sql.append(", UploadTaskInfo t where t.id=inventory.tid and t.active= '1' and inventory.active=1");
					}else{
						sql.append(" WHERE inventory.active=1");
					}
					
					if (!StringUtil.isEmpty(period)) {
						sql.append(" and t.period like '%" + period + "%' ");
					}
					if (!StringUtil.isEmpty(dealername)) {
						sql.append(" and t.creator in(select dealer.dealerid from DealerInfo dealer where dealer.dealername like '%"
								+ dealername + "%' )  ");
					}
					if (!StringUtil.isEmpty(remark)) {
						sql.append("and t.remark like '%" + remark + "%' ");
					}

					if (!StringUtil.isEmpty(taskconfirm)) {
						sql.append(" and t.taskconfirm like '%" + taskconfirm + "%' ");
					}
					
					Query query = entityManager.createQuery(sql.toString()+"ORDER BY inventory.dealername desc, inventory.seqno",DealerUploadReSale.class);
//					query.setParameter("tids", tidList);
					return query.getResultList();
				}

		// 下载模板
	public void importFileDownload(HttpServletRequest request,
			HttpServletResponse response, String fname)
			throws Exception {
		String path = request.getSession().getServletContext().getRealPath("/download");
		InputStream in = new FileInputStream(path + "/UploadReSale.xls");//模板
		HSSFWorkbook wb = new HSSFWorkbook(in);
			String file = new String(fname.getBytes("gb2312"), "ISO-8859-1")
				+ ".xls";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename=" + file
				+ " ");
		OutputStream out = response.getOutputStream();
		wb.write(out);
		out.close();
	}
//解析
	public List<DealerUploadReSale> readFile(MultipartFile file,String period)throws IOException, InvalidFormatException {
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")); 
		InputStream is = file.getInputStream();
		List<DealerUploadReSale> list = new ArrayList<DealerUploadReSale>();
		Workbook wbs = WorkbookFactory.create(is);
		Sheet sheet = wbs.getSheetAt(0);
		if (sheet.getRow(0).getPhysicalNumberOfCells()!=15){
			throw new BusinessException("请下载模板,上传正确格式的文件");
		}
		DealerInfo model =new DealerInfo();
		
		for (int j = 1; j <= sheet.getLastRowNum(); j++) {
			DealerUploadReSale dto = new DealerUploadReSale();
			Row row = sheet.getRow(j);
			String[] arr = new String[15];
			if (null != row) {
				int k = 0;
				for (k = 0; k < row.getLastCellNum(); k++) {
					String value = null;
					Cell cell = row.getCell(k);
					if (null != cell) {
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING:// 字符串
							value = cell.getRichStringCellValue().getString();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:// 数字
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
									double d = cell.getNumericCellValue();
									Date date = HSSFDateUtil.getJavaDate(d);
									SimpleDateFormat dformat = new SimpleDateFormat(
											"yyyy/MM/dd HH:mm:ss");
									value = dformat.format(date);
								} else {
									NumberFormat nf = NumberFormat.getInstance();
									nf.setMaximumFractionDigits(9);
									nf.setGroupingUsed(false);// true时的格式：1,234,567,890
									value = nf.format(cell.getNumericCellValue());
								}
							
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							value = "";
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							try {  
								value = String.valueOf(cell.getStringCellValue());  
							} catch (IllegalStateException e) {  
								value = String.valueOf(cell.getNumericCellValue());  
							}  
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN:// boolean型值
							value = String.valueOf(cell.getBooleanCellValue());
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							value = String.valueOf(cell.getErrorCellValue());
							break;
						default:
							break;
						}
					}

					if (value != null)
						value = value.trim();
					arr[k] = value;
				}
			}
			
			if(!StringUtil.isEmpty(arr[0])){

				String errmsg="";
				
				if (StringUtil.isEmpty(arr[0])) {
					errmsg+="<br/>请将经销商名称填写完整";
				}else{
					model=dealerinforepository.findDealerInfoByDealerName(arr[0]);
					if(model==null){
						errmsg+="经销商不存在";
					}
				}
				
				if (StringUtil.isEmpty(arr[8])) {
					errmsg+="请将交货日期填写完整";
				}
				String tempDate =arr[8];
				String date = "";
				try {
					date=tempDate.substring(0, tempDate.lastIndexOf("/")).replace("/", "-");
				} catch (Exception e) {
					errmsg+="<br/>交货日期格式为yyyy/MM/dd";
				}
			
				if (!period.equals(date)) {
					errmsg+="<br/>请上传本会计周期的数据";
				}
				
				

				if (StringUtil.isEmpty(arr[1])) {
					errmsg+="<br/>请将序列号填写完整";
				}
				
				
				if(model!=null){
					String tempPeriod = period.replace("-", "");
					if(arr[1].indexOf(tempPeriod)!=-1){
						String tempEname = arr[1].substring(0, arr[1].indexOf(tempPeriod));
						if(!model.getEname().equals(tempEname)||arr[1].indexOf(tempPeriod)==-1){
							errmsg+="<br/>序列号格式应为大写的经销商拼音首字母缩写"+model.getEname()+"+会计周期"+tempPeriod+"+流水号"+"XXX";
						}
					}else{
						errmsg+="<br/>序列号格式应为大写的经销商拼音首字母缩写"+model.getEname()+"+会计周期"+tempPeriod+"+流水号"+"XXX";
					}
					
				}
				
				if (StringUtil.isEmpty(arr[2]) ){
					errmsg+="<br/>请将订货单位填写完整";
				}
				if (StringUtil.isEmpty(arr[3])) {
					errmsg+="<br/>请将产品型号填写完整";
				}
				if ( StringUtil.isEmpty(arr[4])) {
					errmsg+="<br/>请将出货数量填写完整";
				}
				if (StringUtil.isEmpty(arr[6])) {
					errmsg+="<br/>请将付款方式填写完整";
				}
				
				
				if (StringUtil.isEmpty(arr[9])) {
					errmsg+="<br/>请将单价填写完整";
				}
				if ( StringUtil.isEmpty(arr[9])) {
					errmsg+="<br/>请将单价填写完整";
				}
				if(!arr[10].equals("是")&&!arr[10].equals("否")){
					errmsg+="<br/>含税否 请上传'是'或者'否'的数据格式";
				}
				if (StringUtil.isEmpty(arr[11])) {
					errmsg+="<br/>请将是否特价填写完整";
				}
				if(!arr[11].equals("是")&&!arr[11].equals("否")){
					errmsg+="<br/>是否特价错误";
				}
				
				if (StringUtil.isEmpty(arr[12])) {
					errmsg+="<br/>请将经销商标准成本价(未税)填写完整";
				}
				if(arr[11].equals("是")){
					if (StringUtil.isEmpty(arr[13])) {
						errmsg+="<br/>请将特价的经销商特价成本价(未税)填写完整";
					}
				}
				if(errmsg!=""){
					throw new BusinessException("第"+j+"行<br/>"+errmsg);
				}else{
					dto.setDealername(arr[0]);
					dto.setSeqno(arr[1]);
					dto.setCustname(arr[2]);
					dto.setMaterialname(arr[3]);
					dto.setQty(Integer.valueOf(arr[4]));
					dto.setBatchno(arr[5]);
					dto.setPaymenttype(arr[6]);
					dto.setCurrency(arr[7]);
					try {
						dto.setDeliverydate(new Date(arr[8]));
					} catch (Exception e) {
						throw new BusinessException("时间格式错误！请上传正确的时间");
					}
					if(arr[8].equalsIgnoreCase("null")){
						dto.setUnitprice(null);
					}else{
						dto.setUnitprice(new BigDecimal(arr[9]));
					}
					// 含税
					dto.setIstax(arr[10]);
					// 特价
					dto.setIsspl(arr[11]);
					
					
					if(arr[12].equals("null")){
						dto.setDealerstdcost(null);
					}else{
						dto.setDealerstdcost(new BigDecimal(arr[12]));
					}
					if(arr[13]==null||arr[13]==""){
						dto.setDealersplcost(null);
					}else{
						dto.setDealersplcost(new BigDecimal(arr[13]));
					}
					dto.setRemark(arr[14]);
					list.add(dto);
				}
			}
		}
			
		return list;
	}
//保存
	public String SaveOrUpdate(List<DealerUploadReSale> list, String period) {
		UploadTaskInfo uploadTaskInfo=uploadTaskInfoServiceImpl.saveUploadTaskInfo(period,"resale","resale");
		SaveOrUpdates(list,period,uploadTaskInfo.getId());
		return uploadTaskInfo.getId();
	}
	public void SaveOrUpdates(List<DealerUploadReSale> list,String period,String tid){
		for(DealerUploadReSale u : list){
			DealerUploadReSale dealeruploadresale=new DealerUploadReSale();
			dealeruploadresale.setId(GuidIdUtil.getGuId());
			dealeruploadresale.setTid(tid);
			dealeruploadresale.setPeriod(period);
			dealeruploadresale.setCurrency(u.getCurrency());
			dealeruploadresale.setFileid(null);
			dealeruploadresale.setSeqno(u.getSeqno());
			dealeruploadresale.setCustname(u.getCustname());
			dealeruploadresale.setDealername(u.getDealername());
			dealeruploadresale.setMaterialname(u.getMaterialname());
			dealeruploadresale.setMaterialcode(GetMaterialCode(u.getMaterialname()));
			Integer qty = 0;
			if(u.getQty()<0){
				qty=u.getQty();
			}else{
				qty=u.getQty();
			}
			if(u.getBatchno()!=null){
				dealeruploadresale.setBatchno(u.getBatchno());
			}else{
				dealeruploadresale.setBatchno(null);
			}
			
			dealeruploadresale.setPaymenttype(u.getPaymenttype());
			
			dealeruploadresale.setDeliverydate(DateUtil.roundDate(u.getDeliverydate()));
			dealeruploadresale.setUnitprice(NumberUtil.setScale(u.getUnitprice(),3));
			String isspl="";
			if(u.getIsspl().equals("是")){
				isspl="1";
			}else{
				isspl="0";
			}
			dealeruploadresale.setIsspl(isspl);
			String istax="";
			if(u.getIstax().equals("是")){
				istax="1";
			}else{
				istax="0";
			}
			dealeruploadresale.setIstax(istax);
			dealeruploadresale.setDealerstdcost(NumberUtil.setScale(u.getDealerstdcost(),4));
			if(u.getDealersplcost()==null){
				dealeruploadresale.setDealersplcost(null);
			}else{
				dealeruploadresale.setDealersplcost(NumberUtil.setScale(u.getDealersplcost(),4));
			}
			if(u.getRemark()!=null){
				dealeruploadresale.setRemark(u.getRemark());
			}else{
				dealeruploadresale.setRemark(null);
			}
			
			dealeruploadresale.setActive("1");
			dealeruploadresale.setErrormsg("");
			dealeruploadresale.setCreator(CurrentUserInfo.getUid());
			dealeruploadresale.setCreatedate(new Date());
			entityManager.persist(dealeruploadresale);
			//调用存储过程
		}
	}
		public String GetMaterialCode(String materialname){
			List<MaterialInfoDTO> list = new ArrayList<MaterialInfoDTO>();
			list=materialinfoserviceimpl.findAlls(materialname);
			String materialcode=null;
			if(list.size()>0){
				if(list.size()==1){
					materialcode=list.get(0).getMaterialcode();
				}else{
					materialcode="手动选择";
				}
				 
			}
			return materialcode;
		}

	public void get9Id(String tid) {

		SessionImplementor session = entityManager.unwrap(SessionImplementor.class);
		Connection conn = session.connection();
		String testPrint = null;
		try {
			conn.setAutoCommit(false);
			CallableStatement cstmt = conn.prepareCall("{ call proc_Upload_Resales_Valid(?) }");
			cstmt.setString(1, tid);
			cstmt.execute();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

     }

	public DealerUploadReSale SaveOrCommits(DealerUploadReSale inventory,String value){
		DealerUploadReSale u=new DealerUploadReSale();
			u=uploadresalerepository.findById(inventory.getId());
			u.setTid(inventory.getTid());
			u.setFileid(null);
			u.setRegion(inventory.getRegion());
			u.setDealername(inventory.getDealername());
			u.setPeriod(inventory.getPeriod());
			u.setSeqno(inventory.getSeqno());
			u.setCustname(inventory.getCustname());
			u.setCurrency(inventory.getCurrency());
			u.setMaterialname(inventory.getMaterialname());
			u.setMaterialcode(inventory.getMaterialcode());
			u.setBatchno(inventory.getBatchno());
			if(inventory.getQty()<0){
				u.setQty(inventory.getQty());
			}else{
				u.setQty(inventory.getQty());
			}
			u.setPaymenttype(inventory.getPaymenttype());
			u.setDeliverydate(inventory.getDeliverydate());

			if(inventory.getUnitprice()!=null){
				u.setUnitprice(inventory.getUnitprice());
			}
			if(inventory.getContractamount()!=null){
				u.setContractamount(inventory.getContractamount());
			}
			u.setIstax(inventory.getIstax());
			u.setIsspl(inventory.getIsspl());
			if(inventory.getDealerstdcost()!=null){
				u.setDealerstdcost(NumberUtil.setScale(inventory.getDealerstdcost(),4));
			}
			if(inventory.getDealersplcost()!=null){
				u.setDealersplcost(NumberUtil.setScale(inventory.getDealersplcost(),4));
			}
			if(inventory.getDealerstdpo()!=null){
				u.setDealerstdpo(NumberUtil.setScale(inventory.getDealerstdpo(),4));
			}
			if(inventory.getDealerstdact()!=null){
				u.setDealerstdact(NumberUtil.setScale(inventory.getDealerstdact(),4));
			}
			if(inventory.getBpsstdcost()!=null){
				u.setBpsstdcost(NumberUtil.setScale(inventory.getBpsstdcost(),4));
			}
			if(inventory.getBpssplcost()!=null){
				u.setBpssplcost(NumberUtil.setScale(inventory.getBpssplcost(),4));
			}

			if(inventory.getRebateamount()!=null){
				u.setRebateamount(inventory.getRebateamount());
			}
			if(inventory.getRebatediff()!=null){
				u.setRebatediff(inventory.getRebatediff());
			}
			u.setRemark(inventory.getRemark());
			u.setActive("1");
			u.setUpdator(CurrentUserInfo.getUid());
			u.setUpdatedate(new Date());
			entityManager.merge(u);
			UpdateTasks(u.getTid(),value);
			return u;
	}
		public String  DeleteInfo(String tid) {
					// TODO Auto-generated method stub
					UploadTaskInfo uploadtaskinfo=uploadtaskinforepository.findById(tid);
					String result=uploadTaskInfoServiceImpl.DeleteByTid(tid);
					if(result.equals("操作成功!")){
						List<DealerUploadReSale> list=new ArrayList<DealerUploadReSale>();
						if(uploadtaskinfo.getId()!=null){
							list=uploadresalerepository.fingByTid(uploadtaskinfo.getId());
							DealerUploadReSale u=new DealerUploadReSale();
							if(list.size()>0){
								for(int i = 0; i < list.size(); i++){
									u=deleteSales(list.get(i).getId());
								}
							}
						}
					}
					return result;
				}
		//删除resale数据
					public DealerUploadReSale deleteSales(String id) {
					// TODO Auto-generated method stub
					DealerUploadReSale u=new DealerUploadReSale();
					u=uploadresalerepository.findById(id);
					u.setTid(u.getTid());
					u.setFileid(null);
					u.setRegion(u.getRegion());
					u.setDealername(u.getDealername());
					u.setPeriod(u.getPeriod());
					u.setSeqno(u.getSeqno());
					u.setCustname(u.getCustname());
					u.setMaterialname(u.getMaterialname());
					u.setMaterialcode(u.getMaterialcode());
					u.setBatchno(u.getBatchno());
					u.setQty(u.getQty());
					u.setPaymenttype(u.getPaymenttype());
					u.setDeliverydate(u.getDeliverydate());
					
					if(u.getUnitprice()!=null){
						u.setUnitprice(u.getUnitprice());
					}
					if(u.getContractamount()!=null){
						u.setContractamount(u.getContractamount());
					}
					u.setIstax(u.getIstax());
					u.setIsspl(u.getIsspl());
					u.setCurrency(u.getCurrency());
					if(u.getDealerstdcost()!=null){
						u.setDealerstdcost(NumberUtil.setScale(u.getDealerstdcost(),4));
					}
					if(u.getDealersplcost()!=null){
						u.setDealersplcost(NumberUtil.setScale(u.getDealersplcost(),4));
					}
					if(u.getDealerstdpo()!=null){
						u.setDealerstdpo(NumberUtil.setScale(u.getDealerstdpo(),4));
					}
					if(u.getDealerstdact()!=null){
						u.setDealerstdact(NumberUtil.setScale(u.getDealerstdact(),4));
					}
					if(u.getBpsstdcost()!=null){
						u.setBpsstdcost(NumberUtil.setScale(u.getBpsstdcost(),4));
					}
					if(u.getBpssplcost()!=null){
						u.setBpssplcost(NumberUtil.setScale(u.getBpssplcost(),4));
					}
					if(u.getRebateamount()!=null){
						u.setRebateamount(u.getRebateamount());
					}
					if(u.getRebatediff()!=null){
						u.setRebatediff(u.getRebatediff());
					}
					u.setRemark(u.getRemark());
					u.setActive("0");
					u.setUpdator(CurrentUserInfo.getUid());
					u.setUpdatedate(new Date());
					entityManager.merge(u);
					return u;
				}
}
