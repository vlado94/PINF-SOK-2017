package app.user.banker;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
@Transactional
public class BankerServiceImpl implements BankerService {
	private final BankerRepository bankerRepository;
	
	@Autowired
	public BankerServiceImpl(final BankerRepository bankerRepository) {
		this.bankerRepository = bankerRepository;
	}

	@Override
	public List<Banker> findAll() {
		return Lists.newArrayList(bankerRepository.findAll());
	}

	@Override
	public Banker save(Banker Banker) {
		return bankerRepository.save(Banker);
	}

	@Override
	public Banker findOneById(Long id) {
		return bankerRepository.findOne(id);
	}
	
	@Override
	public Banker findOneByMailAndPassword(String mail, String password) {
		return bankerRepository.findByMailAndPassword(mail, password);
	}
	
	@Override
	public Banker findOneByMail(String mail) {
		return bankerRepository.findByMail(mail);
	}

	@Override
	public void delete(Long id) {
		bankerRepository.delete(id);
	}
}