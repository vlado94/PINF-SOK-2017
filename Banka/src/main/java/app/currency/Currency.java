package app.currency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
public class Currency { //Valuta

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CURRENCY_ID")
	private Long id;
	
	@Column(length=3)
	@NotBlank
	private String code;
	
	@Column
	@NotBlank
	private String name;
	
	/*@Jsonig
	@OneToMany
	@JoinTable(name = "CURRENCY_COUNTRY", joinColumns = @JoinColumn(name = "CURRENCY_ID"), inverseJoinColumns = @JoinColumn(name = "COUNTRY_ID"))
	private List<Country> countries;*/
	
}
