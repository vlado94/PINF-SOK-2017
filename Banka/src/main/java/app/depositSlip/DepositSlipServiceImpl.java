package app.depositSlip;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DepositSlipServiceImpl implements DepositSlipService{
	
	private final DepositSlipRepository depositSlipRepository;
	
	@Autowired
	public DepositSlipServiceImpl(final DepositSlipRepository depositSlipRepository) {
		this.depositSlipRepository = depositSlipRepository;
	}
	
	@Override
	public List<DepositSlip> findAll() {
		return (List<DepositSlip>) depositSlipRepository.findAll();
	}

	@Override
	public DepositSlip save(DepositSlip dailyBalance) {
		return depositSlipRepository.save(dailyBalance);
	}

	@Override
	public DepositSlip findOne(Long id) {
		return depositSlipRepository.findOne(id);
	}
}