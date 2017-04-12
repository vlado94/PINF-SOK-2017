package app.paymentTypes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import app.country.Country;
import lombok.Data;

@Data
@Entity
public class PaymentTypes {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PAYMENT_TYPES_ID")
	private Long id;
	
	@Column
	private String name;
}
