package app.depositSlip;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import app.Adapter1;
import lombok.Data;

@Entity
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DepositSlipXml {

	@XmlTransient
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DEPOSIT_SLIP_XML_ID")
	private Long id;
	
	@XmlElement
	private String messageId; //str50
    
	@XmlElement
	private String deptor; //duznik Dužnik - nalogodavac String 255
	
	@XmlElement
	private String purposeOfPayment; //svrha placanja Svrha plaćanja String 255
	
	@XmlElement
	private String receiver; //primalac Primalac - poverilac String 255
	
	@XmlElement
	@XmlJavaTypeAdapter(Adapter1.class)
	private Date depositSlipDate; //datum naloga Datum naloga Date
	
	
	@XmlElement
	private String billOfDeptor; //racun duznika 18 Račun dužnika String 18
	
	@XmlElement
	private int modelAssignment; //model zaduzenja 2 Model zaduženja Number 2
	
	@XmlElement
	private String referenceNumberAssignment; // poziv na broj zaduzenja 20 Poziv na broj zaduženja String 20
	
	@XmlElement
	private String billOfReceiver; //racun primaoca 18 Račun poverioca String 18
	
	@XmlElement
	private int modelApproval; //model odobrenja 2 Model odobrenja Number 2
	
	@XmlElement
	private String referenceNumberApproval; //poziv na broj odobrenja 20 Poziv na broj odobrenja String 20
	
	@XmlElement
	private double amount; //iznos Iznos Decimal 15,2
	
	@XmlElement
	private String codeOfCurrency; //sifra valute 3 	Šifra valute
	
	
	
	
	
		
}
