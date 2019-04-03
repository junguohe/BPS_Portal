package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.CustBPS;

@Repository
public interface CustBPSRepository extends JpaSpecificationExecutor<CustBPS>,JpaRepository<CustBPS, Long>{

	//Object findById(String id);
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_bpsinfo where id=?1 ")
	public CustBPS findById(String id); 
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_bpsinfo where cpid=?1 ")
	public CustBPS findByCpid(String cpid); 
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_bpsinfo where cpid=?1 and active=1")
	public List<CustBPS> findByCpids(String cpid);


	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_bpsinfo where cpid=?1 and creator=?2  ")
	public List<CustBPS> findCustBPS(String cpid, String creator); 
}
