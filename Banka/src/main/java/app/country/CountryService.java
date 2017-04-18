package app.country;

import java.util.List;

public interface CountryService {

	public List<Country> findAll();

	public void save(Country country);

	Country findOne(Long id);

	void delete(Long id);
	
}
