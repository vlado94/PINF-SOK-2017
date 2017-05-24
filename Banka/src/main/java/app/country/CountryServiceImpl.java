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
	public Country save(Country country) {
		return countryRepository.save(country);
	}
	
	@Override
	public Country findOne(Long id) {
		return countryRepository.findOne(id);
	}
	
	@Override
	public Country findByName(String name) {
		return countryRepository.findByName(name);
	}

	@Override
	public void delete(Long id) {
		countryRepository.delete(id);
	}

	@Override
	public List<Country> findByCodeLikeAndNameLike(String code, String name) {
		return countryRepository.findByCodeLikeAndNameLike(code, name);
	}
}
