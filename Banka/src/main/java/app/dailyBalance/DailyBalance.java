package app.dailyBalance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

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
	@ManyToMany
	@JoinTable(name = "DAILY_BALANCEL_DEPOSIT_SLIP", joinColumns = @JoinColumn(name = "DB_ID"), inverseJoinColumns = @JoinColumn(name = "DS_ID"))
	private List<DepositSlip> depositSlips;
	
	public DailyBalance() {
		setDate(new Date());
		setDepositSlips(new ArrayList<DepositSlip>());
		setNewState(0);
		setPreviousState(0);
		setTrafficAtExpense(0);
		setTrafficToBenefit(0);
	}
	
	public DailyBalance(DailyBalance oldDailyBalance) {
		setDate(new Date());
		setDepositSlips(new ArrayList<DepositSlip>());
		setNewState(oldDailyBalance.getNewState());
		setPreviousState(oldDailyBalance.getNewState());
		setTrafficAtExpense(0);
		setTrafficToBenefit(0);
	}	
}