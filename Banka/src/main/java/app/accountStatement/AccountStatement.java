package app.accountStatement;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import app.Adapter1;
import app.depositSlip.DepositSlip;
import lombok.Data;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "accountStatement")
@Data
public class AccountStatement {

	@XmlTransient
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ACCOUNT_STATEMENT_ID")
	private Long id;
	
	@XmlJavaTypeAdapter(Adapter1.class)
    @XmlElement(required = true)
	private Date fromDate; //50
	
	
	@XmlJavaTypeAdapter(Adapter1.class)
    @XmlElement(required = true)
	private Date toDate; //50
	
	@OneToMany
	@JoinTable(name = "ACCOUNT_STATEMENT_DEPOSIT_SLIP", joinColumns = @JoinColumn(name = "AS_ID"), inverseJoinColumns = @JoinColumn(name = "DS_ID"))
    @XmlElement(required = true)
    protected List<DepositSlip> depositSlip = new ArrayList<DepositSlip>();
}
