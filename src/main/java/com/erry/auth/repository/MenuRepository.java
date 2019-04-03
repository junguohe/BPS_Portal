package com.erry.auth.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.CustAddress;
import com.erry.auth.model.CustProdLine;
import com.erry.auth.model.Menu;
import com.erry.auth.model.ProdLine;

@Repository
public interface MenuRepository extends JpaSpecificationExecutor<Menu>,JpaRepository<Menu, Long>{
	
	@Query(nativeQuery = true, value = "select * from t_bps_function   ")
	public List<Menu> findAll();
	
	@Query(nativeQuery = true, value = "select * from t_bps_function where module='BPS'  ")
	public List<Menu> findAlls(); 
	
}
