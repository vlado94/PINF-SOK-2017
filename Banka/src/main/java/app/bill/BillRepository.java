package app.bill;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.client.Client;
import app.dailyBalance.DailyBalance;

public interface BillRepository extends PagingAndSortingRepository<Bill, Long> {

	public Bill findByAccountNumber(String accountNumber);
}
