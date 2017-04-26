package app.closingBill;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.populatedPlace.PopulatedPlaceRepository;

@Service
@Transactional
public class ClosingBillServiceImpl implements ClosingBillService{

	private final ClosingBillRepository closingBillRepository;
	
	@Autowired
	public ClosingBillServiceImpl(final ClosingBillRepository closingBillRepository) {
		this.closingBillRepository = closingBillRepository;
	}
	@Override
	public List<ClosingBill> findAll() {
		// TODO Auto-generated method stub
		return (List<ClosingBill>) closingBillRepository.findAll();
	}

	@Override
	public ClosingBill save(ClosingBill closingBill) {
		// TODO Auto-generated method stub
		return closingBillRepository.save(closingBill);
	}

	@Override
	public ClosingBill findOne(Long id) {
		// TODO Auto-generated method stub
		return closingBillRepository.findOne(id);
	}

}
