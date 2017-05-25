package app.MT102xml;

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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import app.depositSlip.DepositSlipXml;
import app.depositSlip.HeaderMT102xml;
import lombok.Data;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "mt102xml")
@Data
public class MT102xml {

	@XmlTransient
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MT102_ID")
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name = "HEADER_MT102_ID")
    @XmlElement(required = true)
    protected HeaderMT102xml headerMT102xml;
    
    
	@OneToMany
	@JoinTable(name = "MT102_DEPOSIT_SLIP", joinColumns = @JoinColumn(name = "MT102_ID"), inverseJoinColumns = @JoinColumn(name = "DEPOSIT_SLIP_ID"))
    @XmlElement(required = true)
    protected List<DepositSlipXml> depositSlipXml = new ArrayList<DepositSlipXml>();

}
