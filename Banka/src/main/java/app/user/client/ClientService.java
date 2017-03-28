package app.user.client;

import java.util.List;

import app.user.banker.Banker;

public interface ClientService {
	public List<Client> findAll();

	public Client save(Client Bank);

	public Client findOneById(Long id);

	public Client findOneByMail(String mail);

	public Client findOneByMailAndPassword(String mail, String password);
	
	public void delete(Long id);
}