package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.Contact;
import com.erry.auth.model.CustAddress;
import com.erry.auth.model.Customer;

@Repository
public interface ContactRepository extends JpaSpecificationExecutor<Contact>, JpaRepository<Contact, Long> {

	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_ContactInfo ")
	public List<Contact> findAll();

	//public Object findById(String id); 
	
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Contactinfo where id=?1 ")
	public Contact findById(String id);
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Contactinfo where cpid=?1 and active=1")
	public List<Contact> findByCpid(String cpid); 
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Cust_Contactinfo where cpid=?1 and creator=?2 ")
	public List<Contact> findContact(String cpid,String creator); 
	
	
	
}
