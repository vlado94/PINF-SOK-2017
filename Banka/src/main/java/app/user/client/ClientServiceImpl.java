package app.user.client;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
	private final ClientRepository clientRepository;
	
	@Autowired
	public ClientServiceImpl(final ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public List<Client> findAll() {
		return Lists.newArrayList(clientRepository.findAll());
	}

	@Override
	public Client save(Client client) {
		return clientRepository.save(client);
	}

	@Override
	public Client findOneById(Long id) {
		return clientRepository.findOne(id);
	}
	
	@Override
	public Client findOneByMailAndPassword(String mail, String password) {
		return clientRepository.findByMailAndPassword(mail, password);
	}
	
	@Override
	public Client findOneByMail(String mail) {
		return clientRepository.findByMail(mail);
	}

	@Override
	public void delete(Long id) {
		clientRepository.delete(id);
	}
}