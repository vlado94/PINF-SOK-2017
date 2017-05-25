package app.bank;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
@Transactional
public class BankServiceImpl implements BankService {
	private final BankRepository bankRepository;
	
	@Autowired
	public BankServiceImpl(final BankRepository bankRepository) {
		this.bankRepository = bankRepository;
	}

	@Override
	public List<Bank> findAll() {
		return Lists.newArrayList(bankRepository.findAll());
	}

	@Override
	public Bank save(Bank Bank) {
		return bankRepository.save(Bank);
	}

	@Override
	public Bank findOne(Long id) {
		return bankRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		bankRepository.delete(id);
	}

	@Override
	public Bank findOneByCode(Integer code) {
		return bankRepository.findByCode(code);
	}
}