package app.modelView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import app.depositSlip.DepositSlip;

public class Excerpt {

	private Date fromDate;
	private Date toDate;
	private List<DepositSlip> depositSlips = new ArrayList<DepositSlip>();
	
	public Excerpt() {
		fromDate = new Date(0);
		toDate = new Date(0);
		depositSlips.add(new DepositSlip(true));
		depositSlips.add(new DepositSlip(true));
		depositSlips.add(new DepositSlip(true));	
	}
	
	public List<DepositSlip> findAll() {
		return depositSlips;
	}
}
