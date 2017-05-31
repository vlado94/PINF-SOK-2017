package app.accountStatement;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountStatementServiceImpl implements AccountStatementService{

	private final AccountStatementRepository accountStatementRepository;
	
	@Autowired
	public AccountStatementServiceImpl(final AccountStatementRepository accountStatementRepository) {
		this.accountStatementRepository = accountStatementRepository;
	}
	@Override
	public List<AccountStatement> findAll() {
		return (List<AccountStatement>) accountStatementRepository.findAll();
	}
}
