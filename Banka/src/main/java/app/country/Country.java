package app.country;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	
	@ManyToOne
	@JoinColumn(name = "CURRENCY_ID")
	private Currency currency;	
	
	public void update(Country country) {
		setCode(country.getCode());
		setName(country.getName());
	}
}
