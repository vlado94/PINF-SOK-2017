package app.MT103xml;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class MT103xmlServiceImpl implements MT103xmlService{

	private final MT103xmlRepository MT103xmlRepository;
	
	@Autowired
	public MT103xmlServiceImpl(final MT103xmlRepository MT103xmlRepository) {
		this.MT103xmlRepository = MT103xmlRepository;
	}
	
	@Override
	public List<MT103xml> findAll() {
		return (List<MT103xml>) MT103xmlRepository.findAll();
	}

	@Override
	public MT103xml save(MT103xml MT103xml) {
		return MT103xmlRepository.save(MT103xml);
	}

	@Override
	public MT103xml findOne(Long id) {
		return MT103xmlRepository.findOne(id);
	}

}
