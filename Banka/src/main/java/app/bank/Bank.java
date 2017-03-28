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

import org.hibernate.validator.constraints.NotBlank;

import app.bill.Bill;
import app.user.banker.Banker;
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
	
	@OneToMany
	@JoinTable(name = "BANK_BANKERS", joinColumns = @JoinColumn(name = "BANK_ID"), inverseJoinColumns = @JoinColumn(name = "BANKER_ID"))
	private List<Banker> bankers;

	@OneToMany
	@JoinTable(name = "BANK_BILLS", joinColumns = @JoinColumn(name = "BANK_ID"), inverseJoinColumns = @JoinColumn(name = "BILL_ID"))
	private List<Bill> bills;
}
