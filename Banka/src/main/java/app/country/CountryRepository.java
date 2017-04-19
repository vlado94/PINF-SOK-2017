package app.country;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CountryRepository extends PagingAndSortingRepository<Country, Long>{

	Country findByName(String name);

}
