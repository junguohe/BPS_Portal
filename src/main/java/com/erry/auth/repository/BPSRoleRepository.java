package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.BPSRole;

@Repository
public interface BPSRoleRepository extends JpaSpecificationExecutor<BPSRole>, JpaRepository<BPSRole, Long> {
	@Query("select * from T_BPS_Role where active='1' ")
	public List<BPSRole> findAll();
}
