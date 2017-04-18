package app.exchangeRate;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import app.exchangeInCurrency.ExchangeInCurrency;
import lombok.Data;

@Data
@Entity
public class ExchangeRate {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EXCHANGE_RATE_ID")
	private Long id;
	
	@NotBlank
	@Column
	private Date startDate;
	
	@NotBlank
	@Column
	private Date date;
	
	@NotNull
	@Column(length=3)
	private Integer numberOfExchangeRate;
	
	@OneToMany
	@JoinTable(name = "EXCHANGE_CURRENCY", joinColumns = @JoinColumn(name = "EXCHANGE_RATE_ID"), inverseJoinColumns = @JoinColumn(name = "EXCHANGE_IN_CURRENCY_ID"))
	private List<ExchangeInCurrency> exchangeInCurrencies;
}