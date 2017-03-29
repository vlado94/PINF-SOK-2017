package app.user.clientPersonal;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
@Transactional
public class ClientPersonalServiceImpl implements ClientPersonalService {
	private final ClientPersonalRepository clientRepository;
	
	@Autowired
	public ClientPersonalServiceImpl(final ClientPersonalRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public List<ClientPersonal> findAll() {
		return Lists.newArrayList(clientRepository.findAll());
	}

	@Override
	public ClientPersonal save(ClientPersonal client) {
		return clientRepository.save(client);
	}

	@Override
	public ClientPersonal findOneById(Long id) {
		return clientRepository.findOne(id);
	}
	
	@Override
	public ClientPersonal findOneByMailAndPassword(String mail, String password) {
		return clientRepository.findByMailAndPassword(mail, password);
	}
	
	@Override
	public ClientPersonal findOneByMail(String mail) {
		return clientRepository.findByMail(mail);
	}

	@Override
	public void delete(Long id) {
		clientRepository.delete(id);
	}
}