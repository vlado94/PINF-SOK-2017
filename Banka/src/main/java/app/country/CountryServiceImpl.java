package app.country;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CountryServiceImpl implements CountryService{

	private final CountryRepository countryRepository;
	
	@Autowired
	public CountryServiceImpl(final CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@Override
	public List<Country> findAll() {
		return (List<Country>) countryRepository.findAll();
	}

	@Override
	public void save(Country country) {
		countryRepository.save(country);
	}
	
	@Override
	public Country findOne(Long id) {
		return countryRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		countryRepository.delete(id);
	}
}
