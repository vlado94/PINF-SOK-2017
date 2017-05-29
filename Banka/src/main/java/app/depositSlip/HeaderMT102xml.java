package app.depositSlip;

import java.math.BigDecimal;
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
public class HeaderMT102xml {

	
		@XmlTransient
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "HEADER_MT102_ID")
		private Long id;
		
		@XmlElement(required = true)
		private String messageId; //50
	    
		@XmlElement(required = false)
		private String debtorBankswiftCode; //8 
		
		@XmlElement(required = false)
		private String debtorBankclearingAccount; //18
		
		@XmlElement(required = false)	
		private String receiverBankswiftCode; //8
		
		@XmlElement(required = false)
		private String receiverBankclearingAccount; //18
		
		@XmlElement(required = true)
		private BigDecimal amount; //15-2
		
		@XmlElement(required = true)
		private String currencyCode;//3
		
		@XmlJavaTypeAdapter(Adapter1.class)
	    @XmlElement(required = true)
		private Date currencyDate;
		
		@XmlJavaTypeAdapter(Adapter1.class)
	    @XmlElement(required = true)
		private Date date; 
}
