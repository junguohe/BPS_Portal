package com.erry.auth.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.DealerCurrency;
import com.erry.auth.model.PriceSpecialDetail;

@Repository
public interface DealerCurrencyRepository extends JpaSpecificationExecutor<DealerCurrency>,JpaRepository<DealerCurrency, Long>{
	
	
	@Query(nativeQuery = true, value = "select * from bps_dealer_currency where dealerid=?1 and currentyid=?2 ")
	public DealerCurrency findByDCId(String dealerid,String currentyid);
	
	@Query(nativeQuery = true, value = "select * from bps_dealer_currency where dealerid=?1 ")
	public List<DealerCurrency> findByDealerid(String dealerid);
	
	@Query(nativeQuery = true, value = "select * from bps_dealer_currency where id=?1 ")
	public DealerCurrency findById(String id);
	
	
}
