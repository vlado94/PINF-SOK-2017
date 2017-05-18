package app.client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;

import app.codeBookActivities.CodeBookActivities;
import lombok.Data;


@Data
@Entity
public class Client {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true)
	protected Long id;
	
	@NotBlank
	@Column
	protected String applicant;
	
	
	@Column(nullable = false)
	protected Integer jmbg; //not null
	
	@NotBlank
	@Column
	protected String address;
	
	@Column
	protected Integer phone;
	
	@Column
	protected Integer fax;
	
	//@Email
	//@NotBlank
	@Column(unique = true)
	protected String mail;
	
	@Column
	protected String deliveryAddress;
	
	@Column
	protected boolean deliveryByMail;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	protected TypeOfClient type; //not null
	
	
	@Column
	private String shortName;
	
	@Column
	private Integer pib; //(length=9) not null
	
	@Column
	private Integer mib; //(length=8) not null
	
	
	@Column
	private String taxAuthority; //naziv poreskog organa
	
	@Column
	private String responsiblePerson;
	
	@ManyToOne
	@JoinColumn(name = "CODE_BOOK_ACTIVITIES_ID")
	private CodeBookActivities codeBookActivities;

	
	public enum TypeOfClient {
	   PRAVNO,
	   FIZICKO
	}
	
	public void update(Client client) {
		setApplicant(client.getApplicant());
		setJmbg(client.getJmbg());
		setAddress(client.getAddress());
		setPhone(client.getPhone());
		setFax(client.getFax());
		setMail(client.getMail());
		setDeliveryAddress(client.getDeliveryAddress());
		setDeliveryByMail(client.isDeliveryByMail());
		setShortName(client.getShortName());
		setPib(client.getPib());
		setMib(client.getMib());
		setTaxAuthority(client.getTaxAuthority());
		setResponsiblePerson(client.getResponsiblePerson());
		setCodeBookActivities(client.getCodeBookActivities());
	}
}
