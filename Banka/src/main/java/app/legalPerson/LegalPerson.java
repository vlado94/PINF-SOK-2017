package app.legalPerson;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import app.client.Client;
import lombok.Data;

@Data
@Entity
public class LegalPerson extends Client {
	
	@Column
	@NotBlank
	private String shortName;
	
	@Column(length=9)
	@NotBlank
	private int pib;
	
	@Column(length=8)
	@NotBlank
	private int mib;
	
	@Column
	@NotBlank
	private String taxAuthority; //naziv poreskog organa
	
	@Column
	@NotBlank
	private String responsiblePerson;
	

}
