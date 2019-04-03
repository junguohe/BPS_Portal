package com.erry.auth.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.CustProdLine;
import com.erry.auth.model.Customer;
import com.erry.auth.model.ProdLine;

@Repository
public interface CustomerProdLineRepository extends JpaSpecificationExecutor<CustProdLine>,JpaRepository<CustProdLine, Long>{
	@Query(nativeQuery = true, value = "select * from T_BPS_Customer_Prodline ")
	public List<CustProdLine> findAll();
	
	public CustProdLine findByProdid(String prodid); 
	
	public CustProdLine findByCustid(String custid); 
}
