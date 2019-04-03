package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.erry.auth.model.PersonView;

@Repository
public interface PersonRepository extends JpaSpecificationExecutor<PersonView>, JpaRepository<PersonView, Long> {

	@Query(nativeQuery = true, value = "select * from erp_person_v where active='1'  ")
	public List<PersonView> findAll();
	
	@Query(nativeQuery = true, value = "select * from erp_person_v where active='1' and fempgroup = ?1 ")
	public List<PersonView> findByfempgroup(String fempgroup);
	//根据empid查询
	@Query(nativeQuery = true, value = " select * from erp_person_v where active='1' and EmpID=?1 ")
	public  PersonView findByempid(String empid);
	
	@Query(nativeQuery = true, value = " select * from erp_person_v where active='1' and id=?1 ")
	public PersonView findById(String id);
	
	@Query(nativeQuery = true, value = " select COUNT(*) from erp_person_v where id !=?1 and ( per_id=?2 or EmpID=?3) ")
	public int findList(String id, String per_id, String empid);
	
	
	
}
