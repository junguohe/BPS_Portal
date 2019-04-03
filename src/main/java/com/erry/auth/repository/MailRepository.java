package com.erry.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.erry.auth.model.BPSMail;

@Repository
public interface MailRepository extends JpaSpecificationExecutor<BPSMail>,JpaRepository<BPSMail, Long>{
	@Query(nativeQuery = true, value = "select top 100 * from dbo.T_BPS_EMAIL where status='0'")
	public List<BPSMail> findBystauts(); 
}
