package app.populatedPlace;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import app.country.Country;

public interface PopulatedPlaceRepository extends PagingAndSortingRepository<PopulatedPlace, Long>{

	@Query("select p from PopulatedPlace p where lower(p.name) like ?1 and p.pttCode like ?2 and p.country=?3")
	List<PopulatedPlace> findByNameLikeAndPttCodeLikeAndCountry(String name,String pttCode,Country country);

	
}
