package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.erry.auth.model.CustAddress;

@Repository
public interface CustAddressRepository extends JpaSpecificationExecutor<CustAddress>, JpaRepository<CustAddress, Long> {

	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Addinfo ")
	public List<CustAddress> findAll(); 
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Addinfo where cpid=?1 and creator=?2 ")
	public List<CustAddress> findCustAddress(String cpid,String creator); 
	

	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Addinfo where id=?1 ")
	public CustAddress findById(String id);


	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Addinfo where cpid=?1 ")
	public List<CustAddress> findByCpid(String cpid); 
	
	
	
	
	
}
