package app.interbankTransfer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

import app.bank.Bank;
import app.depositSlip.DepositSlip;
import lombok.Data;

@Data
@Entity
public class InterbankTransfer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INTERBANK_TRANSFER_ID")
	private Long id;
	
	
	@Column
	@NotBlank
	private String typeOfMessage;
	
	@Column
	private Timestamp dateTime;
	
	@Column
	private double amount;
	
	@OneToMany
	@JoinTable(name = "INTERBANK_TRANSFER_DEPOSIT_SLIP", joinColumns = @JoinColumn(name = "INTERBANK_ID"), inverseJoinColumns = @JoinColumn(name = "DS_ID"))
	private List<DepositSlip> depositSlips;
	
	@ManyToOne
	@JoinColumn(name = "BANK_R_ID")
	private Bank bankReciever;

	@ManyToOne
	@JoinColumn(name = "BANK_S_ID")
	private Bank bankSender;

	public InterbankTransfer(boolean type) {
		depositSlips = new ArrayList<DepositSlip>();
		amount = 0;
		if(type) {//RTGS
			typeOfMessage = "MT102";
			dateTime = new Timestamp(System.currentTimeMillis());
		}
		else {//CLEARING
			typeOfMessage = "MT103";			
		}
	}
	
	public InterbankTransfer() {}
	
}
