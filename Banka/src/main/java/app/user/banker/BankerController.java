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

import app.bank.BankService;
import app.bill.Bill;
import app.bill.BillService;
import app.closingBill.ClosingBill;
import app.closingBill.ClosingBillService;
import app.dailyBalance.DailyBalance;
import app.dailyBalance.DailyBalanceService;
import app.depositSlip.DepositSlip;
import app.depositSlip.DepositSlip.Type;
import app.depositSlip.DepositSlipService;
import app.interbankTransfer.InterbankTransfer;
import app.interbankTransfer.InterbankTransferService;

@RestController
@RequestMapping("/banker")
public class BankerController {
	
	private final BankerService bankerService;
	private final BankService bankService;
	private final BillService billService;
	private HttpSession httpSession;
	private final ClosingBillService closingBillService;
	private final DailyBalanceService dailyBalanceService;
	private final DepositSlipService depositSlipService;
	private final InterbankTransferService interbankTransferService;
	
	@Autowired
	public BankerController(final HttpSession httpSession,final BankerService bankerService, 
							final BillService billService, final ClosingBillService closingBillService,final InterbankTransferService interbankTransferService,
							final DailyBalanceService dailyBalanceService,final DepositSlipService depositSlipService,final BankService bankService) {
		this.bankerService = bankerService;
		this.billService = billService;
		this.bankService = bankService;
		this.httpSession = httpSession;
		this.closingBillService = closingBillService;
		this.dailyBalanceService = dailyBalanceService;
		this.depositSlipService = depositSlipService;
		this.interbankTransferService = interbankTransferService;
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
			depositSlip = depositSlipService.save(depositSlip);
			if(depositSlip.getType() == Type.TRANSFER) {
				checkBillInBank(billService.findByAccountNumber(depositSlip.getBillOfDeptor()).getId());
				DepositSlipTransfer(depositSlip);				
			}
			else if (depositSlip.getType() == Type.PAYOUT) {
				DepositSlipPayOut(depositSlip);
			}else if (depositSlip.getType() == Type.PAYMENTIN) {
				DepositSlipPaymentIn(depositSlip);				
				
			}else if (depositSlip.getType() == Type.PAYMENTOUT) {
				checkBillInBank(billService.findByAccountNumber(depositSlip.getBillOfReceiver()).getId());
				DepositSlipPaymentOut(depositSlip);								
			}
		
	}
	
	private void DepositSlipTransfer(DepositSlip depositSlip) {
		int bankCodeBillOfReciver = Integer.parseInt(depositSlip.getBillOfReceiver().substring(0, 3));
		int bankCodeBillOfDeptor = Integer.parseInt(depositSlip.getBillOfDeptor().substring(0, 3));
		int bankCode = ((Banker)httpSession.getAttribute("user")).getBank().getCode();

		//da bi se izvrsila transakcija odmah klijenti moraju biti iz iste banke
		resolveAllForDeptor(depositSlip);
		if(bankCode == bankCodeBillOfReciver) {
			resolveAllForReciver(depositSlip);
		}
		else {
			if(depositSlip.getAmount() > 250000 || depositSlip.isUrgently()) {
				//rtgs
				InterbankTransfer interbankTransfer = new InterbankTransfer(true);
				interbankTransfer.getDepositSlips().add(depositSlip);	
				interbankTransfer.setBankReciever(bankService.findOneByCode(bankCodeBillOfReciver));
				interbankTransfer.setBankSender(bankService.findOneByCode(bankCodeBillOfDeptor));
				interbankTransferService.save(interbankTransfer);
			}
			else {
				InterbankTransfer interbankTransfer = findInterBankTransfer(bankCodeBillOfReciver,bankCodeBillOfDeptor);
				interbankTransfer.getDepositSlips().add(depositSlip);	
				interbankTransfer.setAmount(interbankTransfer.getAmount() + depositSlip.getAmount());
				interbankTransferService.save(interbankTransfer);
			}
		}		
	}
	
