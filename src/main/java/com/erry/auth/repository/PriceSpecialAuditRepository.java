package com.erry.auth.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.PriceSpecialAudit;

@Repository
public interface PriceSpecialAuditRepository extends JpaSpecificationExecutor<PriceSpecialAudit>,JpaRepository<PriceSpecialAudit, Long> {
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Price_Special_Audit where DID=?1 and SPID=?2 ")
	public PriceSpecialAudit findbysidandspid(String did,String spid);
	
	@Query(nativeQuery = true, value = " select top 1  * from T_BPS_Price_Special_Audit where active='1' and SPID in (select spid from T_BPS_Price_Special where Applicator = ?1 and BillStatus='2' and active='1' ) and DID in (select DID from T_BPS_Price_Special_Detail where CPID =?2 and MaterialID = ?3 and active='1')")
	public PriceSpecialAudit findlastPriceAudit(String applicator,String cpid,String materialid);
	
	@Query(nativeQuery = true, value = "select * from T_BPS_Price_Special_Audit where active='1' and DID in (select DID from T_BPS_Price_Special_Detail where CPID =?1 and MaterialID=?2 and active='1') and  ActiveDate >= ?3 and ID !=(select id from T_BPS_Price_Special_Audit where did=?4 and spid=?5)")
	public List<PriceSpecialAudit> findbysidandspid(String cpid,String materialid,Date approvedate,String did,String spid);
}
