package app.populatedPlace;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PopulatedPlaceRepository extends PagingAndSortingRepository<PopulatedPlace, Long>{

	List<PopulatedPlace> findByNameLikeOrPttCodeLikeOrCountry_NameLike(String name,String pttCode,String country);

	
}
