package com.erry.auth.service;

import java.util.ArrayList;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.dto.RegionInfoDTO;
import com.erry.auth.model.Json;
import com.erry.auth.model.RegionInfo;
import com.erry.auth.repository.RegionInfoReposotory;
import com.erry.util.GuidIdUtil;
import com.erry.util.StringUtil;

@Service
@Transactional
public class RegionInfoServiceImpl {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private RegionInfoReposotory regionInfoReposotory;

	//封装省市信息 提供修改显示
	public List<RegionInfoDTO> findRegionForUpdate(String province ,String city ){
		StringBuffer sql =new StringBuffer("(SELECT b.id AS ProvinceId,b.id AS CityId, b.City AS Province,NULL AS city FROM dbo.T_BPS_MD_Region a ");
		sql.append("RIGHT JOIN T_BPS_MD_Region b ON b.Province=a.ID ");
		sql.append("WHERE a.City IS NULL ");
		if (!StringUtil.isEmpty(province)) {
			sql.append("AND b.City LIKE '%"+province+"%'");
		}
		if (!StringUtil.isEmpty(city)) {
			sql.append("AND b.ID IN (SELECT Province FROM dbo.T_BPS_MD_Region WHERE City LIKE '%"+city+"%' AND Province !='')");
		}
		sql.append(")");
		
		sql.append("UNION ALL ");
		sql.append("(SELECT NULL ProvinceId,b.ID AS CityId, a.City AS Province,b.City AS city FROM dbo.T_BPS_MD_Region a ");
		sql.append("RIGHT JOIN T_BPS_MD_Region b ON b.Province=a.ID ");
		sql.append("WHERE a.City IS not NULL ");
		if (!StringUtil.isEmpty(province)) {
			sql.append("AND a.City LIKE '%"+province+"%'");
		}
		if (!StringUtil.isEmpty(city)) {
			sql.append("AND b.City LIKE '%"+city+"%'");
		}
		sql.append(")");
		
		sql.append("ORDER BY Province,city ");
		
		Query query = entityManager.createNativeQuery(sql.toString());
		List<RegionInfoDTO> r = new ArrayList<RegionInfoDTO>();
		List<Object[]> objArrList = query.getResultList();
		for(Object[] objArr : objArrList)
		{
			RegionInfoDTO dto = new RegionInfoDTO();
			if(objArr[0]==null){
				dto.setProvinceid("");
			}else{
				dto.setProvinceid(String.valueOf(objArr[0]));
			}
			dto.setId(String.valueOf(objArr[1]));
			dto.setProvince(String.valueOf(objArr[2]));
			if(objArr[3]!=null){
				dto.setCity(String.valueOf(objArr[3]));
			}else{
				dto.setCity("");
			}
			r.add(dto);
		}
		return r;
	}
	//修改
	public Json updateOrsaveCity(String id,String provinceid,String city,String province){
		Json json = new Json();
		String msg = "操作成功！";
		boolean success = true;
		try{
			RegionInfo r=regionInfoReposotory.findById(id);
			
			if(provinceid==""){
				if(r.getProvince()!=null){
					//修改市
					r.setCity(city);
					entityManager.merge(r);
				}else{
					msg = "操作失败！";
					success = false;
				}
				
			}else{
				//修改省
				if(r!=null){
					r.setCity(province);
					entityManager.merge(r);
				}else{
					msg = "操作失败！";
					success = false;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			msg ="操作失败！";
			success=false;
		}
		json.setData(id);
		json.setMsg(msg);
		json.setSuccess(success);
		return json;
	}
	//新增省
	public Json saveProvince(String province){
		Json json = new Json();
		String msg = "操作成功！";
		boolean success = true;
		RegionInfo r = regionInfoReposotory.findProvinceByName(province);
		if(r!=null){
			msg="该省已经存在，无需再次添加";
			success=false;
		}else{
			RegionInfo region = new RegionInfo();
			try {
				region.setId(GuidIdUtil.getGuId());
				region.setCity(province);
				region.setProvince("");
				region.setActive("1");
				entityManager.persist(region);
			} catch (Exception e) {
				e.printStackTrace();
				msg="操作失败";
				success=false;
			}
		}
		
		json.setData(r);
		json.setMsg(msg);
		json.setSuccess(success);
		return json;
	}
	//新增市
		public Json saveCity(String city,String provinceid){
			Json json = new Json();
			String msg = "操作成功！";
			boolean success = true;
			RegionInfo r = regionInfoReposotory.findCityByCityOnProvince(city, provinceid);
			if(r!=null){
				msg="该省已存在该市";
				success=false;
			}else{
				RegionInfo region = new RegionInfo();
				try {
					region.setId(GuidIdUtil.getGuId());
					region.setCity(city);
					region.setProvince(provinceid);
					region.setActive("1");
					entityManager.persist(region);
				} catch (Exception e) {
					e.printStackTrace();
					msg="操作失败";
					success=false;
				}
			}
			
			json.setData(r);
			json.setMsg(msg);
			json.setSuccess(success);
			return json;
		}
	public List<RegionInfo> findAllRegionInfo() {
		return regionInfoReposotory.findProvince();
	}
	
	
	public List<RegionInfo> findRegionInfoByCity(String name) {
		return regionInfoReposotory.findCity(name);
	}


	
	public List<RegionInfo> findList(final String city) {

		List<RegionInfo> pSysLog = regionInfoReposotory.findAll(new Specification<RegionInfo>() {
					public Predicate toPredicate(Root<RegionInfo> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
						List<Expression<Boolean>> expressions = predicate
								.getExpressions();
						if (!StringUtil.isEmpty(city))
							expressions.add(cb.like(root.<String> get("city"), "%" + city + "%"));
							expressions.add(cb.like(root.<String> get("Province"), ""));
						
						return predicate;
					}
				});
		return pSysLog;
	}


	public List<RegionInfo> findByCity(final String province,final String city) {

		List<RegionInfo> pSysLog = regionInfoReposotory.findAll(new Specification<RegionInfo>() {
					public Predicate toPredicate(Root<RegionInfo> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
						List<Expression<Boolean>> expressions = predicate
								.getExpressions();
						expressions.add(cb.like(root.<String> get("active"), "1"));
						if (!StringUtil.isEmpty(province)){
							expressions.add(cb.like(root.<String> get("city"), "%" + city + "%"));
							expressions.add(cb.like(root.<String> get("Province"), ""));
						}
							
						
						return predicate;
					}
				});
		return pSysLog;
	}

}
