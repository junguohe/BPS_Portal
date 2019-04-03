package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.DealerUploadReSale;
import com.erry.auth.model.UploadInventoryAdjustment;

@Repository
public interface UploadInventoryAdjustmentRepository extends JpaSpecificationExecutor<UploadInventoryAdjustment>, JpaRepository<UploadInventoryAdjustment, Long> {

	@Query(nativeQuery = true, value = "select * from t_bps_dealer_upload_inventory_adjustment ")
	public List<UploadInventoryAdjustment> findAll(); 
	
	@Query(nativeQuery = true, value = "select * from t_bps_dealer_upload_inventory_adjustment where id=?1 ")
	public UploadInventoryAdjustment findById(String id);
	
	@Query(nativeQuery = true, value = "select * from t_bps_dealer_upload_inventory_adjustment where tid=?1 ")
	public List<UploadInventoryAdjustment> fingByTid(String tid); 
	
	
	
	
}
