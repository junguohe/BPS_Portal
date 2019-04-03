package com.erry.auth.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.AuUser;
import com.erry.auth.model.Customer;


@Repository
public interface CustomerRepository extends JpaSpecificationExecutor<Customer>, JpaRepository<Customer, Long> {

	@Query(nativeQuery = true, value = "select * from T_BPS_Customer_Info ")
	public List<Customer> findAll(); 
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Customer_Info where id=?1 ")
	public Customer findById(String id); 
	
	public Customer findByCustname(String custname);
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Customer_Info where custname=?1 ")
	public List<Customer> findIdByName(String custname);
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Customer_Info where custname=?1 and active=1 and custid !=?2  ")
	public List<Customer> findIdByCustName(String custname,String custid);
	
	public Customer findByCustcode(String custcode);
	
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Customer_Info where custname= ?1 and custid!=?2 ")
	public List<Customer> findAllByCustomer(String custname,String id);
	
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Customer_Info where taxno= ?1 and custid!=?2")
	public List<Customer> findAllByTaxno(String taxno,String id);
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Customer_Info where custid= ?1 ")
	public Customer findByCustid(String custid);
	
	@Query(nativeQuery = true, value = "select * from dbo.T_BPS_Customer_Info c where c.Active='1' and c.CustID = (select p.CustID from dbo.T_BPS_Customer_ProdLine p where p.Active='1' and p.ProdID  = ?1 and p.CustID=?2)")
	public List<Customer> findCustByProdId(String prodId, String custId);
	
	
	@Query(nativeQuery = true, value = "select * from dbo.T_BPS_Customer_Info c where c.Active='1' and c.ParenetCompany is null and c.CustID = (select p.CustID from dbo.T_BPS_Customer_ProdLine p where p.cpid=?1)")
	public Customer findCustByCpid(String cpid);
	
	
	//public List<Customer> findAllIsParent();
	@Query(nativeQuery = true, value = "select * from dbo.T_BPS_Customer_Info where IsParent = '1' and Active='1'")
	public List<Customer> findAllIsParent();
	
	@Query(nativeQuery = true, value = " select * from T_BPS_Customer_Info where custid =(select custid from T_BPS_Customer_ProdLine where cpid =(select cpid from T_BPS_Cust_RegInfo where id = ?1))")
	public Customer findOpen(String id);
	//1
	@Query(nativeQuery = true, value = "select * from T_BPS_Customer_Info where taxno= ?1 ")
	public Customer findByTaxno(String taxno);
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Customer_Info where taxno= ?1 ")
	public Map<Integer, List<Customer>> findCustByProdId(String uid,String prodid, String id);
	
	@Query(nativeQuery = true, value = "select * from dbo.T_BPS_Customer_Info where CustID in (select CustID from dbo.T_BPS_Customer_ProdLine where CPID=?1 and active=1) and active=1")
	public Customer findCustBycpid(String cpid);
	
	
}
