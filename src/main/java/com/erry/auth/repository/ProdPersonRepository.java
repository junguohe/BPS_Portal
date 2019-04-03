package com.erry.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.ProdPerson;

@Repository
public interface ProdPersonRepository extends JpaSpecificationExecutor<ProdPerson>, JpaRepository<ProdPerson, Long> {

	
	@Query(nativeQuery = true, value = " select * from erp_area_v where  id=?1 ")
	public ProdPerson findById(String id);
	@Query(nativeQuery = true, value = " select COUNT(*) from erp_area_v where id !=?1 and  area_id=?2 ")
	public int findList(String id, String area_id);

	
	
}
