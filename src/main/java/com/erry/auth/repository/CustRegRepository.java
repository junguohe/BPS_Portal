package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustProdLine;
import com.erry.auth.model.CustReg;

@Repository
public interface CustRegRepository extends JpaSpecificationExecutor<CustReg>, JpaRepository<CustReg, Long> {

	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_RegInfo ")
	public List<CustReg> findAll(); 
	
	public List<CustReg> findByCustprodline(CustProdLine custProdLine);
	
	
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Reginfo where active='1' and cpid=?1 and id!=?2 and regstatus='0'")
	public  List<CustReg> findCustReg(String cpid,String id);
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Reginfo where cpid=?1 and id= ?2 and active='1'")
	public  CustReg findCustRegs(String cpid,String id); 
	

	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Reginfo where id=?1 ")
	public CustReg findById(String id); 
	
	
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Reginfo where cpid=?1 and active=1 ")
	public CustReg findByCpid(String cpid); 
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Reginfo where cpid=?1 and cpid<>?2 ")
	public CustReg findCpids(String cpid,String id);
	
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Reginfo where cpid=?1")
	public List<CustReg> findByCpids(String cpid);
	
//open
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Reginfo where cpid=?1  and regstatus=1 and active=1 ")
	public List<CustReg> findByCpidOpen(String cpid);
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Reginfo where cpid=?1  and regstatus=1 and active=1 ")
	public List<CustReg> findByCpidss(String cpid);

	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Reginfo where cpid=?1 and dealerid=?2 and active=1 ")
	public CustReg findcustReg(String cpid, String dealerid);
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_RegInfo where cpid in (select cpid from T_BPS_Customer_ProdLine where CustID=?1 and ProdID=?2) ")
	public CustReg finBycustandprodid(String custid, String prodid);
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Reginfo where cpid=?1 and isbps=?2 and active=1 ")
	public CustReg finByBPSCpid(String cpid, String bps);

	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Reginfo where cpid=?1 and isbps=?2 and active=1 and regstatus='2' and approveresult ='3' ")
	public CustReg finByBPSCpidBo(String cpid, String bps);

	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Reginfo where cpid=?1  and active=1 and regstatus='1' and approveresult ='2' ")
	public CustReg findByRegCpid(String cpid);

	
}
