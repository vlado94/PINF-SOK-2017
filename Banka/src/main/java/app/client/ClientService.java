package app.client;

import java.util.List;

public interface ClientService {

	public List<Client> findAll();

	public Client save(Client client);

	public List<Client> findAllIndividualBills();

	public List<Client> findAllLegalBills();

	public Client findOne(Long id);
}
