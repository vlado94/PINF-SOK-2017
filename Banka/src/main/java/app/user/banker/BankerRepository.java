package app.user.banker;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BankerRepository extends PagingAndSortingRepository<Banker, Long>{

	@Query("select r from Banker r where r.mail = ?1 and r.password = ?2")
	public Banker findByMailAndPassword(String mail, String password);

	public Banker findByMail(String mail);
}
