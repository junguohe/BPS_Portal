package com.erry.auth.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.erry.auth.component.CurrentUserInfo;
import com.erry.auth.dto.CustomerBatchDTO;
import com.erry.auth.model.Contact;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustBPS;
import com.erry.auth.model.CustProdLine;
import com.erry.auth.model.CustProjectInfo;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.Customer;
import com.erry.auth.model.CustomerBatchProc;
import com.erry.auth.model.DealerInfo;
import com.erry.auth.repository.ContactRepository;
import com.erry.auth.repository.CustAddressRepository;
import com.erry.auth.repository.CustBPSRepository;
import com.erry.auth.repository.CustProdLineRepository;
import com.erry.auth.repository.CustProjectInfoRepository;
import com.erry.auth.repository.CustRegRepository;
import com.erry.auth.repository.CustomerBatchProcRepository;
import com.erry.auth.repository.CustomerRepository;
import com.erry.auth.repository.DealerInfoRepository;
import com.erry.common.BusinessException;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class CustomerBatchProcService {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CustomerBatchProcRepository customerBatchProcRepository;

	@Autowired
	private CustRegRepository custRegRepository;
	
	@Autowired
	private DealerInfoRepository dealerInfoRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustProdLineRepository custProdlineRepository;

	@Autowired
	private CustBPSRepository custBPSRepository;

	@Autowired
	private CustProjectInfoRepository custProjectInfoRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private CustAddressRepository custAddressRepository;
	
	@Autowired
	private MailService mailService;

	/**
	 * 解析上传数据
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public List<CustomerBatchProc> readFile(MultipartFile file)
			throws IOException, InvalidFormatException {
		String suffix = file.getOriginalFilename().substring(
				file.getOriginalFilename().lastIndexOf("."));
		InputStream is = file.getInputStream();
		List<CustomerBatchProc> list = new ArrayList<CustomerBatchProc>();
		Workbook wbs = WorkbookFactory.create(is);
		Sheet sheet = wbs.getSheetAt(0);
		if (sheet.getRow(0).getPhysicalNumberOfCells() != 5) {
			throw new BusinessException("请下载模板,上传正确格式的文件");
		}
		for (int j = 1; j <= sheet.getLastRowNum(); j++) {
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
								value = String.valueOf(cell
										.getStringCellValue());
							} catch (IllegalStateException e) {
								value = String.valueOf(cell
										.getNumericCellValue());
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

			if (!StringUtil.isEmpty(arr[0])) {

				String errmsg = "";

				if (StringUtil.isEmpty(arr[1])) {
					errmsg += "<br/>请将客户Code填写完整";
				}
				if (StringUtil.isEmpty(arr[2])) {
					errmsg += "<br/>请将产品线填写完整";
				}
				if (StringUtil.isEmpty(arr[3])) {
					errmsg += "<br/>请将操作类型填写完整";
				}
				if (arr[3].equals("1") && StringUtil.isEmpty(arr[4])) {
					errmsg += "<br/>操作类型为延期的需要填写延长期限";
				}

				if (errmsg != "") {
					throw new BusinessException("第" + j + "行<br/>" + errmsg);
				} else {
					CustomerBatchProc dto = new CustomerBatchProc();
					dto.setDealerName(arr[0]);
					dto.setCustomerCode(arr[1]);
					dto.setProdCode(arr[2]);
					dto.setType(arr[3]);
					if (arr[3].equals("1")) {
						try {
							Date date = new Date(arr[4]);
							dto.setRegDate(new Timestamp(date.getTime()));
						} catch (Exception e) {
							throw new BusinessException("时间格式错误！请上传正确的时间");
						}
					}
					list.add(dto);
				}
			}
		}
		return list;
	}

	/**
	 * 保存customer的批量操作数据 id 和 seq 不同视为 新的批量数据
	 * 
	 * @param customerBatchInfos
	 */
	public void saveCustomerBatchInfo(List<CustomerBatchProc> customerBatchInfos) {
		try {
			for (CustomerBatchProc cust : customerBatchInfos) {
				// 同一个seq 并且没有执行过操作的
				CustomerBatchProc model = customerBatchProcRepository.findOne(
						cust.getId(), cust.getSeq());
				if (model == null) {
					cust.setId(GuidIdUtil.getGuId());
					cust.setStatus("0");
					cust.setCreateDate(new Timestamp(new Date().getTime()));
					cust.setCreateUser(CurrentUserInfo.getUid());
					cust.setUpdateDate(new Timestamp(new Date().getTime()));
					cust.setUpdateUser(CurrentUserInfo.getUid());
					entityManager.merge(cust);
				} else {
					copyTempToModel(cust, model);
					entityManager.merge(model);
				}
			}
		} catch (Exception e) {
			throw new BusinessException("保存失败！请联系管理员");
		}
	}

	/**
	 * 读取对应的seq的数据
	 * 
	 * @param seq
	 * @return
	 */
	public List<CustomerBatchProc> readerCustomerBatchProc(String seq) {
		return customerBatchProcRepository.readerCustomerBatchProc(seq);
	}
	/**
	 * customer 操作
	 * @param seq
	 * @throws Exception
	 */
	public void procCustomerBatch(String seq) throws Exception {
		try {
			updateCustomerBatchByCpid(seq);
			// 待处理的数据 status 为0
			List<CustomerBatchProc> list = customerBatchProcRepository
					.readerCustomerBatchProc(seq);
			for (CustomerBatchProc cust : list) {
				if(cust.getCpid()!=null){
					DealerInfo dealerInfo = dealerInfoRepository.findDealerInfoByDealerName(cust.getDealerName());
					if(dealerInfo!=null){
						if (cust.getType().equals("1")) {
							// 延期
							customerReg(seq,cust.getCpid(), cust.getRegDate(),dealerInfo.getDealerid());
						} else {
							// open
							openCustomer(seq,cust.getCpid(),dealerInfo.getDealerid());
						}
						// 修改执行完的状态
						customerBatchLinstener(cust);
					}
				}
			}
			createCustoemrBatchMail(seq);
		} catch (BusinessException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * open customer 并记录 open掉的cpid 
	 * 
	 * @param cpid
	 */
	public void openCustomer(String seq,String cpid,String dealerId) throws BusinessException{
		try {
			Customer customer = customerRepository.findCustBycpid(cpid);
			if(customer!=null){
				customerCancel(seq,cpid,customer,dealerId);
				//customer 的母公司为空  查询所有母公司为该customer的customer  如果有  全部open
				if (customer.getParenetcompany() == null) {
					List<String> ccs = custProdlineRepository.findByCustid(customer.getId());
					for(String cc : ccs){
						Customer temp = customerRepository.findCustBycpid(cc);
						customerCancel(seq,cc,temp,dealerId);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("customerOpen 异常:"+cpid);
		}
	}

	/**
	 * 将customer相关信息open
	 * CustReg，CustProdLine，bps，projectInfo，contact，custAddress
	 * 
	 * @param cpid
	 */
	public void customerCancel(String seq,String cpid,Customer customer,String dealerId) throws BusinessException{
		try {
			if (customer.getIsparent() != null && customer.getIsparent().equals("0")) {
				customer.setIsparent("1");
				customer.setParenetcompany(null);
				customer.setUpdatedate(new Date());
				customer.setUpdator(CurrentUserInfo.getUid());
				entityManager.merge(customer);
			}
			// 取消 CustomerReg 信息
			CustReg custReg = custRegRepository.findcustReg(cpid,dealerId);
			if(custReg != null){
				custReg.setActive("0");
				if (custReg.getRegstatus().equals("1")) {
					custReg.setRegenddate(new Date());
					custReg.setUpdatedate(new Date());
					custReg.setUpdator(CurrentUserInfo.getUid());
				}
				entityManager.merge(custReg);
			}

			// CustomerProd 的状态置为未报备
			CustProdLine custProdLine = custProdlineRepository.findById(cpid);
			if(custProdLine!=null){
				custProdLine.setRegstatus("2");
				custProdLine.setRegstartdate(null);
				custProdLine.setUpdatedate(new Date());
				custProdLine.setUpdator(CurrentUserInfo.getUid());
				entityManager.merge(custProdLine);
			}

			// CustomerBPS信息
			List<CustBPS> bps = custBPSRepository.findByCpids(cpid);
			for(CustBPS b : bps){
				b.setActive("0");
				b.setUpdatedate(new Date());
				b.setUpdator(CurrentUserInfo.getUid());
				entityManager.merge(b);
			}
			// customerProject 信息
			List<CustProjectInfo> projectInfo = custProjectInfoRepository
					.findByCpids(cpid);
			for(CustProjectInfo p : projectInfo){
				p.setActive("0");
				p.setUpdatedate(new Date());
				p.setUpdator(CurrentUserInfo.getUid());
				entityManager.merge(p);
			}

			// Contact 信息
			List<Contact> contact = contactRepository.findByCpid(cpid);
			for(Contact con : contact){
				con.setActive("0");
				con.setUpdatedate(new Date());
				con.setUpdator(CurrentUserInfo.getUid());
				entityManager.merge(con);
			}
			// address 信息
			List<CustAddress> custAddress = custAddressRepository.findByCpid(cpid);
			for (CustAddress ca : custAddress) {
				ca.setActive("0");
				ca.setUpdatedate(new Date());
				ca.setUpdator(CurrentUserInfo.getUid());
				entityManager.merge(ca);
			}
			cusotmerBatchProcLog(seq,cpid, "0",dealerId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("open Customer 相关信息异常");
		}
	}

	/**
	 * 延期操作
	 * 
	 * @param cpid
	 * @param regDate
	 * @throws Exception
	 */
	public void customerReg(String seq,String cpid, Date regDate,String dealerId) throws Exception {
		try {
			CustReg custReg = custRegRepository.findcustReg(cpid,dealerId);
			custReg.setRegstartdate(regDate);
			entityManager.merge(custReg);

			CustProdLine custProdLine = custProdlineRepository.findById(cpid);
			custProdLine.setRegstartdate(regDate);
			entityManager.merge(custProdLine);
			
			cusotmerBatchProcLog(seq,cpid, "1",dealerId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("延期失败" + cpid);
		}
	}

	/**
	 * 修改执行过操作的数据状态
	 * 
	 * @param cust
	 */
	public void customerBatchLinstener(CustomerBatchProc cust) throws BusinessException{
		try {
			cust.setUpdateDate(new Timestamp(new Date().getTime()));
			cust.setUpdateUser(CurrentUserInfo.getUid());
			cust.setStatus("1");
			entityManager.merge(cust);
		} catch (Exception e) {
			throw new BusinessException("修改批量数据执行状态异常");
		}
	}
	
	/**
	 * 批量修改seq 的cpid
	 * 
	 * @param seq
	 * @return
	 */
	public void updateCustomerBatchByCpid(String seq) {
		try {
			StringBuffer sql = new StringBuffer(
					"UPDATE dbo.T_BPS_Customer_BatchProc SET cpid= a.CPID ");
			sql.append("FROM dbo.T_BPS_Customer_ProdLine a ");
			sql.append("LEFT JOIN dbo.T_BPS_Cust_RegInfo b ON b.CPID = a.CPID AND b.Active=1 ");
			sql.append("LEFT JOIN dbo.T_BPS_Customer_Info c ON c.CustID = a.CustID AND c.Active=1 ");
			sql.append("LEFT JOIN dbo.T_BPS_ProductLine d ON d.ProdID = a.ProdID AND d.Active=1 ");
			sql.append("LEFT JOIN dbo.T_BPS_DealerInfo e ON e.DealerID = b.DealerID AND e.Active=1 ");
			sql.append("WHERE a.Active=1 AND c.CustCode =CustomerCode AND (d.ProdCode=T_BPS_Customer_BatchProc.ProdCode or d.ProdName=T_BPS_Customer_BatchProc.ProdCode) AND e.DealerName=T_BPS_Customer_BatchProc.DealerName ");
			sql.append("AND Seq=?1 ");
			Query query = entityManager.createNativeQuery(sql.toString());
			query.setParameter(1, seq);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void copyTempToModel(CustomerBatchProc temp, CustomerBatchProc model) {
		model.setCustomerCode(temp.getCustomerCode());
		model.setDealerName(temp.getDealerName());
		model.setProdCode(temp.getProdCode());
		if (temp.getRegDate() != null)
			model.setRegDate(temp.getRegDate());
		model.setType(temp.getType());
	}
	
	public void cusotmerBatchProcLog(String seq,String cpid,String type,String dealerId){
		try {
			StringBuffer sql = new StringBuffer(
					"INSERT INTO T_BPS_Customer_BatchProc_log VALUES(NEWID(),?1,?2,?3,?4,GETDATE(),?5) ");
			Query query = entityManager.createNativeQuery(sql.toString());
			query.setParameter(1, seq);
			query.setParameter(2, cpid);
			query.setParameter(3, type);
			query.setParameter(4, dealerId);
			query.setParameter(5, CurrentUserInfo.getUid());
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createCustoemrBatchMail(String seq)  throws BusinessException{
		try {
			String sql = "SELECT c.UserName,c.EMail,c.DealerID FROM dbo.T_BPS_Customer_BatchProc_Log a "
					+ "LEFT JOIN dbo.T_BPS_User_Dealer c ON c.DealerID = a.DealerID AND c.Active=1 "
					+ "WHERE a.seq='"+seq+"' "
					+ "GROUP BY c.UserName,c.EMail,c.DealerID ";
			Query query = entityManager.createNativeQuery(sql);
			List<Object[]> arrs = query.getResultList();
			for(Object[] arr : arrs){
				if(!StringUtil.isEmpty(String.valueOf(arr[0]))&&!StringUtil.isEmpty(String.valueOf(arr[1]))&&!StringUtil.isEmpty(String.valueOf(arr[2]))){
					String customerBatchSql = "SELECT c.CustName,c.Region,d.ProdName,e.RegDate,CASE WHEN a.type='1' THEN '延期' WHEN a.type='0' THEN 'Open' END AS type FROM T_BPS_Customer_BatchProc_Log a "
							+ "LEFT JOIN dbo.T_BPS_Customer_ProdLine b ON b.CPID=a.cpid "
							+ "LEFT JOIN dbo.T_BPS_Customer_Info c ON c.CustID = b.CustID AND c.Active=1 "
							+ "LEFT JOIN dbo.T_BPS_ProductLine d ON d.ProdID = b.ProdID AND d.Active=1 "
							+ "LEFT JOIN dbo.T_BPS_Customer_BatchProc e ON e.cpid=a.cpid AND e.Seq=a.seq AND e.Type='1' "
							+ "WHERE a.seq='"+seq+"' AND a.dealerid='"+String.valueOf(arr[2])+"' ";
					query = entityManager.createNativeQuery(customerBatchSql);
					arrs = query.getResultList();
					List<CustomerBatchDTO> dtos = new ArrayList<CustomerBatchDTO>();
					for(Object[] arr1 : arrs){
						CustomerBatchDTO custBatch = new CustomerBatchDTO();
						custBatch.setCustName(String.valueOf(arr1[0]));
						custBatch.setRegion(String.valueOf(arr1[1]));
						custBatch.setProdName(String.valueOf(arr1[2]));
						custBatch.setRegDate(String.valueOf(arr1[3]));
						custBatch.setStatus(String.valueOf(arr1[4]));
						dtos.add(custBatch);
					}
					mailService.createCustomerBatchOpenMail(String.valueOf(arr[0]), String.valueOf(arr[1]), dtos);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("写入邮件异常");
		}
	}
}
