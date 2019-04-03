package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.Contact;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustProjectInfo;


@Repository
public interface CustProjectInfoRepository extends JpaSpecificationExecutor<CustProjectInfo>,JpaRepository<CustProjectInfo, Long>{

	//Object findById(String id);
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Projectinfo where id=?1 ")
	public CustProjectInfo findById(String id);

	
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Projectinfo where cpid=?1 ")
	public CustProjectInfo findByCpid(String Cpid); 
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Projectinfo where cpid=?1 ")
	public List<CustProjectInfo> findCpids(String string);
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Projectinfo where cpid=?1 and creator=?2 ")
	public List<CustProjectInfo> findCustProject(String string,String creator);
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Projectinfo where cpid=?1 and active=1")
	public List<CustProjectInfo> findByCpids(String Cpid); 
	

}
