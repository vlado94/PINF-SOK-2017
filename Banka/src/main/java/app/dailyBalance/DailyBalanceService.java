package app.dailyBalance;

import java.util.List;

public interface DailyBalanceService {
	
	public List<DailyBalance> findAll();

	public DailyBalance save(DailyBalance dailyBalance);

	public DailyBalance findOne(Long id);

	//public DailyBalance findMaxDate(String accountNumber);
	
}
