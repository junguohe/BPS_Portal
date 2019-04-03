package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.UserDealer;

@Repository
public interface UserDealerRepostory extends JpaSpecificationExecutor<UserDealer>, JpaRepository<UserDealer, Long> {
	
	@Query(nativeQuery = true, value = "select * from T_BPS_User_Dealer where DealerID = ?1 ")
	public List<UserDealer> findByDealerId(String id);
	
	@Query(nativeQuery = true, value = "select * from T_BPS_User_Dealer where DealerID = ?1 ")
	public UserDealer findById(String id);
	
	@Query(nativeQuery = true, value = "select * from T_BPS_User_Dealer where UserID = ?1 ")
	public UserDealer findByUserId(String userId);
	
}
