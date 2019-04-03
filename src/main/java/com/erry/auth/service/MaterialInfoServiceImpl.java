package com.erry.auth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.erry.auth.dto.ContactDTO;
import com.erry.auth.dto.CustAddressDTO;
import com.erry.auth.dto.CustRegDTO;
import com.erry.auth.dto.CustomerDTO;
import com.erry.auth.dto.MaterialInfoDTO;
import com.erry.auth.model.Contact;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustReg;
import com.erry.auth.model.Customer;
import com.erry.auth.model.MaterialInfo;
import com.erry.auth.model.PriceStrategyDetail;
import com.erry.auth.repository.ContactRepository;
import com.erry.auth.repository.CustAddressRepository;
import com.erry.auth.repository.CustomerRepository;
import com.erry.auth.repository.MaterialInfoRepository;
import com.erry.auth.repository.PriceStrategyDetailRepository;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class MaterialInfoServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private MaterialInfoRepository infoRepository;
	
	@Autowired
	private PriceStrategyDetailRepository priceStrategyDetailRepository;

	public List<MaterialInfoDTO> findAll(String materialname) {
		List<MaterialInfoDTO> list = new ArrayList<MaterialInfoDTO>();
		String sql = null;
		if(!StringUtil.isEmpty(materialname)){
			sql = "from T_BPS_MaterialInfo m,T_BPS_ProductLine p where m.ProdID = p.ProdID and materialname like '%"
					+ materialname + "%' and m.active=1 order by MaterialName ";
		}else{
			sql = " from T_BPS_MaterialInfo m,T_BPS_ProductLine p where m.ProdID = p.ProdID  and m.active=1 order by MaterialName   ";
		}
		Query query = entityManager.createNativeQuery("select m.Active,m.Assembly,m.AssType,m.LifeCycle,m.MaterialCode,m.MaterialID,m.MaterialName,m.Remark,m.ProdID,p.ProdName "+sql);
		List<Object[]> objArrList = query.getResultList();
		for (Object[] objArr : objArrList) {
			MaterialInfoDTO dto = new MaterialInfoDTO(); 
			dto.setActive(String.valueOf(objArr[0]));
			dto.setAssembly(String.valueOf(objArr[1]));
			dto.setAsstype(String.valueOf(objArr[2]));
			dto.setLifecycle(String.valueOf(objArr[3]));
			dto.setMaterialcode(String.valueOf(objArr[4]));
			dto.setId(String.valueOf(objArr[5]));
			dto.setMaterialname(String.valueOf(objArr[6]));
			dto.setRemark(String.valueOf(objArr[7]));
			dto.setProdid(String.valueOf(objArr[8]));
			dto.setProdname(String.valueOf(objArr[9]));
			list.add(dto);
		}
		return list;

	}
	
	public List<MaterialInfoDTO> findAlls(String materialname) {
		List<MaterialInfoDTO> list = new ArrayList<MaterialInfoDTO>();
		String sql = null;
		if(!StringUtil.isEmpty(materialname)){
			sql = "from T_BPS_MaterialInfo m,T_BPS_ProductLine p  where m.ProdID = p.ProdID and materialname = '"
					+ materialname + "' and m.active='1' order by MaterialName ";
		}else{
			sql = " from T_BPS_MaterialInfo m,T_BPS_ProductLine p where m.ProdID = p.ProdID and m.active='1' order by MaterialName   ";
		}
		Query query = entityManager.createNativeQuery("select m.Active,m.Assembly,m.AssType,m.LifeCycle,m.MaterialCode,m.MaterialID,m.MaterialName,m.Remark,m.ProdID,p.ProdName "+sql);
		List<Object[]> objArrList = query.getResultList();
		for (Object[] objArr : objArrList) {
			MaterialInfoDTO dto = new MaterialInfoDTO(); 
			dto.setActive(String.valueOf(objArr[0]));
			dto.setAssembly(String.valueOf(objArr[1]));
			dto.setAsstype(String.valueOf(objArr[2]));
			dto.setLifecycle(String.valueOf(objArr[3]));
			dto.setMaterialcode(String.valueOf(objArr[4]));
			dto.setId(String.valueOf(objArr[5]));
			dto.setMaterialname(String.valueOf(objArr[6]));
			dto.setRemark(String.valueOf(objArr[7]));
			dto.setProdid(String.valueOf(objArr[8]));
			dto.setProdname(String.valueOf(objArr[9]));
			list.add(dto);
		}
		return list;

	}
	
	//根据code和name得到唯一的对象
	public MaterialInfo getByCodeAndName(String code,String name){
		return infoRepository.findBycodeandname(code, name);
	}
	public MaterialInfo findByid(String id){
		return infoRepository.findByid(id);
	}

	public List<MaterialInfoDTO> findAllByActive(String materialname) {
		
		List<MaterialInfoDTO> list = new ArrayList<MaterialInfoDTO>();
		String sql = null;
		if(!StringUtil.isEmpty(materialname)){
			sql = "from T_BPS_MaterialInfo m,T_BPS_ProductLine p where m.ProdID = p.ProdID and materialname = '"
					+ materialname + "' and m.active=1 order by MaterialName ";
		}else{
			sql = " from T_BPS_MaterialInfo m,T_BPS_ProductLine p where m.ProdID = p.ProdID and m.active=1 order by MaterialName   ";
		}
		Query query = entityManager.createNativeQuery("select m.Active,m.Assembly,m.AssType,m.LifeCycle,m.MaterialCode,m.MaterialID,m.MaterialName,m.Remark,m.ProdID,p.ProdName "+sql);
		List<Object[]> objArrList = query.getResultList();
		for (Object[] objArr : objArrList) {
			MaterialInfoDTO dto = new MaterialInfoDTO(); 
			dto.setActive(String.valueOf(objArr[0]));
			dto.setAssembly(String.valueOf(objArr[1]));
			dto.setAsstype(String.valueOf(objArr[2]));
			dto.setLifecycle(String.valueOf(objArr[3]));
			dto.setMaterialcode(String.valueOf(objArr[4]));
			dto.setId(String.valueOf(objArr[5]));
			dto.setMaterialname(String.valueOf(objArr[6]));
			dto.setRemark(String.valueOf(objArr[7]));
			dto.setProdid(String.valueOf(objArr[8]));
			dto.setProdname(String.valueOf(objArr[9]));
			list.add(dto);
		}
		return list;

	}

}
