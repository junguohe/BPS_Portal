package com.erry.auth.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.PriceSpecialDetail;

@Repository
public interface PriceSpecialDetailRepository extends JpaSpecificationExecutor<PriceSpecialDetail>,JpaRepository<PriceSpecialDetail, Long>{
	
	public PriceSpecialDetail findByDid(String did); 
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Price_Special_Detail where DID=?1 ")
	public PriceSpecialDetail findBydid(String did);

	
	@Query(nativeQuery = true, value = "select * from T_BPS_Price_Special_Detail ps where ps.spid =?1")
	public List<PriceSpecialDetail> findListBySpid(String spid);
	
	
}
