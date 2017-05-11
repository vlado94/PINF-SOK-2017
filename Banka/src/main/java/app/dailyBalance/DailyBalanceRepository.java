package app.dailyBalance;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface DailyBalanceRepository extends PagingAndSortingRepository<DailyBalance, Long> {
	
	public List<DailyBalance> findByBill_id(Long id);
	
	//@Query("select * from daily_balance where date=( select  max(date) from daily_balance where daily_balance .bill_id=(select bill_id from bill where account_number=456752378574512664)) "
	//		+ "and daily_balance_id in (select  daily_balance_id from daily_balance where daily_balance.bill_id=(select bill_id from bill where account_number=456752378574512664))")
	//public DailyBalance findMaxDate(String accountNumber);
}
