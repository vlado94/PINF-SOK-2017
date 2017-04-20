package app.client;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

	@Query("select r from Client r where r.type = FIZICKO")
	List<Client> findAllIndividualBills();

	@Query("select r from Client r where r.type = PRAVNO")
	List<Client> findAllLegalBills();

}
