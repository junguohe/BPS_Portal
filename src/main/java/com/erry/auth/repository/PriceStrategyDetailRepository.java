package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.PriceStrategyDetail;

@Repository
public interface PriceStrategyDetailRepository extends JpaSpecificationExecutor<PriceStrategyDetail>, JpaRepository<PriceStrategyDetail, Long>{
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Price_Strategy_Detail ")
	public List<PriceStrategyDetail> findAll();
	
	@Query(nativeQuery = true, value = "update T_BPS_Price_Strategy_Detail set Active='0' where sid =?1 ")
	public void updatepricedetail(String sid);
	
	@Query(nativeQuery = true, value = "  select * from T_BPS_Price_Strategy_Detail where sid = ?1 ")
	public List<PriceStrategyDetail> getpricebysid(String sid);
	
	
	@Query(nativeQuery = true, value = "  select * from T_BPS_Price_Strategy_Detail where id = ?1 ")
	public PriceStrategyDetail findById(String id);
	
	@Query(nativeQuery = true, value = "select * from dbo.T_BPS_Price_Strategy_Detail where SID=( select top 1 sid from dbo.T_BPS_Price_Strategy where PublicDate <?1 and Status='1' and Active='1'order by publicdate desc)and MaterialID=?2 and Active='1'")
	public PriceStrategyDetail getLastListprice(String publicdate,String materialid);
	
}
