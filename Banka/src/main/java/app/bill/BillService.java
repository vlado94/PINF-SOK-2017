package app.bill;

import java.util.List;

import app.client.Client;

public interface BillService {

	public List<Bill> findAll();

	public Bill save(Bill bill);

	public Bill findOne(Long id);
	
	public Bill findByAccountNumber(String accountNumber);
}
