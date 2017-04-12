package app.user.banker;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.user.*;
import app.bank.Bank;
import lombok.Data;

@Data
@Entity
public class Banker extends User{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BANKER_ID")
	private Long id;
	
	/*@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "BANK_ID")
	private Bank bank;*/

}
