package com.erry.auth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.ProdLine;

@Repository
public interface ProductLineRepository extends JpaSpecificationExecutor<ProdLine>,JpaRepository<ProdLine, Long>{
	
	public ProdLine findByProdid(String prodid); 
}
