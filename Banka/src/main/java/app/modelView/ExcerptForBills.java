package app.modelView;

import java.util.ArrayList;

import app.bank.Bank;
import app.bill.Bill;

public class ExcerptForBills {

	private Bank bank;
	
	private ArrayList<BillForExcerpt> bills;
	
	
	public ExcerptForBills(Bank bank) {
		this.bank = bank;
		bills = new ArrayList<BillForExcerpt>();
	}
	
	public ArrayList<BillForExcerpt> setBills() {
		for(int i=0;i<bank.getBills().size();i++) {
			Bill bill = bank.getBills().get(i);
			BillForExcerpt billForReport = new BillForExcerpt(bill.getClient().getApplicant(), bill.getAccountNumber(), bill.getDailyBalances().get(bill.getDailyBalances().size()-1).getNewState());
			bills.add(billForReport);			
		}
		return bills;
	}
}
