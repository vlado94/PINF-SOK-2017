package app.closingBill;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import app.bill.Bill;
import app.depositSlip.DepositSlip;
import lombok.Data;

@Data
@Entity
public class ClosingBill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CLOSING_BILL_ID")
	private Long id;
	
	//@NotBlank
	@Column
	private Date date;
	
	@Column
	private String billSuccessor;
	
	//account that is closing
	@ManyToOne
	@JoinColumn(name = "BILL_ID")
	private Bill bill;  //not null
	
	@ManyToOne
	@JoinColumn(name = "DEPOSIT_SLIP_ID")
	private DepositSlip depositSlip;
	
}
