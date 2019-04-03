package com.erry.auth.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.erry.auth.model.UserRoleRel;

@Repository
public interface UserRoleRelRepository extends JpaSpecificationExecutor<UserRoleRel>,JpaRepository<UserRoleRel, Long>{
	
	
	@Query(nativeQuery = true, value = "select * from t_bps_user_role_rel where userid=?1 and roleid=?2 ")
	public UserRoleRel findByURId(String userid,String roleid);
	
	@Query(nativeQuery = true, value = "select * from t_bps_user_role_rel where userid=?1 ")
	public List<UserRoleRel> findByUserid(String userid);
	
	@Query(nativeQuery = true, value = "select * from t_bps_user_role_rel where id=?1 ")
	public UserRoleRel findById(String id);
	
	
}
