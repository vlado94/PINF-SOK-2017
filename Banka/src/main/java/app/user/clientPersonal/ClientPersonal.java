package app.user.clientPersonal;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import app.User;
import app.billPersonal.BillPersonal;
import lombok.Data;

@Data
@Entity
public class ClientPersonal extends User{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CLIENT_PERSONAL_ID")
	private Long id;
	
	@OneToMany(mappedBy = "clientPersonal")
	private List<BillPersonal> personalBills;

}
