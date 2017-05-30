package app.dailyBalance;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DailyBalanceServiceImpl implements DailyBalanceService{
	
	private final DailyBalanceRepository dailyBalanceRepository;
	
	@Autowired
	public DailyBalanceServiceImpl(final DailyBalanceRepository dailyBalanceRepository) {
		this.dailyBalanceRepository = dailyBalanceRepository;
	}
	
	@Override
	public List<DailyBalance> findAll() {
		return (List<DailyBalance>) dailyBalanceRepository.findAll();
	}

	@Override
	public DailyBalance save(DailyBalance dailyBalance) {
		return dailyBalanceRepository.save(dailyBalance);
	}

	@Override
	public DailyBalance findOne(Long id) {
		return dailyBalanceRepository.findOne(id);
	}

	
	/*@Override
	public DailyBalance findMaxDate(String accountNumber) {
		return this.dailyBalanceRepository.findMaxDate(accountNumber);
	}*/

}
