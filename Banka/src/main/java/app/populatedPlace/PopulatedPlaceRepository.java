package app.populatedPlace;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PopulatedPlaceRepository extends PagingAndSortingRepository<PopulatedPlace, Long>{

	List<PopulatedPlace> findByNameLikeOrPttCodeLike(String name,String pttCode);

	
}
