package app.MT103xml;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import app.Adapter1;
import app.MT102xml.MT102xml;
import app.depositSlip.DepositSlipXml;
import app.depositSlip.HeaderMT102xml;
import lombok.Data;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mt103xml")
@Data
public class MT103xml {

	@XmlTransient
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "HEADER_MT102_ID")
	private Long id;
	
	@XmlElement(required = true)
	private String messageId; //50
    
	@XmlElement(required = true)
	private String debtorBankswiftCode; //8 
	
	@XmlElement(required = true)
	private String debtorBankclearingAccount; //18
	
	@XmlElement(required = true)	
	private String receiverBankswiftCode; //8
	
	@XmlElement(required = true)
	private String receiverBankclearingAccount; //18
	
	@XmlElement
	private String deptor; //duznik Dužnik - nalogodavac String 255
	
	@XmlElement
	private String purposeOfPayment; //svrha placanja Svrha plaćanja String 255
	
	@XmlElement
	private String receiver; //primalac Primalac - poverilac String 255
	
	@XmlJavaTypeAdapter(Adapter1.class)
    @XmlElement(required = true)
	private Date date;
	
	@XmlJavaTypeAdapter(Adapter1.class)
    @XmlElement(required = true)
	private Date currencyDate;
	
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
	
	
	@XmlElement(required = true)
	private BigDecimal amount; //15-2
	
	@XmlElement(required = true)
	private String currencyCode;//3
	
	
	
	 
}
