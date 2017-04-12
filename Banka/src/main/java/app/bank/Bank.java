package app.bank;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import app.bill.Bill;
import app.exchangeRate.ExchangeRate;
import lombok.Data;

@Data
@Entity
public class Bank {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BANK_ID")
	private Long id;
	
	@NotBlank
	@Column
	private String name;
	
	@NotNull
	@Column(length=3)
	private int code;
	
	@NotNull
	@Column(length=8)
	private int swiftCode;
	
	@NotNull
	@Column(length=18)
	private Long clearingAccount; // obracunski racun
	
	@OneToMany
	@JoinTable(name = "BANK_EXCHANGE_RATE", joinColumns = @JoinColumn(name = "BANK_ID"), inverseJoinColumns = @JoinColumn(name = "EXCHANGE_RATE_ID"))
	private List<ExchangeRate> exchangeRate; // kursna lista
	
	/*@OneToMany
	@JoinTable(name = "BANK_BANKERS", joinColumns = @JoinColumn(name = "BANK_ID"), inverseJoinColumns = @JoinColumn(name = "BANKER_ID"))
	private List<Banker> bankers;*/

	@OneToMany
	@JoinTable(name = "BANK_BILLS", joinColumns = @JoinColumn(name = "BANK_ID"), inverseJoinColumns = @JoinColumn(name = "BILL_ID"))
	private List<Bill> bills;
}
