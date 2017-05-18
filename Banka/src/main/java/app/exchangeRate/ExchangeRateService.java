package app.exchangeRate;

import java.util.List;

public interface ExchangeRateService {

	public List<ExchangeRate> findAll();

	public ExchangeRate save(ExchangeRate exchangeRates);

	public void delete(Long id);
	
	public ExchangeRate findOne(Long id);
}
