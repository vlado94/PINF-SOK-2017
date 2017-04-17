package app.country;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

import app.currency.Currency;
import lombok.Data;

@Data
@Entity
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "COUNTRY_ID")
	private Long id;
	
	@Column//(length=3)
	@NotBlank
	private String code;
	
	@Column 
	@NotBlank
	private String name;
	
	/*@ManyToOne
	private Currency currency;
	*/
	
	@OneToMany
	@JoinTable(name = "COUNTRY_CURRENCIES", joinColumns = @JoinColumn(name = "COUNTRY_ID"), inverseJoinColumns = @JoinColumn(name = "CURRENCY_ID"))
	private List<Currency> currencies;
	
	
}
