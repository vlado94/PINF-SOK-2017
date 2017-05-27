package app.user.banker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductReport {
	public List<Map<String,?>> findAll() {
		List<Map<String,?>> list = new ArrayList<Map<String,?>>();
		ProductModel model = new ProductModel();
		for(Product p : model.findAll()) {
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("id", p.getId());
			m.put("name", p.getName());
			m.put("price", p.getPrice());
			m.put("quantity", p.getQuantity());
			list.add(m);
		}
		
		return list;
	}
}
