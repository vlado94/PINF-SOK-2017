package app.interbankTransfer;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
public class InterbankTransfer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "INTERBANK_TRANSFER_ID")
	private Long id;
	
	
	@Column
	@NotBlank
	private String typeOfMessage;
	
	@Column
	@NotBlank
	private Date date;
	
	@Column
	private double amount;
}
