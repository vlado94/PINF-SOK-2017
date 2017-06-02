package app.modelView;

import lombok.Data;

@Data
public class BillForExcerpt {

	private String owner;
	private String bill;
	private double amount;
	
	public BillForExcerpt(String owner, String bill, double amount) {
		super();
		this.owner = owner;
		this.bill = bill;
		this.amount = amount;
	}
	
	
	
}
