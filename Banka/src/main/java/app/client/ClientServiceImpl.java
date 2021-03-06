package app.client;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ClientServiceImpl  implements ClientService{

	private final ClientRepository clientRepository;
	
	@Autowired
	public ClientServiceImpl(final ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}
	@Override
	public List<Client> findAll() {
		return (List<Client>) clientRepository.findAll();
	}

	@Override
	public Client save(Client client) {
		return clientRepository.save(client);
	}
	@Override
	public List<Client> findAllIndividualBills() {
		return clientRepository.findAllIndividualBills();
	}
	@Override
	public List<Client> findAllLegalBills() {
		return clientRepository.findAllLegalBills();
	}
	@Override
	public Client findOne(Long id) {
		return clientRepository.findOne(id);
	}

}
