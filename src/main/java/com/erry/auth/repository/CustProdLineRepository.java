package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustProdLine;
import com.erry.auth.model.CustProjectInfo;
import com.erry.auth.model.Customer;
import com.erry.auth.model.ProdLine;

@Repository
public interface CustProdLineRepository extends JpaSpecificationExecutor<CustProdLine>, JpaRepository<CustProdLine, Long> {

	@Query(nativeQuery = true, value = "select * from T_BPS_Customer_Prodline ")
	public List<CustProdLine> findAll(); 
	
	//1
	@Query(nativeQuery = true, value = "select * from T_BPS_Customer_Prodline where cpid=?1 and active=1 ")
	public CustProdLine findById(String cpid); 
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Customer_Prodline where cpid=?1 and regstatus=1  ")
	public CustProdLine findIds(String cpid);
	

	@Query(nativeQuery = true, value = "select * from T_BPS_Customer_Prodline where prodid=?1 and custid=?2 and active =1 ")
	public CustProdLine selectProdLine(String prodid, String id);

	@Query(nativeQuery = true, value = "select * from T_BPS_Customer_Prodline where custid=?1 and prodid=?2 ")
	public List<CustProdLine> findCpids(String custid, String prodid);

	@Query(nativeQuery = true, value = "select cpid from T_BPS_Customer_Prodline where custid in (select custid from T_BPS_Customer_Info where ParenetCompany=?1) ")
	public List<String> findByCustid(String id); 
	
}
