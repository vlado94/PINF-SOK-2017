package app.client;

import java.util.List;

import app.country.Country;

public interface ClientService {

	public List<Client> findAll();

	public void save(Client client);

	public List<Client> findAllIndividualBills();

	public List<Client> findAllLegalBills();
}
