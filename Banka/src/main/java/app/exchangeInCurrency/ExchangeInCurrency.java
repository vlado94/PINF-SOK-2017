package app.exchangeInCurrency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
public class ExchangeInCurrency { //kurs u valuti

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column
	@NotBlank
	private String serialNumber;

	@Column
	@NotBlank
	private Double purchasingRate;
	
	@Column
	@NotBlank
	private Double saleRate;

	@Column
	@NotBlank
	private Double middleRate;
}
