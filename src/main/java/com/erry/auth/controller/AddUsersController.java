package com.erry.auth.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.erry.auth.dto.UserDealerDTO;
import com.erry.auth.model.AuUser;
import com.erry.auth.model.BPSRole;
import com.erry.auth.model.DealerInfo;
import com.erry.auth.model.Json;
import com.erry.auth.model.Person;
import com.erry.auth.model.UserBPS;
import com.erry.auth.model.UserDealer;
import com.erry.auth.repository.AuUserRepository;
import com.erry.auth.service.AuUserSeviceImpl;
import com.erry.auth.service.BPSRoleService;
import com.erry.auth.service.DealerInfoServiceImpl;
import com.erry.auth.service.MailService;
import com.erry.auth.service.UserBPSServiceImpl;
import com.erry.auth.service.UserDealerServiceImpl;

@Controller("adduserscontrol")
public class AddUsersController {
	
	@Autowired
	private UserDealerServiceImpl userDealerServiceImpl;
	

	@Autowired
	private UserBPSServiceImpl userbpsserviceimpl;
	
	@Autowired
	private AuUserSeviceImpl auUserSeviceImpl;
	
	@Autowired
	private AuUserRepository auUserRepository;
	
	@Autowired
	private DealerInfoServiceImpl dealerInfoServiceImpl;
	
	@Autowired
	private BPSRoleService bpsRoleService;
	
	@Autowired
	private MailService mailService;
	/**
	 * dealer用户失效
	 * @param request
	 * @param userid
	 * @return
	 */
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public Json endUser(ExtDirectStoreReadRequest request,@RequestParam(value="userid",required=false)String userid ){
		return auUserSeviceImpl.endUser(userid);
	}
	/**
	 * 获取userDealer
	 * @param request
	 * @param dealerName
	 * @return
	 */
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<UserDealerDTO>  readUserDealers(ExtDirectStoreReadRequest request ,@RequestParam(value="name",required=false)String dealerName){
		Map<Integer,List<UserDealerDTO>> pageMap = userDealerServiceImpl.findUserDealerList(request.getPage()-1,request.getLimit(),dealerName);
		Integer total = null;
		List<UserDealerDTO> list = new ArrayList<UserDealerDTO>();
		for(Map.Entry<Integer,List<UserDealerDTO>> entry : pageMap.entrySet()){
			total = entry.getKey();
			list = entry.getValue();
		}
//		List<UserDealerDTO> userDealerDTOList = userDealerServiceImpl.converTouserdealerDTOList(list);
		ExtDirectStoreReadResult<UserDealerDTO> userdealer = new ExtDirectStoreReadResult<UserDealerDTO>(total,list);
		return userdealer;
	}
	
	
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<UserBPS>  readUserBPSs(ExtDirectStoreReadRequest request ,@RequestParam(value="name",required=false)String name){
		Map<Integer,List<UserBPS>> pageMap = userbpsserviceimpl.findUserBPSList(request.getPage()-1,request.getLimit(),name);
		Integer total = null;
		List<UserBPS> list = new ArrayList<UserBPS>();
		for(Map.Entry<Integer,List<UserBPS>> entry : pageMap.entrySet()){
			total = entry.getKey();
			list = entry.getValue();
		}
		ExtDirectStoreReadResult<UserBPS> userdealer = new ExtDirectStoreReadResult<UserBPS>(total,list);
		return userdealer;
	}
			
	/**
	 * 获取角色
	 * @param request
	 * @param rolename
	 * @return
	 */
	@ExtDirectMethod(ExtDirectMethodType.STORE_READ)
	public ExtDirectStoreReadResult<BPSRole>  readcustDTOs(ExtDirectStoreReadRequest request ,@RequestParam(value="rolename",required=false)String rolename){
		Map<Integer,List<BPSRole>> pageMap = bpsRoleService.findBPSRoleList(request.getPage()-1,request.getLimit(),rolename);
		Integer total = null;
		List<BPSRole> list = new ArrayList<BPSRole>();
		for(Map.Entry<Integer,List<BPSRole>> entry : pageMap.entrySet()){
			total = entry.getKey();
			list = entry.getValue();
		}
		ExtDirectStoreReadResult<BPSRole> userdealer = new ExtDirectStoreReadResult<BPSRole>(total,list);
		return userdealer;
	}
	/**
	 * 新增dealer用户
	 * @param name 用户名
	 * @param email 
	 * @param telno
	 * @param dealername dealer名
	 * @param loginname 登录名
	 * @return
	 */
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json createUserDealer(
			@RequestParam(value="id",required=false) String id,
			@RequestParam(value="email",required=false) String email,
			@RequestParam(value="telno",required=false) String telno,
			@RequestParam(value="dealercode",required=false) String dealercode,
			@RequestParam(value="dealername",required=false) String dealername,
			@RequestParam(value="loginname",required=false) String loginname,
			@RequestParam(value="ename",required=false) String ename
			){
		Json json = new Json();
		String result = null;
		boolean flag = true;
		String dealerId=null;
		UserDealer dealer =null;
		AuUser auUser=null;
		try {
			if(auUserRepository.findUserByLoginName(loginname)==null){
				//add dealerinfo
				DealerInfo info = dealerInfoServiceImpl.createDealerInfo(dealername,dealercode,ename);
				dealerId = info.getDealerid();
				auUser = auUserSeviceImpl.createLoginUser(loginname,info.getDealername());
				dealer = userDealerServiceImpl.createUserDealer(dealerId, auUser.getUid(), info.getDealername(), email, telno);
			}
		} catch (Exception e) {
			flag=false;
			result="新增失败";
		}
		
		if(dealer==null){
			flag=false;
			result="新增失败";
		}else{
			result="新增成功！";
			try {
				mailService.createAddUserEmail(dealer, auUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		json.setData(dealer);
		json.setMsg(result);
		json.setSuccess(flag);
		return json;
	}
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public Json UpdateUserDealer(@RequestParam(value="userid",required=false) String userid,
			@RequestParam(value="dealername",required=false) String dealername,
			@RequestParam(value="dealercode",required=false) String dealercode,
			@RequestParam(value="loginname",required=false) String loginname,
			@RequestParam(value="telno",required=false) String telno,
			@RequestParam(value="ename",required=false) String ename,
			@RequestParam(value="email",required=false) String email,
			@RequestParam(value="expiredate",required=false) String expiredate){
		Json json = new Json();
		boolean success= true;
		String msg="修改成功";
		try {
			userDealerServiceImpl.updateUserDealer(userid, dealername, dealercode, loginname, telno, ename, email, expiredate);
		} catch (Exception e) {
			success=false;
			msg =  "修改失败";
		}
		json.setMsg(msg);
		json.setSuccess(success);
		json.setData("");
		return json;
	}
	@ExtDirectMethod(ExtDirectMethodType.SIMPLE_NAMED)
	public void createUserBPS(@RequestParam(value="person",required=false) Person p,@RequestParam(value="roleid",required=false) List<String> roleid){
		
	}
}
