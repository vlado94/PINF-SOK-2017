package app.dailyBalance;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import app.depositSlip.DepositSlip;
import lombok.Data;

@Data
@Entity
public class DailyBalance {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DAILY_BALANCE_ID")
	private Long id;
	
	@Column
	private Date date;
	
	@Column
	private double previousState;
	
	@Column
	private double trafficAtExpense;
	
	@Column
	private double trafficToBenefit;
	
	@Column
	private double newState;
	/*
	@ManyToOne
	@JoinColumn(name = "BILL_ID")
	private Bill bill;  //not null
	*/
	@OneToMany
	@JoinTable(name = "DAILY_BALANCEL_DEPOSIT_SLIP", joinColumns = @JoinColumn(name = "DB_ID"), inverseJoinColumns = @JoinColumn(name = "DS_ID"))
	private List<DepositSlip> depositSlips;
	
	
}
