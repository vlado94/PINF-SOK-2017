package app.billPersonal;

import java.util.List;

public interface BillPersonalService {
	public List<BillPersonal> findAll();

	public BillPersonal save(BillPersonal bill);

	public BillPersonal findOne(Long id);

	public void delete(Long id);
}