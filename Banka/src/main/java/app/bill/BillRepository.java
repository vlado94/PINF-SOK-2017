package app.bill;

import org.springframework.data.repository.PagingAndSortingRepository;

import app.client.Client;

public interface BillRepository extends PagingAndSortingRepository<Bill, Long> {

}
