package app.populatedPlace;

import java.util.List;

import app.client.Client;
import app.country.Country;

public interface PopulatedPlaceService {

	public List<PopulatedPlace> findAll();

	public void save(PopulatedPlace populatedPlace);

	public void delete(Long id);
	
	PopulatedPlace findOne(Long id);

	
}
