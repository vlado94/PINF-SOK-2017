package app.bankAdmin;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AdminRepository extends PagingAndSortingRepository<Admin, Long> {

	@Query("select r from Admin r where r.mail = ?1 and r.password = ?2")
	public Admin findByMailAndPassword(String mail, String password);

}
