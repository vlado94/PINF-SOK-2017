package app.MT102xml;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bank.BankRepository;

@Service
@Transactional
public class MT102xmlServiceImpl implements MT102xmlService{

	private final MT102xmlRepository MT102xmlRepository;
	
	@Autowired
	public MT102xmlServiceImpl(final MT102xmlRepository MT102xmlRepository) {
		this.MT102xmlRepository = MT102xmlRepository;
	}
	@Override
	public List<MT102xml> findAll() {
		return (List<MT102xml>) MT102xmlRepository.findAll();
	}

	@Override
	public MT102xml save(MT102xml MT102xml) {
		return MT102xmlRepository.save(MT102xml);
	}

	@Override
	public MT102xml findOne(Long id) {
		return MT102xmlRepository.findOne(id);
	}

}
