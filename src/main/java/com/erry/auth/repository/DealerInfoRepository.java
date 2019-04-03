package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.DealerInfo;


@Repository
public interface DealerInfoRepository extends JpaSpecificationExecutor<DealerInfo>, JpaRepository<DealerInfo, Long> {

	@Query(nativeQuery = true, value = "select * from T_BPS_Dealerinfo where active='1'")
	public List<DealerInfo> findAll();
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Dealerinfo where active='1' ")
	public List<DealerInfo> findAlls();
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Dealerinfo where DealerID=?1 and active='1'")
	public DealerInfo findByid(String id);
	
	//@Query(nativeQuery = true, value = "select * from dbo.T_BPS_Customer_Info where CustID in (select CustID from dbo.T_BPS_Customer_ProdLine where CPID in (select r.CPID from dbo.T_BPS_Cust_RegInfo r where r.id =?1)) ")
	@Query(nativeQuery = true, value = " select * from T_BPS_DealerInfo where DealerID = ?1 ")
	public DealerInfo findDealerInfo(String dealerId);
	
	@Query(nativeQuery = true, value = " select * from T_BPS_DealerInfo where DealerName = ?1 ")
	public DealerInfo findDealerInfoByDealerName(String dealerName);

	
	
}
