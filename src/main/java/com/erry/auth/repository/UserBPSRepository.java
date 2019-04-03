package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.UserBPS;

@Repository
public interface UserBPSRepository extends JpaSpecificationExecutor<UserBPS>, JpaRepository<UserBPS, Long>  {
	
	@Query(nativeQuery = true, value = "select * from T_BPS_User_BPS ")
	public List<UserBPS> findAll();
	
	@Query(nativeQuery = true, value = "select * from T_BPS_User_BPS where userid= ?1  ")
	public UserBPS findById(String id);
}
