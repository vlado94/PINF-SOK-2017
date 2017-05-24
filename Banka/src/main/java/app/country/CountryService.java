package app.country;

import java.util.List;

public interface CountryService {

	public List<Country> findAll();

	public Country save(Country country);

	Country findOne(Long id);

	void delete(Long id);

	Country findByName(String name);
	
	List<Country> findByCodeLikeAndNameLike(String code,String name);

}
