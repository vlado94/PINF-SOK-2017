package app.billPersonal;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
@Transactional
public class BillPersonalServiceImpl implements BillPersonalService {
	private final BillPersonalRepository billRepository;
	
	@Autowired
	public BillPersonalServiceImpl(final BillPersonalRepository billRepository) {
		this.billRepository = billRepository;
	}

	@Override
	public List<BillPersonal> findAll() {
		return Lists.newArrayList(billRepository.findAll());
	}

	@Override
	public BillPersonal save(BillPersonal bill) {
		return billRepository.save(bill);
	}

	@Override
	public BillPersonal findOne(Long id) {
		return billRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		billRepository.delete(id);
	}
}