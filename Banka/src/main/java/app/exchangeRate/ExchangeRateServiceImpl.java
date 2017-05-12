package app.exchangeRate;

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
	public void save(ExchangeRate exchangeRate) {
		exchangeRateRepository.save(exchangeRate);
		
	}

	@Override
	public void delete(Long id) {
		exchangeRateRepository.delete(id);		
	}

	@Override
	public ExchangeRate findOne(Long id) {
		return exchangeRateRepository.findOne(id);
	}
}
