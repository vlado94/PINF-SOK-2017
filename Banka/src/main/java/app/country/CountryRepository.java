package app.country;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CountryRepository extends PagingAndSortingRepository<Country, Long>{

	Country findByName(String name);
	
	@Query("select c from Country c where lower(c.code) like ?1 and lower(c.name) like ?2")
	List<Country> findByCodeLikeAndNameLike(String code,String name);

}
