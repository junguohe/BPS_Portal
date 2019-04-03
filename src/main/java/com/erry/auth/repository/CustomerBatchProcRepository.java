package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.erry.auth.model.CustomerBatchProc;

public interface CustomerBatchProcRepository extends JpaSpecificationExecutor<CustomerBatchProc>, JpaRepository<CustomerBatchProc, String> {
	@Query(nativeQuery = true, value = "SELECT TOP 1 * FROM [dbo].[T_BPS_Customer_BatchProc] WHERE id=?1 AND Seq=?2 AND Status=0 ")
	public CustomerBatchProc findOne(String id,String seq);
	
	@Query(nativeQuery = true, value = "SELECT * FROM [dbo].[T_BPS_Customer_BatchProc] WHERE Seq=?1")
	public List<CustomerBatchProc> readerCustomerBatchProc(String seq);
}
