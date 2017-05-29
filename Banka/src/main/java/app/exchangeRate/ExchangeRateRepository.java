package app.exchangeRate;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ExchangeRateRepository extends PagingAndSortingRepository<ExchangeRate, Long>{
	
	List<ExchangeRate> findByNumberOfExchangeRateAndDateAndStartDate(Integer number,Date date, Date startDate);
	
	List<ExchangeRate> findByDateAndStartDate(Date date, Date startDate);
	
	List<ExchangeRate> findByStartDate(Date startDate);
	
	List<ExchangeRate> findByDate(Date date);
	
	List<ExchangeRate> findByNumberOfExchangeRateAndStartDate(Integer number, Date startDate);
	
	List<ExchangeRate> findByNumberOfExchangeRateAndDate(Integer number,Date date);
	
	List<ExchangeRate> findByNumberOfExchangeRate(Integer number);


}
