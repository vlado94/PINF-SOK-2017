package app.bankAdmin;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{

	private final AdminRepository adminRepository;
	
	@Autowired
	public AdminServiceImpl(final AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}
	
	@Override
	public Admin findOneByMailAndPassword(String mail, String password) {
		return adminRepository.findByMailAndPassword(mail, password);
	}
	
	@Override
	public Admin findOne(Long id) {
		return adminRepository.findOne(id);
	}
	
	@Override
	public Admin findOneById(Long id) {
		return adminRepository.findOne(id);
	}

	@Override
	public Admin save(Admin admin) {
		return adminRepository.save(admin);
	}
}
