package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.DealerUploadReSale;

@Repository
public interface UploadReSaleRepository extends JpaSpecificationExecutor<DealerUploadReSale>, JpaRepository<DealerUploadReSale, Long> {

	@Query(nativeQuery = true, value = "select * from t_bps_dealer_upload_resale ")
	public List<DealerUploadReSale> findAll(); 
	
	@Query(nativeQuery = true, value = "select * from t_bps_dealer_upload_resale where id=?1 ")
	public DealerUploadReSale findById(String id);
	
	@Query(nativeQuery = true, value = "select * from t_bps_dealer_upload_resale where tid=?1 ")
	public List<DealerUploadReSale> fingByTid(String tid); 
	
	
	
	
}
