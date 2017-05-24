package app.bill;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BillRepository extends PagingAndSortingRepository<Bill, Long> {

	public Bill findByAccountNumber(String accountNumber);
	
	@Query("select b from Bill b where b.status='true' and bill_id<>?#{[0]}")
	public List<Bill> findAllCurrentBillsExceptClosingOne(Long id);
	
	List<Bill> findByAccountNumberLikeAndClient_ApplicantLike(String accountNumber,String client);
}
