package app.user.client;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import app.User;
import app.bill.Bill;
import lombok.Data;

@Data
@Entity
public class Client extends User{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CLIENT_ID")
	private Long id;
	
	//@OneToMany
	//@JoinTable(name = "CLIENT_BILLS", joinColumns = @JoinColumn(name = "CLIENT_ID"), inverseJoinColumns = @JoinColumn(name = "BILL_ID"))
	@OneToMany(mappedBy = "client")
	private List<Bill> bills;

}
