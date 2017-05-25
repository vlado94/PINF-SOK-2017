package app.bank;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BankRepository extends PagingAndSortingRepository<Bank, Long>{
	
	@Query("select r from Bank r where r.code = ?1")
	public Bank findByCode(Integer code);


}
