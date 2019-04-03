package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.RegionInfo;

@Repository
public interface RegionInfoReposotory extends JpaSpecificationExecutor<RegionInfo>,JpaRepository<RegionInfo, Long> {
//	@Query(nativeQuery = true, value = "  select * from T_BPS_MD_Region where Province='' and city=?1")
//	public List<RegionInfo> findProvince(String city);
	
	@Query(nativeQuery = true, value = "  select * from T_BPS_MD_Region where Province=''")
	public List<RegionInfo> findProvince();
	
	@Query(nativeQuery = true, value = "  select * from T_BPS_MD_Region ")
	public List<RegionInfo> findAll();
	
	
	@Query(nativeQuery = true, value = "select * from T_BPS_MD_Region where Province in (select id from T_BPS_MD_Region where City=?1 and  Province ='' or Province =null) ")
	public List<RegionInfo> findCity(String name);
	
	//查找该省下是否已经有该市
	@Query(nativeQuery=true,value="select * from T_BPS_MD_Region where Active='1' and City=?1 and Province=?2")
	public RegionInfo findCityByCityOnProvince(String city,String province);
	
	//查找省是否存在Byname
	@Query(nativeQuery=true,value="select * from T_BPS_MD_Region where Active='1' and City=?1 and Province=''")
	public RegionInfo findProvinceByName(String province);
	
	//查找省是否存在Byid
	@Query(nativeQuery=true,value="select * from T_BPS_MD_Region where Active='1' and id=?1 and Province=''")
	public RegionInfo findProvinceByProvince(String provinceid);
	
	@Query(nativeQuery=true,value="select * from T_BPS_MD_Region where Active='1' and ID=?1 ")
	public RegionInfo findById(String id);
	
}
