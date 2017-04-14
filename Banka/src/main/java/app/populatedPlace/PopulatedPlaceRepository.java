package app.populatedPlace;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.country.Country;
import scala.annotation.implicitNotFound;

public interface PopulatedPlaceRepository extends PagingAndSortingRepository<PopulatedPlace, Long>{

}
