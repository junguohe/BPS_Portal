package com.erry.auth.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.MaterialInfo;

@Repository
public interface MaterialInfoRepository extends JpaSpecificationExecutor<MaterialInfo>,JpaRepository<MaterialInfo, Long>{
	//根据code和name唯一确定一个mate
	@Query(nativeQuery = true, value = "select * from T_BPS_MaterialInfo where MaterialCode = ?1 and MaterialName=?2")
	public MaterialInfo findBycodeandname(String code,String name); 
	
	@Query(nativeQuery = true, value = "select * from T_BPS_MaterialInfo where MaterialID = ?1 and active='1'")
	public MaterialInfo findByid(String id);

	@Query(nativeQuery = true, value = "select * from T_BPS_MaterialInfo where materialname = ?1 and active='1'")
	public List<MaterialInfo> findbyName(String materialname); 
	
	@Query(nativeQuery = true, value = "select * from T_BPS_MaterialInfo where materialcode = ?1 and active='1'")
	public List<MaterialInfo> findbyCode(String materialcode); 
}
