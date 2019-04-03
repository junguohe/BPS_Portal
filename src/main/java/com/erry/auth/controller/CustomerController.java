package com.erry.auth.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.erry.auth.dto.CustProdDTO;
import com.erry.auth.dto.CustomerDTO;
import com.erry.auth.model.Contact;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustBPS;
import com.erry.auth.model.CustProdLine;
import com.erry.auth.model.CustProjectInfo;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.Customer;
import com.erry.auth.model.CustomerBatchProc;
import com.erry.auth.model.Json;
import com.erry.auth.repository.CustProdLineRepository;
import com.erry.auth.repository.CustRegRepository;
import com.erry.auth.repository.CustomerRepository;
import com.erry.auth.service.ContactServiceImpl;
import com.erry.auth.service.CustAddressServiceImpl;
import com.erry.auth.service.CustBPSServiceImpl;
import com.erry.auth.service.CustProdLineServiceImpl;
import com.erry.auth.service.CustProjectInfoServiceImpl;
import com.erry.auth.service.CustRegServiceImpl;
import com.erry.auth.service.CustomerBatchProcService;
import com.erry.auth.service.CustomerServiceImpl;
import com.erry.common.BusinessException;

@Controller("customerControl")
public class CustomerController {

	@Autowired
	private CustomerServiceImpl customerService;

	@Autowired
	private CustProdLineServiceImpl custProdLineServiceImpl;
	@Autowired
	private CustProjectInfoServiceImpl custProjectInfoServiceImpl;
	@Autowired
	private CustProdLineRepository custProdLineRepository;

	@Autowired
	private ContactServiceImpl contactServiceImpl;
	@Autowired
	private CustAddressServiceImpl custAddressServiceImpl;

	@Autowired
	private CustRegServiceImpl custRegServiceImpl;
	@Autowired
	private CustBPSServiceImpl custBPSServiceImpl;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustRegRepository custRegRepository;
	
	@Autowired
	private CustomerBatchProcService customerBatchProcService;

	Logger log = Logger.getLogger(CustomerController.class);

