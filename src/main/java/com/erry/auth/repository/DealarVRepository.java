package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.DealarV;


@Repository
public interface DealarVRepository extends JpaSpecificationExecutor<DealarV>, JpaRepository<DealarV, Long> {

	@Query(nativeQuery = true, value = "select * from erp_dealar_v where active='Y' ")
	public List<DealarV> findAll();
	
	@Query(nativeQuery = true, value = "select * from erp_dealar_v where active='Y' and id=?1 ")
	public DealarV findById(String id);
}
