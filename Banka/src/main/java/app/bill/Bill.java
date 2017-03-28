package app.bill;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.user.client.Client;
import lombok.Data;

@Data
@Entity
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BILL_ID")
	private Long id;
	
	@NotBlank
	@Column(unique = true)
	private String billNumber;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "CLIENT_ID")
	private Client client;
	
}
