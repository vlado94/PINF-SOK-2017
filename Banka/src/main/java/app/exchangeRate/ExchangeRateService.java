package app.exchangeRate;

import java.sql.Date;
import java.util.List;

public interface ExchangeRateService {

	public List<ExchangeRate> findAll();

	public ExchangeRate save(ExchangeRate exchangeRates);

	public void delete(Long id);
	
	public ExchangeRate findOne(Long id);
	
	List<ExchangeRate> findByNumberOfExchangeRateAndDateAndStartDate(Integer number,Date date, Date startDate);
	
	List<ExchangeRate> findByDateAndStartDate(Date date, Date startDate);
	
	List<ExchangeRate> findByStartDate(Date startDate);
	
	List<ExchangeRate> findByDate(Date date);
	
	List<ExchangeRate> findByNumberOfExchangeRateAndStartDate(Integer number, Date startDate);
	
	List<ExchangeRate> findByNumberOfExchangeRateAndDate(Integer number,Date date);
	
	List<ExchangeRate> findByNumberOfExchangeRate(Integer number);
}