	// 冲突查询
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<CustomerDTO> readcustDTOs(
			ExtDirectStoreReadRequest request,
			@RequestParam(value = "custname", required = false) String custname,
			@RequestParam(value = "taxno", required = false) String taxno) {
		// 获取分页后Map集合
		Map<Integer, List<Customer>> pageMap = customerService.findCustLists(
				request.getPage() - 1, request.getLimit(), custname, taxno);
		Integer total = null;
		List<Customer> list = new ArrayList<Customer>();
		for (Map.Entry<Integer, List<Customer>> entry : pageMap.entrySet()) {
			total = entry.getKey();
			list = entry.getValue();
		}

		List<CustomerDTO> custRegDTOList = customerService
				.converTocustomerDTOList(list);
		ExtDirectStoreReadResult<CustomerDTO> slDTO = new ExtDirectStoreReadResult<CustomerDTO>(
				total, custRegDTOList);
		return slDTO;
	}

	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<CustProdDTO> readcustDTO(
			ExtDirectStoreReadRequest request,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "prodid", required = false) String prodid,
			@RequestParam(value = "regstatus", required = false) String regstatus,
			@RequestParam(value = "region", required = false) String region,
			@RequestParam(value = "dealername", required = false) String dealername,
			@RequestParam(value = "isshare", required = false) String isshare,
			@RequestParam(value = "bpssales", required = false) String bpssales,
			@RequestParam(value = "bpsfae", required = false) String bpsfae,
			@RequestParam(value = "parenetcompany", required = false) String parenetcompany,
			@RequestParam(value = "telno", required = false) String telno,
			@RequestParam(value = "isparent", required = false) String isparent,
			@RequestParam(value = "bestlatertime", required = false) String bestlatertime,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "custtype", required = false) String custtype,
			@RequestParam(value = "taxno", required = false) String taxno) {
		// 获取分页后Map集合
		Map<Integer, List<CustProdLine>> pageMap = customerService
				.findCustList(request.getPage() - 1, request.getLimit(), name,
						prodid, regstatus, region, dealername, isshare,
						bpssales, bpsfae, parenetcompany, telno, isparent,
						bestlatertime, address, custtype, taxno);
		Integer total = null;
		List<CustProdLine> list = new ArrayList<CustProdLine>();
		for (Map.Entry<Integer, List<CustProdLine>> entry : pageMap.entrySet()) {
			total = entry.getKey();
			list = entry.getValue();
		}

		List<CustProdDTO> custRegDTOList = customerService
				.converTocustProdDTOList(list);
		ExtDirectStoreReadResult<CustProdDTO> slDTO = new ExtDirectStoreReadResult<CustProdDTO>(
				total, custRegDTOList);
		return slDTO;
	}

	// 修改
	// 1
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json createCustomers(
			ExtDirectStoreReadRequest request,
			@RequestParam(value = "records", required = false) Customer s,
			@RequestParam(value = "address", required = false) List<CustAddress> address,
			@RequestParam(value = "contact", required = false) List<Contact> contact,
			@RequestParam(value = "project", required = false) List<CustProjectInfo> project,
			@RequestParam(value = "custbps", required = false) List<CustBPS> custbps,
			@RequestParam(value = "prodid", required = false) String prodid

	)

	{
		String msg = null;
		Boolean result = false;
		Customer cust = null;
		List<Customer> texnoCount = new ArrayList<Customer>();
		if (s.getTaxno() != "" && s.getTaxno() != null) {
			texnoCount = customerRepository.findAllByTaxno(s.getTaxno(),
					s.getId());
		}
		if (texnoCount.size() > 0) {
			msg = "税号重复";
		} else {
			CustProdLine prodline = custProdLineRepository.selectProdLine(
					prodid, s.getId());
			if (prodline != null && prodline.getCpid() != null) {
				cust = customerService.saveCustomers(s, prodline.getCpid(),
						address, contact, project, custbps);
				if (cust != null && cust.getId() != null) {
					msg = "保存成功";
					result = true;
				} else {
					msg = "保存失败";
				}

			}

		}

		Json json = new Json();
		json.setData(s);
		json.setMsg(msg);
		json.setSuccess(result);
		return json;
	}

	// 创建
	// 1
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json createCustomer(
			ExtDirectStoreReadRequest request,
			@RequestParam(value = "records", required = false) Customer s,
			@RequestParam(value = "address", required = false) List<CustAddress> address,
			@RequestParam(value = "contact", required = false) List<Contact> contact,
			@RequestParam(value = "project", required = false) List<CustProjectInfo> project,
			@RequestParam(value = "custbps", required = false) List<CustBPS> custbps,
			@RequestParam(value = "prodid", required = false) String prodid

	)

	{
		String msg = null;
		Boolean result = false;
		Customer cust = null;
		// 判断是否存在相同名称的客户
		Customer custs = customerRepository.findByCustname(s.getCustname());
		if (custs != null && custs.getId() != null) {
			// 判断是否存在中间表
			CustProdLine prodline = custProdLineRepository.selectProdLine(
					prodid, custs.getId());
			if (prodline != null && prodline.getCpid() != null) {
				if (prodline.getRegstatus().equals("1")) {
					msg = "相同产品线的客户已被审批通过";
				} else {
					// 判断是否存在报备表
					CustReg reg = custRegRepository.finByBPSCpid(
							prodline.getCpid(), "1");
					if (reg != null && reg.getId() != null) {
						msg = "您已报备过相同客户的相同产品线";
					} else {
						CustReg reg2 = custRegRepository.finByBPSCpidBo(
								prodline.getCpid(), "1");
						if (reg2 != null) {
							reg = custRegServiceImpl.updateCustReg(reg2);
						} else {
							reg = custRegServiceImpl.creatCustReg(prodline
									.getCpid());
						}
						if (reg != null && reg.getId() != null) {
							customerService.creatDetail(prodline.getCpid(),
									address, contact, project, custbps);
							msg = "操作成功";
							result = true;
						}
					}
				}

			} else {
				prodline = custProdLineServiceImpl.saveCustProdLine(prodid,
						custs.getId());
				if (prodline != null && prodline.getCpid() != null) {
					CustReg reg = custRegServiceImpl.creatCustReg(prodline
							.getCpid());
					// 是否添加成功
					if (reg != null && reg.getId() != null) {
						// 添加详细信息
						customerService.creatDetail(prodline.getCpid(),
								address, contact, project, custbps);
						msg = "操作成功";
						result = true;
					}
				}
			}
		} else {
			if (!s.getTaxno().equals("") & s.getTaxno() != null) {
				cust = customerRepository.findByTaxno(s.getTaxno());
			}

			if (cust != null && cust.getId() != null) {
				msg = "税号重复";
			} else {
				cust = customerService.saveCustomer(s, prodid, address,
						contact, project, custbps);
				if (cust != null && cust.getId() != null) {
					msg = "操作成功";
					result = true;
				} else {
					msg = "操作失败";
				}

			}

		}

		Json json = new Json();
		json.setData(cust);
		json.setMsg(msg);
		json.setSuccess(result);
		return json;

	}

	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json deleteCustomer(ExtDirectStoreReadRequest request,
			@RequestParam(value = "id", required = false) String id) {

		Customer cust = new Customer();
		String msg = null;
		Boolean flag = false;
		cust = customerService.deleteCustomer(customerRepository.findById(id),
				"0");
		if (cust.getActive().equals("0")) {
			flag = true;
			msg = "删除成功";
		} else {
			msg = "删除失败";
		}

		Json json = new Json();
		json.setData(cust);
		json.setMsg(msg);
		json.setSuccess(flag);
		return json;
	}

	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json transferCust(ExtDirectStoreReadRequest request,
			@RequestParam(value = "ids", required = false) List<String> ids)

	{
		String flag = null;
		Boolean result = false;
		List<Customer> cust = null;
		cust = customerService.findTransferCust(ids);
		if (cust.size() < 0) {
			flag = "失败";
		} else {
			flag = "成功";
			result = true;
		}
		List<CustomerDTO> custRegDTOList = customerService
				.converTocustomerDTOList(cust);
		Json json = new Json();
		json.setData(custRegDTOList);
		json.setMsg(flag);
		json.setSuccess(result);
		return json;

	}

	// 批量转移
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json updateCustInfo(
			ExtDirectStoreReadRequest request,
			@RequestParam(value = "ids", required = false) List<String> ids,
			@RequestParam(value = "dealerid", required = false) String dealerid,
			@RequestParam(value = "bpsid", required = false) String bpsid,
			@RequestParam(value = "faeid", required = false) String faeid

	) {
		String flag = null;
		Boolean result = false;
		List<CustReg> custReg = new ArrayList<CustReg>();
		List<CustBPS> custBps = new ArrayList<CustBPS>();

		if (dealerid != null && dealerid != "") {

			custReg = custRegServiceImpl.updateDealer(ids, dealerid);
		}
		if ((bpsid != null && bpsid != "") || (faeid != null && faeid != "")) {

			custBps = custBPSServiceImpl.updateInfo(ids, bpsid, faeid);
		}
		if (custReg.size() > 0 || custBps.size() > 0) {
			flag = "操作成功";
			result = true;
		} else {
			flag = "操作失败";
		}
		Json json = new Json();
		json.setData(null);
		json.setMsg(flag);
		json.setSuccess(result);
		return json;

	}

	// 批量取消
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json cancelCust(ExtDirectStoreReadRequest request,
			@RequestParam(value = "ids", required = false) List<String> ids) {
		String flag = null;
		Boolean result = false;
		List<CustReg> custReg = null;
		List<CustBPS> custBps = null;
		List<CustProjectInfo> project = null;
		List<Contact> contact = null;
		List<CustAddress> add = null;

		custReg = custRegServiceImpl.cancelActive(ids);
		custBps = custBPSServiceImpl.cancelActive(ids);
		project = custProjectInfoServiceImpl.cancelActive(ids);
		contact = contactServiceImpl.cancelActive(ids);
		add = custAddressServiceImpl.cancelActive(ids);

		for (int i = 0; i < ids.size(); i++) {
			Customer model = ISParentCustomer(ids.get(i));
			if (model != null) {
				List<String> models = new ArrayList<String>();
				models = custProdLineRepository.findByCustid(model.getId());
				if (models.size() > 0) {
					custReg = custRegServiceImpl.cancelActive(models);
					custBps = custBPSServiceImpl.cancelActive(models);
					project = custProjectInfoServiceImpl.cancelActive(models);
					contact = contactServiceImpl.cancelActive(models);
					add = custAddressServiceImpl.cancelActive(models);
				}

			}
		}

		if (custReg.size() > 0) {
			flag = "操作成功";
			result = true;
		} else {
			flag = "操作失败";
		}
		Json json = new Json();
		json.setData(null);
		json.setMsg(flag);
		json.setSuccess(result);
		return json;

	}

	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<Customer> readerParentCustomer(
			ExtDirectStoreReadRequest request,
			@RequestParam(value = "name", required = false) String name) {
		List<Customer> pageInfo = customerService.findParent(name);
		ExtDirectStoreReadResult<Customer> slDTO = new ExtDirectStoreReadResult<Customer>(
				pageInfo);
		return slDTO;
	}

	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Customer ISParentCustomer(String cpid) {
		Customer model = new Customer();
		model = customerService.findCustomer(cpid);
		return model;
	}

	// 延长报备日期
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json UploadRegDate(ExtDirectStoreReadRequest request,
			@RequestParam(value = "cpid", required = false) String cpid) {
		String flag = null;
		Boolean result = false;
		List<CustReg> custReg = new ArrayList<CustReg>();
		CustReg model = new CustReg();
		model = custRegRepository.findByRegCpid(cpid);
		model = custRegServiceImpl.UploadRegDate(model);
		if (model.getId() != null) {
			result = true;
			flag = "操作成功！";
		} else {
			flag = "操作失败！";
		}

		Json json = new Json();
		json.setData(model);
		json.setMsg(flag);
		json.setSuccess(result);
		return json;

	}

	// 判断客户名是否存在
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json IsCustName(
			ExtDirectStoreReadRequest request,
			@RequestParam(value = "customername", required = false) String customername,
			@RequestParam(value = "customerid", required = false) String customerid) {
		String flag = null;
		Boolean result = false;
		List<Customer> list = new ArrayList<Customer>();
		list = customerRepository.findIdByCustName(customername, customerid);
		if (list.size() > 0) {
			result = true;
			flag = "有重复";
		} else {
			flag = "无重复";
		}

		Json json = new Json();
		json.setData(list);
		json.setMsg(flag);
		json.setSuccess(result);
		return json;

	}
	
	/*上传批量处理的客户名单*/
	@ExtDirectMethod(ExtDirectMethodType.FORM_POST)
	public ExtDirectFormPostResult customerBatchUpload(HttpServletRequest request, HttpServletResponse response,@RequestParam("fileUpload") MultipartFile fileUpload) throws Exception {
		ExtDirectFormPostResult resp = new ExtDirectFormPostResult(true);
		List<CustomerBatchProc> list = new ArrayList<CustomerBatchProc>();
		String info = "success";
		try {
			list = customerBatchProcService.readFile(fileUpload);
		} catch(BusinessException e){
			info = e.getMessage();
		}catch (Exception e) {
			info = e.getMessage();
		}
		resp.addResultProperty("info", info);
		resp.addResultProperty("list", list);
		return resp;
	}
	/*保存需要批量处理的客户*/
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json saveCustomerBatchInfo(ExtDirectStoreReadRequest request,@RequestParam(value="customerBatch", required = false)List<CustomerBatchProc> customerBatchInfos){
		Json json = new Json();
		boolean success = true;
		String msg = "保存成功！";
		try {
			customerBatchProcService.saveCustomerBatchInfo(customerBatchInfos);
		} catch (Exception e) {
			success=false;
			msg=e.getMessage();
		}
		json.setData(customerBatchInfos.get(0).getSeq());
		json.setMsg(msg);
		json.setSuccess(success);
		return json;
	}
	/*查询批量处理的历史客户*/
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<CustomerBatchProc> readerCustomerBatch(ExtDirectStoreReadRequest request,@RequestParam(value="seq",required=false)String seq){
		List<CustomerBatchProc> list = new ArrayList<CustomerBatchProc>();
		try {
			list = customerBatchProcService.readerCustomerBatchProc(seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExtDirectStoreReadResult<CustomerBatchProc> result = new ExtDirectStoreReadResult<CustomerBatchProc>(list);
		return result;
	}
	/*执行对应的操作*/
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json ProcCustoemrBatch(ExtDirectStoreReadRequest request,@RequestParam(value="seq",required=false)String seq){
		Json json = new Json();
		String msg = "执行成功！";
		boolean success = true;
		try {
			customerBatchProcService.procCustomerBatch(seq);
		} catch (Exception e) {
			success = false;
			msg = e.getMessage();
		}
		json.setMsg(msg);
		json.setSuccess(success);
		return json;
	}

}
