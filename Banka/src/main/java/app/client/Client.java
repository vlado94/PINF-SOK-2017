package app.client;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
@MappedSuperclass
public class Client {
	
	
	@Id
	@NotBlank
	@Column(unique = true)
	private String id;
	
	@NotBlank
	@Column
	private String applicant;
	
	@NotBlank
	@Column
	private String jmbg;
	
	@Column
	@NotBlank
	private String address;
	
	@Column
	private Integer phone;
	
	@Column
	private Integer fax;
	
	@Email
	@NotBlank
	@Column(unique = true)
	private String mail;
	
	@Column
	private String deliveryAddress;
	
	@Column
	private boolean deliveryByMail;
	
	@Column
	@NotBlank
	private String type;
	
	
}
