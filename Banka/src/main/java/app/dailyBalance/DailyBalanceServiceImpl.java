package app.dailyBalance;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bill.Bill;

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
		return (List<DailyBalance>) this.dailyBalanceRepository.findAll();
	}

	@Override
	public DailyBalance save(DailyBalance dailyBalance) {
		return this.dailyBalanceRepository.save(dailyBalance);
	}

	@Override
	public DailyBalance findOne(Long id) {
		return this.dailyBalanceRepository.findOne(id);
	}

	@Override
	public List<DailyBalance> findByBill_id(Long id) {
		// TODO Auto-generated method stub
		return this.dailyBalanceRepository.findByBill_id(id);
	}

}
