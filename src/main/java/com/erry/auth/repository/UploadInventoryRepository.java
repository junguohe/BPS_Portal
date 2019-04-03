package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.DealerUploadReSale;
import com.erry.auth.model.UploadInventory;
import com.erry.auth.model.UploadInventoryAdjustment;

@Repository
public interface UploadInventoryRepository extends JpaSpecificationExecutor<UploadInventory>, JpaRepository<UploadInventory, Long> {

	@Query(nativeQuery = true, value = "select * from t_bps_dealer_upload_inventory ")
	public List<UploadInventory> findAll(); 
	
	@Query(nativeQuery = true, value = "select * from t_bps_dealer_upload_inventory where id=?1 ")
	public UploadInventory findById(String id);
	
	@Query(nativeQuery = true, value = "select * from t_bps_dealer_upload_inventory where tid=?1 ")
	public List<UploadInventory> fingByTid(String tid); 
	
	
	
	
}
