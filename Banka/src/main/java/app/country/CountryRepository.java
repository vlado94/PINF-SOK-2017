package app.country;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CountryRepository extends PagingAndSortingRepository<Country, Long>{

	Country findByName(String name);
	
	List<Country> findByCodeLikeOrNameLike(String code,String name);

}
