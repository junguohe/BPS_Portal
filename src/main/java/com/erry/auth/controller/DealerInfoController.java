package com.erry.auth.controller;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.erry.auth.dto.ContactDTO;
import com.erry.auth.dto.CustAddressDTO;
import com.erry.auth.dto.CustRegDTO;
import com.erry.auth.dto.CustomerDTO;
import com.erry.auth.dto.DealerInfoDTO;
import com.erry.auth.model.Contact;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.Customer;
import com.erry.auth.model.DealerInfo;
import com.erry.auth.model.Json;
import com.erry.auth.model.ProdLine;
import com.erry.auth.repository.ContactRepository;
import com.erry.auth.repository.DealerInfoRepository;
import com.erry.auth.service.ContactServiceImpl;
import com.erry.auth.service.CustAddressServiceImpl;
import com.erry.auth.service.CustRegServiceImpl;
import com.erry.auth.service.DealerInfoServiceImpl;


@Controller("dealerControl")
public class DealerInfoController {
	

	@Autowired
	private DealerInfoServiceImpl dealerService;
	
	Logger log = Logger.getLogger(DealerInfoController.class);

	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<DealerInfoDTO>  readDTO(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="dealerid",required=false)String dealerid ){
		Page<DealerInfo> pageInfo =dealerService.findList(request.getPage()-1, request.getLimit(),dealerid);
		List<DealerInfoDTO> list = dealerService.converTocustomerDTOList(pageInfo.getContent());
		ExtDirectStoreReadResult<DealerInfoDTO> slDTO = new ExtDirectStoreReadResult<DealerInfoDTO>(pageInfo.getTotalElements(),list);
		return slDTO;
	}
	//dealer 联想控件
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<DealerInfo> getAll(ExtDirectStoreReadRequest request ,
			@RequestParam(value="dealername",required=false)String dealername ){
		ExtDirectStoreReadResult<DealerInfo> readResult = new ExtDirectStoreReadResult<DealerInfo>(dealerService.findDealerByname(dealername));
		return readResult;
	}
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<CustRegDTO>  findList(
											ExtDirectStoreReadRequest request ,
											@RequestParam(value="cpid",required=false)String cpid,
											@RequestParam(value="dealerid",required=false)String dealerid){
		//获取分页后Map集合
				Map<Integer,List<CustReg>> pageMap = dealerService.findCustRegList(request.getPage()-1, request.getLimit(),cpid,dealerid);
				Integer total = null;
				List<CustReg> custRegList = new ArrayList<CustReg>();
				for(Map.Entry<Integer,List<CustReg>> entry : pageMap.entrySet()){
					total = entry.getKey();
					custRegList = entry.getValue();
				}
				List<CustRegDTO> custRegDTOList = dealerService.converTocustRegDTOLists(custRegList);
				ExtDirectStoreReadResult<CustRegDTO> slDTO = new ExtDirectStoreReadResult<CustRegDTO>(total,custRegDTOList);
				return slDTO;
	}
	
	
	
}
