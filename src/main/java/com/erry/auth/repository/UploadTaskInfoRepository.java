package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.CustAddress;
import com.erry.auth.model.Customer;
import com.erry.auth.model.UploadInventory;
import com.erry.auth.model.UploadTaskInfo;

@Repository
public interface UploadTaskInfoRepository extends JpaSpecificationExecutor<UploadTaskInfo>, JpaRepository<UploadTaskInfo, Long> {

	@Query(nativeQuery = true, value = "select * from t_bps_upload_taskinfo ")
	public List<UploadTaskInfo> findAll(); 
	
	@Query(nativeQuery = true, value = "select * from t_bps_upload_taskinfo where tid=?1 ")
	public UploadTaskInfo findById(String id); 
	
 
	
	
}
