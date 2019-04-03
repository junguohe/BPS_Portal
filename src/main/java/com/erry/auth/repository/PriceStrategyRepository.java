package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.PriceStrategy;

@Repository
public interface PriceStrategyRepository extends JpaSpecificationExecutor<PriceStrategy>, JpaRepository<PriceStrategy, Long>{
	//  select * from T_BPS_Price_Strategy where (select (Code+VersionNo) typesid from T_BPS_Price_Strategy) =?1 and active=?2
	@Query(nativeQuery = true, value = "select top 1 * from T_BPS_Price_Strategy where Code=?1 order by createdate desc ")
	public PriceStrategy findBycodeno(String codeno);
	
	//失效策略
	@Query(nativeQuery = true, value = "update T_BPS_Price_Strategy set Active='0',status='2' where code =?1 ")
	public void updateprice(String codeno);
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Price_Strategy where active='1' and  code = ?1 ")
	public PriceStrategy findpricebycode(String code);
	
	//获取所有生效的策略
	@Query(nativeQuery = true, value = " select * from T_BPS_Price_Strategy where active='1' and Status='1'  ")
	public List<PriceStrategy> findpricecode();
	
	
	//获取除了提交的价格之外的所以同政策编码的数据
	@Query(nativeQuery = true, value = " select * from T_BPS_Price_Strategy where Code=?1 and SID !=?2  ")
	public List<PriceStrategy> findListByCode(String code,String id);
}
