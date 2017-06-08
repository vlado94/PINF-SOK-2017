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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import app.Adapter1;
import app.bill.Bill;
import app.closingBill.ClosingBill;
import app.enums.Status;
import app.enums.Type;
import app.paymentTypes.PaymentTypes;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "depositSlip")
@Data
@Entity
public class DepositSlip {

	@XmlTransient
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DEPOSIT_SLIP_ID")
	private Long id;	

	@XmlElement(name = "type", required = true)
	@Column
    @Enumerated(EnumType.STRING)
	private Type type;
	
	@XmlElement
	@Column
	private String deptor; //duznik
	
	@XmlElement
	@Column
	private String receiver; //primalac
	
	@XmlElement
	@Column
	private String purposeOfPayment; //svrha placanja
	
	@XmlJavaTypeAdapter(Adapter1.class)
	@XmlElement
	@Column
	private Date currencyDate; //datum valute
	
	@XmlJavaTypeAdapter(Adapter1.class)
	@XmlElement
	@Column
	private Date depositSlipDate; //datum naloga
	
	@XmlElement
	@Column
	private String billOfDeptor; //racun duznika 18
	
	@XmlElement
	@Column
	private int modelAssignment; //model zaduzenja 2
	
	@XmlElement
	@Column
	private String referenceNumberAssignment; // poziv na broj zaduzenja 20
	
	@XmlElement
	@Column
	private String billOfReceiver; //racun primaoca 18
	
	@XmlElement
	@Column
	private int modelApproval; //model odobrenja 2
	
	@XmlElement
	@Column
	private String referenceNumberApproval; //poziv na broj odobrenja 20
	
	@XmlElement
	@Column
	private Double amount; //iznos
	
	@XmlElement
	@Column
	private String codeOfCurrency; //sifra valute 3
	
	@XmlElement
	@Column
	private boolean urgently; //hitno
	
	
	@XmlTransient
	@ManyToOne
	@JoinColumn(name = "PAYMENT_TYPE_ID")
	private PaymentTypes paymentType;
	
	
	@XmlTransient
	@Column
	private Status status; 	
	
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

		setAmount(3.33);
	}
		
}
