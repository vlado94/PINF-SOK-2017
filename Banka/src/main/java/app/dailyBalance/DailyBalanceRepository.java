package app.dailyBalance;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface DailyBalanceRepository extends PagingAndSortingRepository<DailyBalance, Long> {
	
	public List<DailyBalance> findByBill_id(Long id);
}
