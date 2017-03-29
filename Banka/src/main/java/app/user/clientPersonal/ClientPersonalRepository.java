package app.user.clientPersonal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientPersonalRepository extends PagingAndSortingRepository<ClientPersonal, Long>{

	@Query("select r from ClientPersonal r where r.mail = ?1 and r.password = ?2")
	public ClientPersonal findByMailAndPassword(String mail, String password);

	public ClientPersonal findByMail(String mail);
}
