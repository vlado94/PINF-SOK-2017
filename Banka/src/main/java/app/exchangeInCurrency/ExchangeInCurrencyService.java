package app.exchangeInCurrency;

import java.util.List;

public interface ExchangeInCurrencyService {

	public List<ExchangeInCurrency> findAll();

	public ExchangeInCurrency save(ExchangeInCurrency exchangeInCurrency);

	public void delete(Long id);
	
	public ExchangeInCurrency findOne(Long id);
}
