package com.erry.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.CustBPS;
import com.erry.auth.model.Dictionary;

@Repository
public interface DirectoryRepository extends JpaSpecificationExecutor<Dictionary>,JpaRepository<Dictionary, Long>{
	
	@Query(nativeQuery = true, value = "select * from t_bps_md_directory where id=?1 ")
	public Dictionary findById(String id); 

}
