package app.bill;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import app.client.Client;
import app.closingBill.ClosingBill;
import app.dailyBalance.DailyBalance;
import lombok.Data;

@Data
@Entity
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BILL_ID")
	private Long id;
	
	//@NotBlank
	@Column
	private String accountNumber; //18
	
	//@NotNull
	@Column
	private boolean status;//false=zatvoren, true=otvoren
	
	//@NotNull
	@Column
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "CLIENT_ID")
	private Client client;  //not null

	/*@OneToMany
	@JoinTable(name = "BILL_CLOSING_BILL", joinColumns = @JoinColumn(name = "BILL_ID"), inverseJoinColumns = @JoinColumn(name = "CLOSING_BILL_ID"))
	private List<ClosingBill> closingBills;*/
	

	@ManyToMany
	@JoinTable(name = "BILL_DAILY_BALANCE", joinColumns = @JoinColumn(name = "BILL_ID"), inverseJoinColumns = @JoinColumn(name = "DAILY_BALANCE_ID"))
	private List<DailyBalance> dailyBalances;
	
	
}
