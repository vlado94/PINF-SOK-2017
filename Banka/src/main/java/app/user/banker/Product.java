package app.user.banker;

import lombok.Data;

@Data
public class Product {
	private String id;
	private String name;
	private Double price;
	private int quantity;
	
	public Product(String id, String name, double price, int quantity) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	
	
	
}
