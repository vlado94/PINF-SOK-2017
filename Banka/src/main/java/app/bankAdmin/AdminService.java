package app.bankAdmin;

public interface AdminService {
	public Admin findOne(Long id);
	
	public Admin findOneByMailAndPassword(String mail, String password);

	public Admin findOneById(Long id);

	public Admin save(Admin admin);
}
