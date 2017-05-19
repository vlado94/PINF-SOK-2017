package app.populatedPlace;

import java.util.List;

public interface PopulatedPlaceService {

	public List<PopulatedPlace> findAll();

	public PopulatedPlace save(PopulatedPlace populatedPlace);

	public void delete(Long id);
	
	PopulatedPlace findOne(Long id);

	List<PopulatedPlace> findByNameLikeOrPttCodeLike(String name,String pttCode);

	
}
