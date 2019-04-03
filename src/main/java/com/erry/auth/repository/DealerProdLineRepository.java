package com.erry.auth.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erry.auth.model.DealerCurrency;
import com.erry.auth.model.DealerProdLine;
import com.erry.auth.model.PriceSpecialDetail;

@Repository
public interface DealerProdLineRepository extends JpaSpecificationExecutor<DealerProdLine>,JpaRepository<DealerProdLine, Long>{
	
	
	@Query(nativeQuery = true, value = "select * from bps_dealer_prodline where dealerid=?1 and prodid=?2 ")
	public DealerProdLine findByDPId(String dealerid,String prodid);
	
	@Query(nativeQuery = true, value = "select * from bps_dealer_prodline where dealerid=?1 ")
	public List<DealerProdLine> findByDealerid(String dealerid);
	
	@Query(nativeQuery = true, value = "select * from bps_dealer_prodline where id=?1 ")
	public DealerProdLine findById(String id);
	
	
}
