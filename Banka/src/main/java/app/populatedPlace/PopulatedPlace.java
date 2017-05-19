package app.populatedPlace;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;

import app.country.Country;
import lombok.Data;

@Data
@Entity
public class PopulatedPlace {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "POPULATED_PLACE_ID")
	private Long id;
	
	@Column
	@NotBlank
	private String name;
	
	@Column(length=5)
	@NotBlank
	private String pttCode;
	
	@ManyToOne
	@JoinTable(name = "POPULATED_PLACE_COUNTRY", joinColumns = @JoinColumn(name = "POPULATED_PLACE_ID"), inverseJoinColumns = @JoinColumn(name = "COUNTRY_ID"))
	private Country country;
	
	public void update(PopulatedPlace populatedPlace) {
		setName(populatedPlace.getName());
		setPttCode(populatedPlace.getPttCode());
		setCountry(populatedPlace.getCountry());
	}
	
	
}
