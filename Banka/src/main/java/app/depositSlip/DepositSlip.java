package app.depositSlip;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import app.bill.Bill;
import app.closingBill.ClosingBill;
import app.enums.Type;
import app.paymentTypes.PaymentTypes;
import lombok.Data;

@Data
@Entity
public class DepositSlip {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DEPOSIT_SLIP_ID")
	private Long id;	

	@Column
    @Enumerated(EnumType.STRING)
	private Type type;
	
	@Column
	private String deptor; //duznik
	
	@Column
	private String receiver; //primalac
	
	@Column
	private String purposeOfPayment; //svrha placanja
	
	@Column
	private Date currencyDate; //datum valute
	
	@Column
	private Date depositSlipDate; //datum naloga
	
	@Column
	private String billOfDeptor; //racun duznika 18
	
	@Column
	private int modelAssignment; //model zaduzenja 2
	
	@Column
	private String referenceNumberAssignment; // poziv na broj zaduzenja 20
	
	@Column
	private String billOfReceiver; //racun primaoca 18
	
	@Column
	private int modelApproval; //model odobrenja 2
	
	@Column
	private String referenceNumberApproval; //poziv na broj odobrenja 20
	
	@Column
	private Double amount; //iznos
	
	@Column
	private String codeOfCurrency; //sifra valute 3
	
	@Column
	private boolean urgently; //hitno
	
	@Column
	private boolean direction; //smjer provjeriti tip - na stetu, na korist
	
	@ManyToOne
	@JoinColumn(name = "PAYMENT_TYPE_ID")
	private PaymentTypes paymentType;
	
	public DepositSlip(Bill billForClosing,ClosingBill closingBill,String billSuccessor) {
		setType(Type.TRANSFER);
		setDeptor(billForClosing.getClient().getApplicant());
		setPurposeOfPayment("zatvaranje racuna");
		setReceiver("Pravni nasljednik");
		setCurrencyDate(closingBill.getDate());
		setCodeOfCurrency("rsd");
		setBillOfReceiver(billSuccessor);
		setModelApproval(2);
		setReferenceNumberApproval("20");
		setReferenceNumberAssignment("20");
		setBillOfDeptor(billForClosing.getAccountNumber());
		setModelAssignment(2);
		setDepositSlipDate(closingBill.getDate());
		setUrgently(false);
		setDirection(false);
	}
	
	public DepositSlip(){}
	
	public DepositSlip(boolean type) {
		setType(Type.TRANSFER);
		setDeptor("2131312");
		setPurposeOfPayment("zatvaranje racuna");
		setReceiver("Pravni nasljednik");
		setCurrencyDate(new Date(0));
		setCodeOfCurrency("rsd");
		setBillOfReceiver("4566656");
		setModelApproval(2);
		setReferenceNumberApproval("20");
		setReferenceNumberAssignment("20");
		setBillOfDeptor("321312");
		setModelAssignment(2);
		setDepositSlipDate(new Date(0));
		setUrgently(false);
		setDirection(false);
		setAmount(3.33);
	}
		
}
