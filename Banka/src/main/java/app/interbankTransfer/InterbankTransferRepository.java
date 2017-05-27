package app.interbankTransfer;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InterbankTransferRepository extends PagingAndSortingRepository<InterbankTransfer, Long>{

	@Query("SELECT w FROM InterbankTransfer w WHERE w.dateTime is null")
	public List<InterbankTransfer> findAllAndDateTimeIsNull();
	
	@Query("SELECT w FROM InterbankTransfer w WHERE w.dateTime is null and w.bankSender.code=?1")
	public List<InterbankTransfer> findAllByBankCodeAndDateTimeIsNull(Integer codeOfBank);
	
	
}
