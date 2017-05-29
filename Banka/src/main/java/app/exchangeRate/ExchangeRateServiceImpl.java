package app.exchangeRate;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ExchangeRateServiceImpl implements ExchangeRateService {

	private final ExchangeRateRepository exchangeRateRepository;
	
	@Autowired
	public ExchangeRateServiceImpl(final ExchangeRateRepository exchangeRateRepository) {
		this.exchangeRateRepository = exchangeRateRepository;
	}

	@Override
	public List<ExchangeRate> findAll() {
		return (List<ExchangeRate>) exchangeRateRepository.findAll();
	}

	@Override
	public ExchangeRate save(ExchangeRate exchangeRate) {
		return exchangeRateRepository.save(exchangeRate);		
	}

	@Override
	public void delete(Long id) {
		exchangeRateRepository.delete(id);		
	}

	@Override
	public ExchangeRate findOne(Long id) {
		return exchangeRateRepository.findOne(id);
	}

	@Override
	public List<ExchangeRate> findByNumberOfExchangeRateAndDateAndStartDate(Integer number, Date date, Date startDate) {
		// TODO Auto-generated method stub
		return exchangeRateRepository.findByNumberOfExchangeRateAndDateAndStartDate(number, date, startDate);
	}

	@Override
	public List<ExchangeRate> findByDateAndStartDate(Date date, Date startDate) {
		// TODO Auto-generated method stub
		return exchangeRateRepository.findByDateAndStartDate(date, startDate);
	}

	@Override
	public List<ExchangeRate> findByStartDate(Date startDate) {
		// TODO Auto-generated method stub
		return exchangeRateRepository.findByStartDate(startDate);
	}

	@Override
	public List<ExchangeRate> findByDate(Date date) {
		// TODO Auto-generated method stub
		return exchangeRateRepository.findByDate(date);
	}

	@Override
	public List<ExchangeRate> findByNumberOfExchangeRateAndStartDate(Integer number, Date startDate) {
		// TODO Auto-generated method stub
		return exchangeRateRepository.findByNumberOfExchangeRateAndStartDate(number, startDate);
	}

	@Override
	public List<ExchangeRate> findByNumberOfExchangeRateAndDate(Integer number, Date date) {
		// TODO Auto-generated method stub
		return exchangeRateRepository.findByNumberOfExchangeRateAndDate(number, date);
	}

	@Override
	public List<ExchangeRate> findByNumberOfExchangeRate(Integer number) {
		// TODO Auto-generated method stub
		return exchangeRateRepository.findByNumberOfExchangeRate(number);
	}
}
