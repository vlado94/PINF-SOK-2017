package app.depositSlip;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.bill.Bill;
import app.bill.BillService;
import app.dailyBalance.DailyBalance;
import app.dailyBalance.DailyBalanceService;
import app.depositSlip.DepositSlip.Type;
import app.user.banker.Banker;

@RestController
@RequestMapping("/depositSlip")
public class DepositSlipController {

	private final DepositSlipService depositSlipService;
	private final DailyBalanceService dailyBalanceService;
	private HttpSession httpSession;
	private final BillService billService;
	
	@Autowired
	public DepositSlipController(final DepositSlipService depositSlipService,final BillService billService,final DailyBalanceService dailyBalanceService,HttpSession httpSession) {
		this.depositSlipService = depositSlipService;
		this.httpSession = httpSession;
		this.dailyBalanceService = dailyBalanceService;
		this.billService = billService;
	}
	
	@GetMapping("/findAllDepositSlips")
	@ResponseStatus(HttpStatus.OK)
	public List<DepositSlip> findAllDepositSlips() {
		return depositSlipService.findAll();
	}
	
	@PostMapping(path = "/saveDepositSlip")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveDepositSlip(@RequestBody DepositSlip depositSlip) {
		try {
			depositSlipService.save(depositSlip);
			int bankCodeBillOfReciver = Integer.parseInt(depositSlip.getBillOfReceiver().substring(0, 3));
			int bankCodeBillOfDeptor = Integer.parseInt(depositSlip.getBillOfDeptor().substring(0, 3));
			int bankCode = ((Banker)httpSession.getAttribute("user")).getBank().getCode();
			if(bankCode == bankCodeBillOfReciver && bankCode == bankCodeBillOfDeptor) {
				Bill billForRecieveMoney = null;
				if(depositSlip.getType() == Type.TRANSFER) {
					billForRecieveMoney = billService.findByAccountNumber(depositSlip.getBillOfReceiver());
				}else if (depositSlip.getType() == Type.PAYOUT) {
					
				}else if (depositSlip.getType() == Type.PAYMENTIN) {
					
				}else if (depositSlip.getType() == Type.PAYMENTOUT) {
					
				}
				if(billForRecieveMoney == null)
					throw new BadRequestException();
				if(billForRecieveMoney.getDailyBalances() == null) {
					billForRecieveMoney.setDailyBalances(new ArrayList<DailyBalance>());
					DailyBalance dailyB = dailyBalanceService.save(new DailyBalance());
					dailyB.getDepositSlips().add(depositSlip);
					dailyB.setPreviousState(dailyB.getNewState());
					dailyB.setNewState(dailyB.getNewState() + depositSlip.getAmount());
					dailyB.setTrafficToBenefit(depositSlip.getAmount());

					dailyB = dailyBalanceService.save(dailyB);
					billForRecieveMoney.getDailyBalances().add(dailyB);				
				}
				else if(billForRecieveMoney.getDailyBalances().size() == 0) {
 					DailyBalance dailyB = dailyBalanceService.save(new DailyBalance());
					dailyB.getDepositSlips().add(depositSlip);
					dailyB.setPreviousState(dailyB.getNewState());
					dailyB.setNewState(dailyB.getNewState() + depositSlip.getAmount());
					dailyB.setTrafficToBenefit(depositSlip.getAmount());

					dailyB = dailyBalanceService.save(dailyB);
					billForRecieveMoney.getDailyBalances().add(dailyB);
				}
				else {
					Date currentTime = new Date();
					DailyBalance dailyB = billForRecieveMoney.getDailyBalances().get(billForRecieveMoney.getDailyBalances().size()-1);
					
					Calendar c1= Calendar.getInstance();
					Calendar c2= Calendar.getInstance();
					c1.setTime(currentTime);
					c2.setTime(dailyB.getDate());
					int yearDiff = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
					int monthDiff = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
					int dayDiff = c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH);
					if(yearDiff == 0 && monthDiff == 0 && dayDiff == 0) {
						dailyB.getDepositSlips().add(depositSlip);
						dailyB.setPreviousState(dailyB.getNewState());
						dailyB.setNewState(dailyB.getNewState() + depositSlip.getAmount());
						dailyB.setTrafficToBenefit(depositSlip.getAmount());
					}
				}
			}
		}
		catch(Exception ex) {
			throw new BadRequestException();
		}
	}
}
