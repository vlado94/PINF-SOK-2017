package app.exchangeInCurrency;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

import app.bank.Bank;
import app.bill.Bill;
import app.exchangeRate.ExchangeRate;
import lombok.Data;

@Data
@Entity
public class ExchangeInCurrency {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column
	@NotBlank
	private String serialNumber;

	@Column
	@NotBlank
	private double purchasingRate;
	
	@Column
	@NotBlank
	private double saleRate;

	@Column
	@NotBlank
	private double middleRate;
	
	
}
