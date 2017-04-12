package app.user.banker;

import java.util.List;

public interface BankerService {
	
	public List<Banker> findAll();

	public Banker save(Banker Bank);

	public Banker findOneById(Long id);
	
	//public Banker findOneByMail(String mail);

	public Banker findOneByMailAndPassword(String mail, String password);

	public void delete(Long id);
}