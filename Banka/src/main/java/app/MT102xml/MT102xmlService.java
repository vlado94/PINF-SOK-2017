package app.MT102xml;

import java.util.List;

import app.bank.Bank;

public interface MT102xmlService {

	public List<MT102xml> findAll();

	public MT102xml save(MT102xml MT102xml);

	public MT102xml findOne(Long id);

}
