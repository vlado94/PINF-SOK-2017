package app.user.banker;

import java.util.ArrayList;
import java.util.List;

public class ProductModel {

	List<Product> list = new ArrayList<Product>();
	
	public ProductModel() {
		list.add(new Product("p1","n1",5,10000));
		list.add(new Product("p2","n2",6,10001));
		list.add(new Product("p3","n3",7,10002));
		list.add(new Product("p4","n4",8,10003));
	}
	public List<Product> findAll() {
		
		return list;
	}
}
