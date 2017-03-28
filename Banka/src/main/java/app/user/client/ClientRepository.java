package app.user.client;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long>{

	@Query("select r from Client r where r.mail = ?1 and r.password = ?2")
	public Client findByMailAndPassword(String mail, String password);

	public Client findByMail(String mail);
}
