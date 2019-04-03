package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.AuUser;

@Repository
public interface AuUserRepository  extends JpaSpecificationExecutor<AuUser>, JpaRepository<AuUser, Long> {
	
	@Query(nativeQuery = true, value = "select * from T_BPS_User where usertype = '0' and (ExpireDate>GETDATE() or ExpireDate is null) and active=1 and usercode = ?1")
	public List<AuUser> findByUsercode(String usercode); 
	
	public AuUser findByUid(String uid);
	
	@Query(nativeQuery = true, value = "select * from T_BPS_User")
	public List<AuUser> findAllUser();
	
	@Query("from AuUser obj where obj.uid != ?1 and obj.usercode = ?2")
	public List<AuUser> findUserByNotInId(String id, String usercode);
	
	//@Query("select * from T_BPS_User where [UserID]=?1 ")
	@Query(nativeQuery = true, value = "select * from T_BPS_User where UserID=?1")
	public AuUser findUserById(String id);
	
	@Query(nativeQuery = true, value = " select * from T_BPS_User where UserCode=?1")
	public AuUser findUserByLoginName(String loginName);
}
