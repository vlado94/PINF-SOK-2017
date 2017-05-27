package app.MT103xml;

import java.util.List;

public interface MT103xmlService {

	public List<MT103xml> findAll();

	public MT103xml save(MT103xml MT103xml);

	public MT103xml findOne(Long id);
}
