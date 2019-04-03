package com.erry.auth.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.erry.auth.model.BPSMonthly;

@Repository
public interface BPSMonthlyRepository extends JpaSpecificationExecutor<BPSMonthly>, JpaRepository<BPSMonthly, Long> {
	@Query(nativeQuery = true,value = "select * from bps_monthly  where period=?1")
	public BPSMonthly findBpsMonthByPeriod(String period);
	@Query(nativeQuery = true,value = "select * from bps_monthly  where id=?1")
	public BPSMonthly findBpsMonthById(String id);
}
