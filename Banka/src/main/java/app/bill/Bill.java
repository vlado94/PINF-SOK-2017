package app.bill;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import app.client.Client;
import lombok.Data;

@Data
@Entity
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BILL_ID")
	private Long id;
	
	@NotBlank
	@Column
	private String accountNumber; //18
	
	@NotNull
	@Column
	private boolean status;
	
	@NotNull
	@Column
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "CLIENT_ID")
	private Client client;  //not null
	
}
