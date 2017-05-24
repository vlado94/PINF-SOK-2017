package app.interbankTransfer;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class InterbankTransferServiceImpl implements InterbankTransferService {

	private final InterbankTransferRepository interbankTransferRepo;
	
	@Autowired
	public InterbankTransferServiceImpl(final InterbankTransferRepository interbankTransferRepo) {
		this.interbankTransferRepo = interbankTransferRepo;
	}

	@Override
	public List<InterbankTransfer> findAll() {
		return (List<InterbankTransfer>) interbankTransferRepo.findAll();
	}

	@Override
	public InterbankTransfer save(InterbankTransfer interbankTransfer) {
		return interbankTransferRepo.save(interbankTransfer);
	}

	@Override
	public void delete(Long id) {
		interbankTransferRepo.delete(id);
	}

	@Override
	public InterbankTransfer findOne(Long id) {
		return interbankTransferRepo.findOne(id);
	}
}