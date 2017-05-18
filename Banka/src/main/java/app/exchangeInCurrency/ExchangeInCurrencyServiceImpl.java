package app.exchangeInCurrency;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ExchangeInCurrencyServiceImpl implements ExchangeInCurrencyService {

	private final ExchangeInCurrencyRepository exchangeInCurrencyRepository;
	
	@Autowired
	public ExchangeInCurrencyServiceImpl(final ExchangeInCurrencyRepository exchangeInCurrencyRepository) {
		this.exchangeInCurrencyRepository = exchangeInCurrencyRepository;
	}

	@Override
	public List<ExchangeInCurrency> findAll() {
		return (List<ExchangeInCurrency>) exchangeInCurrencyRepository.findAll();
	}

	@Override
	public ExchangeInCurrency save(ExchangeInCurrency exchangeInCurrency) {
		return exchangeInCurrencyRepository.save(exchangeInCurrency);		
	}

	@Override
	public void delete(Long id) {
		exchangeInCurrencyRepository.delete(id);		
	}

	@Override
	public ExchangeInCurrency findOne(Long id) {
		return exchangeInCurrencyRepository.findOne(id);
	}
}
