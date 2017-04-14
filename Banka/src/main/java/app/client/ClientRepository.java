package app.client;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.country.Country;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

}
