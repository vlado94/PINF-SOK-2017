package app.exchangeRate;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import app.bank.Bank;
import app.billPersonal.BillPersonal;
import lombok.Data;

@Data
@Entity
public class ExchangeRate {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EXCHANGERATE_ID")
	private Long id;
}
