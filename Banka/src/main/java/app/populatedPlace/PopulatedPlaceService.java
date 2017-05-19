package app.populatedPlace;

import java.util.List;

import app.country.Country;

public interface PopulatedPlaceService {

	public List<PopulatedPlace> findAll();

	public PopulatedPlace save(PopulatedPlace populatedPlace);

	public void delete(Long id);
	
	PopulatedPlace findOne(Long id);

	List<PopulatedPlace> findByNameLikeOrPttCodeLikeOrCountry_NameLike(String name,String pttCode,Country country);

	
}
