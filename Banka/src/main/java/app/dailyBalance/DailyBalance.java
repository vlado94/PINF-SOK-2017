package app.dailyBalance;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	
}
