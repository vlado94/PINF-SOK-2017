package app.closingBill;

import java.util.List;


public interface ClosingBillService {
	public List<ClosingBill> findAll();

	public ClosingBill save(ClosingBill closingBill);

	public ClosingBill findOne(Long id);
}
