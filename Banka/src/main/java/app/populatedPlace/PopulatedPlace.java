package app.populatedPlace;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import app.billPersonal.BillPersonal;
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
}
