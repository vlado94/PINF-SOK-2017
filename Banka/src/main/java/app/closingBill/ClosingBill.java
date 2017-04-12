package app.closingBill;

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
public class ClosingBill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CLOSING_BILL_ID")
	private Long id;
	
	@Column
	@NotBlank
	private Date date;
	
	@Column
	private String billSuccessor;
	
	
	
}
