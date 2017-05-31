package app.accountStatement;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface AccountStatementRepository  extends PagingAndSortingRepository<AccountStatement, Long> {

}
