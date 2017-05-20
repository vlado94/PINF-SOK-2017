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

import app.bank.Bank;
import app.bank.BankService;
import app.bill.Bill;
import app.bill.BillService;
import app.client.Client;
import app.client.ClientService;
import app.client.Client.TypeOfClient;
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
	private final BankService bankService;
	private HttpSession httpSession;
	private final ClosingBillService closingBillService;
	private final DailyBalanceService dailyBalanceService;
	private final DepositSlipService depositSlipService;
	private final ClientService clientService;
	
	@Autowired
	public BankerController(final HttpSession httpSession,final BankerService bankerService, 
							final BillService billService, final BankService bankService,final ClosingBillService closingBillService,
							final DailyBalanceService dailyBalanceService,final DepositSlipService depositSlipService,final ClientService clientService) {
		this.bankerService = bankerService;
		this.billService = billService;
		this.bankService = bankService;
		this.httpSession = httpSession;
		this.closingBillService = closingBillService;
		this.dailyBalanceService = dailyBalanceService;
		this.depositSlipService = depositSlipService;
		this.clientService = clientService;
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
		DepositSlip depositSlip = new DepositSlip();
		depositSlip.setType(Type.TRANSFER);
		depositSlip.setDeptor(billForClosing.getClient().getApplicant());
		depositSlip.setPurposeOfPayment("zatvaranje racuna");
		depositSlip.setReceiver("Pravni nasljednik");
		depositSlip.setCurrencyDate(closingBill.getDate());
		depositSlip.setCodeOfCurrency("rsd");
		depositSlip.setBillOfReceiver(billSuccessor);
		depositSlip.setModelApproval(2);
		depositSlip.setReferenceNumberApproval("20");
		depositSlip.setReferenceNumberAssignment("20");
		depositSlip.setBillOfDeptor(billForClosing.getAccountNumber());
		depositSlip.setModelAssignment(2);
		depositSlip.setDepositSlipDate(closingBill.getDate());
		depositSlip.setUrgently(false);
		depositSlip.setDirection(false);
		//novac za prenos dobijam iz dnevnog stanja racuna kog zatvaraju 
		List<DailyBalance> dbs = billForClosing.getDailyBalances();
		if(!dbs.isEmpty()){
			DailyBalance db = dbs.get(dbs.size()-1);
			depositSlip.setAmount(db.getNewState());
			System.out.println("ima dnevno stanje");
		}
		//poziva obradu izvoda
		saveDepositSlip(depositSlip);
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
					billForRecieveMoney = defineFirstBill(depositSlip);
				}else if (depositSlip.getType() == Type.PAYOUT) {
					
				}else if (depositSlip.getType() == Type.PAYMENTIN) {
					
				}else if (depositSlip.getType() == Type.PAYMENTOUT) {
					
				}
				if(billForRecieveMoney == null)
					throw new BadRequestException();
				if(billForRecieveMoney.getDailyBalances() == null) {
					billForRecieveMoney.setDailyBalances(new ArrayList<DailyBalance>());
					DailyBalance dailyB = createNewDailyBalance(depositSlip);
					dailyB.getDepositSlips().add(depositSlip);
					dailyB.setPreviousState(dailyB.getNewState());
					dailyB.setNewState(dailyB.getNewState() + depositSlip.getAmount());
					dailyB.setTrafficToBenefit(depositSlip.getAmount());

					dailyB = dailyBalanceService.save(dailyB);
					billForRecieveMoney.getDailyBalances().add(dailyB);				
				}
				else if(billForRecieveMoney.getDailyBalances().size() == 0) {
 					DailyBalance dailyB = createNewDailyBalance(depositSlip);
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
	
	private Bill defineFirstBill(DepositSlip depositSlip) {
		Bill retVal = null;
		String accountNumberForRecive = depositSlip.getBillOfReceiver();
		retVal = billService.findByAccountNumber(accountNumberForRecive);
		
		return retVal;
	}
	
	private DailyBalance createNewDailyBalance(DepositSlip depositSlip) {
		DailyBalance dailyBalance = new DailyBalance();
		dailyBalance.setDate(new Date());
		dailyBalance.setDepositSlips(new ArrayList<DepositSlip>());
		dailyBalance.setNewState(0);
		dailyBalance.setPreviousState(0);
		dailyBalance.setTrafficAtExpense(0);
		dailyBalance.setTrafficToBenefit(0);
		dailyBalance = dailyBalanceService.save(dailyBalance);
		return dailyBalance;
	}
	
	@GetMapping("/findBillsForAllBanks/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<Bill> findBillsForAllBanks(@PathVariable Long id) {
		List<Bill> allBills = billService.findAllCurrentBillsExceptClosingOne(id);
		if(allBills.isEmpty())
			System.out.println(allBills.get(0));
		else 
			System.out.println("PRAZNOOO");
		return allBills;
	}
	
	
}