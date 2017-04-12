package app.user.banker;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import app.bank.Bank;
import app.user.User;
import lombok.Data;

@Data
@Entity
public class Banker extends User{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BANKER_ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "BANK_ID")
	private Bank bank;

	public void setAttributes (Banker banker) {
		this.setFirstname(banker.getFirstname());
		this.setLastname(banker.getLastname());
		this.setMail(banker.getMail());
		this.setPassword(banker.getPassword());
	}
}
