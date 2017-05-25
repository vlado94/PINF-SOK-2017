package app.bill;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BillServiceImpl implements BillService{

	private final BillRepository billRepository;
	
	@Autowired
	public BillServiceImpl(final BillRepository billRepository) {
		this.billRepository = billRepository;
	}
	
	@Override
	public List<Bill> findAll() {
		return (List<Bill>) billRepository.findAll();
	}

	@Override
	public Bill save(Bill bill) {
		return billRepository.save(bill);
	}

	@Override
	public Bill findOne(Long id) {
		return billRepository.findOne(id);
	}

	@Override
	public Bill findByAccountNumber(String accountNumber) {
		return this.billRepository.findByAccountNumber(accountNumber);
	}

	@Override
	public List<Bill> findAllCurrentBillsExceptClosingOne(Long id) {
		return this.billRepository.findAllCurrentBillsExceptClosingOne(id);
	}

	@Override
	public List<Bill> findByAccountNumberLikeAndClient_ApplicantLike(String accountNumber,String client) {
		return this.billRepository.findByAccountNumberLikeAndClient_ApplicantLike(accountNumber, client);
	}
	

}
