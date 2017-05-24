package app.bank;

import java.util.List;

public interface BankService {
	public List<Bank> findAll();

	public Bank save(Bank Bank);

	public Bank findOne(Long id);

	public Bank findOneByCode(Integer id);

	public void delete(Long id);
}