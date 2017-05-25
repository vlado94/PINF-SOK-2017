package app.interbankTransfer;

import java.util.List;

public interface InterbankTransferService {

	public List<InterbankTransfer> findAll();

	public InterbankTransfer save(InterbankTransfer interbankTransfer);

	public void delete(Long id);
	
	public InterbankTransfer findOne(Long id);

	public List<InterbankTransfer> findAllAndDateTimeIsNull();
}
