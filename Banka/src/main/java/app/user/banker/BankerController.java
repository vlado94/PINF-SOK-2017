package app.user.banker;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.bill.Bill;
import app.bill.BillService;
import app.closingBill.ClosingBill;
import app.closingBill.ClosingBillService;
import app.dailyBalance.DailyBalance;
import app.dailyBalance.DailyBalanceService;
import app.depositSlip.DepositSlip;
import app.depositSlip.DepositSlip.Type;
import app.depositSlip.DepositSlipService;

@RestController
@RequestMapping("/banker")
public class BankerController {
	
	private final BankerService bankerService;
	private final BillService billService;
	private HttpSession httpSession;
	private final ClosingBillService closingBillService;
	private final DailyBalanceService dailyBalanceService;
	private final DepositSlipService depositSlipService;
	
	@Autowired
	public BankerController(final HttpSession httpSession,final BankerService bankerService, 
							final BillService billService, final ClosingBillService closingBillService,
							final DailyBalanceService dailyBalanceService,final DepositSlipService depositSlipService) {
		this.bankerService = bankerService;
		this.billService = billService;
		this.httpSession = httpSession;
		this.closingBillService = closingBillService;
		this.dailyBalanceService = dailyBalanceService;
		this.depositSlipService = depositSlipService;
	}
	
	@GetMapping("/checkRights")
	@ResponseStatus(HttpStatus.OK)
	public Banker checkRights() throws AuthenticationException {
		try {
			Banker banker = bankerService.findOneById(((Banker) httpSession.getAttribute("user")).getId());
			return banker;
		} catch (Exception e) {
			throw new AuthenticationException("Forbidden.");
		}
	}
	
	@PutMapping("/updateProfile/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Banker update(@PathVariable Long id,@RequestBody Banker banker) {
		Banker bankerForEdit = bankerService.findOneById(id);
		if(bankerForEdit != null) {
			bankerForEdit.setAttributes(banker);
			banker = bankerService.save(bankerForEdit);
			return banker;		
		}
		else {
			throw new NotFoundException();
		}
	}	
	
	@PostMapping(path = "/closeBill")
	@ResponseStatus(HttpStatus.CREATED)
	public ClosingBill closeBill(@Valid @RequestBody ClosingBill closingBill) {
		Bill billForClosing = closingBill.getBill();
		String billSuccessor = closingBill.getBillSuccessor();
		DepositSlip depositSlip = new DepositSlip(billForClosing,closingBill,billSuccessor);
		//novac za prenos dobijam iz dnevnog stanja racuna kog zatvaraju 
		List<DailyBalance> dbs = billForClosing.getDailyBalances();
		if(!dbs.isEmpty()){
			DailyBalance db = dbs.get(dbs.size()-1);
			depositSlip.setAmount(db.getNewState());
		}
		//poziva obradu izvoda
		depositSlipService.save(depositSlip);
		closingBill.setDepositSlip(depositSlip);
		ClosingBill savedClosingBill = closingBillService.save(closingBill);
		if(savedClosingBill != null){//uspjesno zatvoren racuna
			billForClosing.setStatus(false);
			billService.save(billForClosing);
			return savedClosingBill;
		}else{
			return null;
		}
	}	
	
	
	//kopija!!
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