package com.erry.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.PriceSpecial;
import com.erry.auth.model.PriceStrategy;

@Repository
public interface PriceSpecialRepository extends JpaSpecificationExecutor<PriceSpecial>, JpaRepository<PriceSpecial, Long> {
	public PriceSpecial findBySpid(String spid); 
	  
		@Query(nativeQuery = true, value = "select top 1 * from T_BPS_Price_Special ps where ps.Applicator=?1 and spid in(select spid from T_BPS_Price_Special_Detail where MaterialID=?2 and CPID=?3) order by ApplyDate ")
		public PriceSpecial findLastPrice(String creator,String materialid,String cpid);

		
	@Query(nativeQuery = true, value = "select  * from T_BPS_Price_Special ps where ps.spid=?1 ")
	public PriceSpecial findById(String spid);
		


	
}
