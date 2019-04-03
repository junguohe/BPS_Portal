package com.erry.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.component.CurrentUserInfo;
import com.erry.auth.dto.MaterialInfoDTO;
import com.erry.auth.dto.TreeDTO;
import com.erry.auth.model.AuUser;
import com.erry.auth.model.Menu;
import com.erry.auth.repository.MenuRepository;
import com.erry.util.StringUtil;

@Service
@Transactional
public class MenuServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private MenuRepository repository;
	
	
	
	public List<Menu> findAll(){
		return repository.findAll();
	}
	
	//获取用户的权限菜单
	public List<Menu> findMenuByUser() {
		List<Menu> list = new ArrayList<Menu>();
		String userid=CurrentUserInfo.getUid();
		StringBuffer sql = new StringBuffer(" from t_bps_function menu  where 1=1 and  menu.active='1' and menu.Module='BPS' ");
		
		if(!StringUtil.isEmpty(userid)&&!userid.equals("153DB85F-4737-4E30-B9DC-45D56A303A35")){
			sql.append(" and menu.funcid in(SELECT rf.functionID FROM T_BPS_Role_Function rf WHERE  rf.ACTIVE='1' AND rf.roleID IN ("
					+ "SELECT ur.roleid FROM T_BPS_User_Role_Rel ur WHERE ur.UserID='"+userid+"'  AND ur.active='1' )) ") ;
		}
		Query query = entityManager.createNativeQuery("select * "+sql,Menu.class);
		return query.getResultList();

	}
	public TreeDTO findFunction() {
		List<Menu> functionList = findMenuByUser();
		List<Menu> myFunctionList = new ArrayList<Menu>();
		for (Menu function : functionList) {
			if (function.getActive().equals("1") ) {
				myFunctionList.add(function);
			}
		}
		return generationRootNode(myFunctionList);
	}
	
	public TreeDTO generationRootNode(List<Menu> allFunctions) {
		// Root层级
		TreeDTO rootNode = new TreeDTO();
		rootNode.setId(null);
		rootNode.setText("root");
		rootNode.setCode("root");
		Map<String, TreeDTO> index = new HashMap<String, TreeDTO>();
		for (Menu function : allFunctions) {
			TreeDTO temp = sysFunctionTreeConstructor(function);
			index.put(function.getFunccode(), temp);
			functionRecursive(function,index);
		}
		for (Menu function : allFunctions) {
			functionAddRecursive(function,index,rootNode);
		}
		return rootNode;
	}
	/**
	 *对当前叶子节点的父节点递归，map中存入所有与当前节点有继承关系的节点对象 
	 * @param function
	 * @param index
	 */
	public void functionRecursive(Menu function,Map<String, TreeDTO> index){
		TreeDTO temp1 = null;
		if(function.getParent()!=null){
			temp1 = sysFunctionTreeConstructor(function.getParent());
			index.put(function.getParent().getFunccode(), temp1);
			//functionRecursive(function.getParent(),index);
		}
	}
	/**
	 *将当前子节点所有有关的父节点，通过递归添加形成树结构
	 * @param function
	 * @param index
	 * @param rootNode
	 */
	public void functionAddRecursive(Menu function,Map<String, TreeDTO> index,TreeDTO rootNode){
		if(function.getParent() != null){
			index.get(function.getParent().getFunccode()).appendChild(index.get(function.getFunccode()));
			//functionAddRecursive(function.getParent(),index,rootNode);
		}else{
			rootNode.appendChild(index.get(function.getFunccode()));
		}
	}
	public TreeDTO sysFunctionTreeConstructor(Menu func) {
		TreeDTO sftDTO = new TreeDTO();
		sftDTO.setId(func.getId());
		sftDTO.setText(func.getFuncname());
		sftDTO.setCode(func.getFunccode());
	//	sftDTO.setIcon(func.get);
		sftDTO.setPath(func.getFuncurl());
		sftDTO.setRemark(func.getRemark());
		sftDTO.setLeaf(func.getChildren()==null ? true : false);
		return sftDTO;
	}
}
