package app.exchangeInCurrency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import app.currency.Currency;
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
	@NotNull
	private Double purchasingRate;
	
	@Column
	@NotNull
	private Double saleRate;

	@Column
	@NotNull
	private Double middleRate;
	
	@ManyToOne
	@JoinColumn(name = "CURRENCY_ID")
	private Currency currency;
}