	private InterbankTransfer findInterBankTransfer(Integer bankCodeBillOfReciver,Integer bankCodeBillOfDeptor) {
		List<InterbankTransfer> list = interbankTransferService.findAll();
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getBankReciever().getCode().equals(bankCodeBillOfReciver) && list.get(i).getDateTime() == null) {
				return list.get(i);
			}
		}
		InterbankTransfer retVal = new InterbankTransfer(false);
		retVal.setBankReciever(bankService.findOneByCode(bankCodeBillOfReciver));
		retVal.setBankSender(bankService.findOneByCode(bankCodeBillOfDeptor));
		return retVal;
	}
	
	private void DepositSlipPayOut(DepositSlip depositSlip) {
		resolveAllForDeptor(depositSlip);
	}
	private void DepositSlipPaymentIn(DepositSlip depositSlip) {
		int bankCodeBillOfReciver = Integer.parseInt(depositSlip.getBillOfReceiver().substring(0, 3));
		int bankCodeBillOfDeptor = Integer.parseInt(depositSlip.getBillOfDeptor().substring(0, 3));
		int bankCode = ((Banker)httpSession.getAttribute("user")).getBank().getCode();

		//da bi se izvrsila transakcija odmah klijenti moraju biti iz iste banke
		if(bankCode == bankCodeBillOfReciver && bankCode == bankCodeBillOfDeptor) {
			resolveAllForDeptor(depositSlip);
			resolveAllForReciver(depositSlip);
		}
		else {
			System.out.println("nije ista banka");
			//nalog ide u clearing ili rtgs
		}
	}
	private void DepositSlipPaymentOut(DepositSlip depositSlip) {
		resolveAllForReciver(depositSlip);
	}
	
	private void checkBillInBank(long id) {
		List<Bill> billsInBank = bankService.findOne(((Banker)httpSession.getAttribute("user")).getBank().getId()).getBills();
		for(int i=0;i<billsInBank.size();i++) 
			if(id == billsInBank.get(i).getId())
				return;
		throw new BadRequestException();	
	}
	
	
	private boolean compareDates(Date dBDate) {
		Date today = new Date();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(today);
		c2.setTime(dBDate);
		if(c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)){
		    return true;
		}
		return false;
	}
	
	private DailyBalance createNewDailyBalanceForDeptor(DepositSlip depositSlip) {
		DailyBalance dailyBalance = new DailyBalance();
		dailyBalance.setNewState(depositSlip.getAmount());
		dailyBalance.setTrafficAtExpense(depositSlip.getAmount());
		
		dailyBalance.getDepositSlips().add(depositSlip);
		return dailyBalance;		
	}
	
	
	private void resolveAllForDeptor(DepositSlip depositSlip) {
		Bill billDeptor = billService.findByAccountNumber(depositSlip.getBillOfDeptor());
		List<DailyBalance> dailyBalanceForDeptor = billDeptor.getDailyBalances();
		
		if(dailyBalanceForDeptor == null) {
			dailyBalanceForDeptor = new ArrayList<DailyBalance>();
		}
		
		if(dailyBalanceForDeptor.size() == 0) {
			DailyBalance dailyBalance = createNewDailyBalanceForDeptor(depositSlip);
			
			dailyBalance = dailyBalanceService.save(dailyBalance);
			dailyBalanceForDeptor.add(dailyBalance);
		}
		
		else if(!compareDates(dailyBalanceForDeptor.get(dailyBalanceForDeptor.size()-1).getDate())) {
			DailyBalance dailyBalance = createNewDailyBalanceForDeptor(depositSlip);
			
			DailyBalance lastDailyBalance = dailyBalanceForDeptor.get(dailyBalanceForDeptor.size()-1);
			dailyBalance.setPreviousState(lastDailyBalance.getNewState());
			dailyBalance.setNewState(lastDailyBalance.getNewState() - depositSlip.getAmount());
			
			dailyBalance = dailyBalanceService.save(dailyBalance);
			dailyBalanceForDeptor.add(dailyBalance);
		}
		else {
			DailyBalance currentDailyBalance = dailyBalanceForDeptor.get(dailyBalanceForDeptor.size()-1);
			
			currentDailyBalance.setNewState(currentDailyBalance.getNewState() - depositSlip.getAmount());
			currentDailyBalance.setTrafficAtExpense(currentDailyBalance.getTrafficAtExpense() + depositSlip.getAmount());				
			
			currentDailyBalance.getDepositSlips().add(depositSlip);
			dailyBalanceService.save(currentDailyBalance);				
		}
		billService.save(billDeptor);
	}
	
	private void resolveAllForReciver(DepositSlip depositSlip) {
		Bill billReciver = billService.findByAccountNumber(depositSlip.getBillOfReceiver());
		List<DailyBalance> dailyBalanceForReceiver = billReciver.getDailyBalances();
		
		if(dailyBalanceForReceiver == null) {
			dailyBalanceForReceiver = new ArrayList<DailyBalance>();
		}
		
		if(dailyBalanceForReceiver.size() == 0) {
			DailyBalance dailyBalance = new DailyBalance();
			
			dailyBalance.setNewState(depositSlip.getAmount());
			dailyBalance.setTrafficToBenefit(depositSlip.getAmount());

			dailyBalance.getDepositSlips().add(depositSlip);
			dailyBalance = dailyBalanceService.save(dailyBalance);
		
			dailyBalanceForReceiver.add(dailyBalance);
		}
		else if(!compareDates(dailyBalanceForReceiver.get(dailyBalanceForReceiver.size()-1).getDate())) {
			//create new daily balnce
			DailyBalance lastDailyBalance = dailyBalanceForReceiver.get(dailyBalanceForReceiver.size()-1);
			DailyBalance dailyBalance = new DailyBalance();
			dailyBalance.setPreviousState(lastDailyBalance.getNewState());
			dailyBalance.setNewState(lastDailyBalance.getNewState() + depositSlip.getAmount());
			dailyBalance.setTrafficToBenefit(depositSlip.getAmount());				
			
			dailyBalance.getDepositSlips().add(depositSlip);				
			dailyBalance = dailyBalanceService.save(dailyBalance);
			
			dailyBalanceForReceiver.add(dailyBalance);
		}
		else {
			DailyBalance currentDailyBalance = dailyBalanceForReceiver.get(dailyBalanceForReceiver.size()-1);
			currentDailyBalance.setNewState(currentDailyBalance.getNewState() + depositSlip.getAmount());
			currentDailyBalance.setTrafficToBenefit(currentDailyBalance.getTrafficToBenefit() + depositSlip.getAmount());				
			
			currentDailyBalance.getDepositSlips().add(depositSlip);
			currentDailyBalance = dailyBalanceService.save(currentDailyBalance);
			
		}
		billService.save(billReciver);	
	}
}