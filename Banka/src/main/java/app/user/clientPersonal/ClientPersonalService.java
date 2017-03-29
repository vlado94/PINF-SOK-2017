package app.user.clientPersonal;

import java.util.List;

import app.user.banker.Banker;

public interface ClientPersonalService {
	public List<ClientPersonal> findAll();

	public ClientPersonal save(ClientPersonal Bank);

	public ClientPersonal findOneById(Long id);

	public ClientPersonal findOneByMail(String mail);

	public ClientPersonal findOneByMailAndPassword(String mail, String password);
	
	public void delete(Long id);
}