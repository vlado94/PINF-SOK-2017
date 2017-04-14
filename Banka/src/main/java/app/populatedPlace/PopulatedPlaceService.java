package app.populatedPlace;

import java.util.List;

import app.country.Country;

public interface PopulatedPlaceService {

	public List<PopulatedPlace> findAll();

	public void save(PopulatedPlace populatedPlace);
}
