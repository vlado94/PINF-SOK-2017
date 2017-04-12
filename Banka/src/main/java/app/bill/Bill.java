package app.bill;

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
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BILL_ID")
	private Long id;
	
	@NotBlank
	@Column
	private String accountNumber; //18
	
	@NotBlank
	@Column
	private boolean status;
	
	@NotBlank
	@Column
	private Date date;
	
	
}
