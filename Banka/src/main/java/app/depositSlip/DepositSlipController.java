package app.depositSlip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import app.MT103xml.MT103xml;
import app.accountStatement.AccountStatement;
import app.bank.BankService;
import app.bill.Bill;
import app.bill.BillService;
import app.closingBill.ClosingBill;
import app.closingBill.ClosingBillService;
import app.dailyBalance.DailyBalance;
import app.dailyBalance.DailyBalanceService;
import app.enums.Status;
import app.enums.Type;
import app.interbankTransfer.InterbankTransfer;
import app.interbankTransfer.InterbankTransferService;
import app.user.banker.Banker;

@RestController
@RequestMapping("/depositSlip")
public class DepositSlipController {

	private final DepositSlipService depositSlipService;
	private final DailyBalanceService dailyBalanceService;
	private HttpSession httpSession;
	private final BillService billService;
	private final BankService bankService;
	private final InterbankTransferService interbankTransferService;
	private final ClosingBillService closingBillService;

	
	@Autowired
	public DepositSlipController(final InterbankTransferService interbankTransferService,final BankService bankService, final DepositSlipService depositSlipService,final BillService billService,final DailyBalanceService dailyBalanceService,final ClosingBillService closingBillService,HttpSession httpSession) {
		this.depositSlipService = depositSlipService;
		this.httpSession = httpSession;
		this.dailyBalanceService = dailyBalanceService;
		this.billService = billService;
		this.bankService = bankService;
		this.closingBillService = closingBillService;
		this.interbankTransferService = interbankTransferService;
	}
	
	@GetMapping("/findAllDepositSlips")
	@ResponseStatus(HttpStatus.OK)
	public List<DepositSlip> findAllDepositSlips() {
		return depositSlipService.findAll();
	}
	
	@GetMapping("/findAllDepositSlipsForBank")
	@ResponseStatus(HttpStatus.OK)
	public List<DepositSlip> findAllDepositSlipsForBank() {
		int bankCode = ((Banker)httpSession.getAttribute("user")).getBank().getCode();
		
		List<DepositSlip> allDepositSlips = depositSlipService.findAll();
		List<DepositSlip> depositSlipsForBank = new ArrayList<DepositSlip>();
		
		for(int i = 0; i <allDepositSlips.size(); i++){
			if(allDepositSlips.get(i).getBillOfDeptor() != null && allDepositSlips.get(i).getBillOfDeptor().substring(0, 3).equals(String.valueOf(bankCode))){
				depositSlipsForBank.add(allDepositSlips.get(i));
			}
			if(allDepositSlips.get(i).getType() == Type.PAYMENTOUT && allDepositSlips.get(i).getBillOfReceiver().substring(0, 3).equals(String.valueOf(bankCode))){
				depositSlipsForBank.add(allDepositSlips.get(i));
			}
		}
		return depositSlipsForBank;
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
	
	
	@PostMapping(path = "/saveDepositSlip")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveDepositSlip(@RequestBody DepositSlip depositSlip) {
		depositSlip.setStatus(Status.UNPROCESSED);
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
			long id = billService.findByAccountNumber(depositSlip.getBillOfReceiver()).getId();
			checkBillInBank(id);
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
			String currencyCode = ((Banker)httpSession.getAttribute("user")).getBank().getCurrencyCode();
			if(depositSlip.getAmount() > 250000 || depositSlip.isUrgently()) {
				//rtgs
				InterbankTransfer interbankTransfer = new InterbankTransfer(true,currencyCode);
				interbankTransfer.getDepositSlips().add(depositSlip);	
				interbankTransfer.setBankReciever(bankService.findOneByCode(bankCodeBillOfReciver));
				interbankTransfer.setBankSender(bankService.findOneByCode(bankCodeBillOfDeptor));
				interbankTransferService.save(interbankTransfer);
				
				MT103xml mt103 = makeMT103Message(interbankTransfer);
				depositSlip.setStatus(Status.PROCESSED);
				depositSlipService.save(depositSlip);
				exportMT103Message(mt103);
			}
			else {
				InterbankTransfer interbankTransfer = findInterBankTransfer(bankCodeBillOfReciver,bankCodeBillOfDeptor);
				interbankTransfer.getDepositSlips().add(depositSlip);	
				interbankTransfer.setAmount(interbankTransfer.getAmount() + depositSlip.getAmount());
				interbankTransferService.save(interbankTransfer);
			}
		}		
	}

	@PostMapping("/searchAll")
	@ResponseStatus(HttpStatus.CREATED)
	public List<DepositSlip> searchDepositSlipsProcessed(@RequestBody DepositSlip depositSlip) {
		Double amount = depositSlip.getAmount();
		Type type = depositSlip.getType();
		String receiver = depositSlip.getBillOfReceiver();
		if(receiver==null){
			receiver="%";
		}else{
			receiver="%"+depositSlip.getBillOfReceiver()+"%";
		}
		String deptor = depositSlip.getBillOfDeptor();
		if(deptor==null){
			deptor="%";
		}else{
			deptor="%"+depositSlip.getBillOfDeptor()+"%";
		}

		if(type==Type.PAYMENTOUT){
			if(amount==null){
				if(depositSlip.getDepositSlipDate()==null){
					return depositSlipService.findByTypeAndBillOfReceiverLike(type, receiver);
				}else{
					return depositSlipService.findByDepositSlipDateAndTypeAndBillOfReceiverLike(depositSlip.getDepositSlipDate(), type, receiver);
				}
			}else{
				if(depositSlip.getDepositSlipDate()==null){
					return depositSlipService.findByTypeAndBillOfReceiverLikeAndAmount(type, receiver, amount);
				}else{
					return depositSlipService.findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndAmount(depositSlip.getDepositSlipDate(), type, receiver, amount);
				}
			}
		}else if(type==Type.PAYOUT){
			if(amount==null){
				if(depositSlip.getDepositSlipDate()==null){
					return depositSlipService.findByTypeAndBillOfDeptorLike(type, deptor);
				}else{
					return depositSlipService.findByDepositSlipDateAndTypeAndBillOfDeptorLike(depositSlip.getDepositSlipDate(), type, deptor);
				}
			}else{
				if(depositSlip.getDepositSlipDate()==null){
					return depositSlipService.findByTypeAndBillOfDeptorLikeAndAmount(type, deptor, amount);
				}else{
					return depositSlipService.findByDepositSlipDateAndTypeAndBillOfDeptorLikeAndAmount(depositSlip.getDepositSlipDate(), type, deptor, amount);
				}
			}
		}else if(type==Type.PAYMENTIN || type==Type.TRANSFER){
			if(amount==null){
				if(depositSlip.getDepositSlipDate()==null){
					return depositSlipService.findByTypeAndBillOfReceiverLikeAndBillOfDeptorLike(type, receiver, deptor);
				}else{
					return depositSlipService.findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLike(depositSlip.getDepositSlipDate(), type, receiver, deptor);
				}
			}else{
				if(depositSlip.getDepositSlipDate()==null){
					return depositSlipService.findByTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(type, receiver, deptor, amount);
				}else{
					return depositSlipService.findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(depositSlip.getDepositSlipDate(), type, receiver, deptor, amount);
				}
			}
		}else{
			if(amount==null){
				if(depositSlip.getDepositSlipDate()==null){
					return depositSlipService.findByBillOfReceiverLikeAndBillOfDeptorLike(receiver, deptor);
				}else{
					return depositSlipService.findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLike(depositSlip.getDepositSlipDate(), receiver, deptor);
				}
			}
		}
		return depositSlipService.findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmount(depositSlip.getDepositSlipDate(), receiver, deptor, amount);
	}
	@PostMapping("/search")
	@ResponseStatus(HttpStatus.CREATED)
	public List<DepositSlip> searchDepositSlips(@RequestBody DepositSlip depositSlip) {
		Double amount = depositSlip.getAmount();
		Type type = depositSlip.getType();
		String receiver = depositSlip.getBillOfReceiver();
		if(receiver==null){
			receiver="%";
		}else{
			receiver="%"+depositSlip.getBillOfReceiver()+"%";
		}
		String deptor = depositSlip.getBillOfDeptor();
		if(deptor==null){
			deptor="%";
		}else{
			deptor="%"+depositSlip.getBillOfDeptor()+"%";
		}

		if(type==Type.PAYMENTOUT){
			if(amount==null){
				if(depositSlip.getDepositSlipDate()==null){
					return depositSlipService.findByTypeAndBillOfReceiverLikeAndStatus(type, receiver,Status.UNPROCESSED);
				}else{
					return depositSlipService.findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndStatus(depositSlip.getDepositSlipDate(), type, receiver,Status.UNPROCESSED);
				}
			}else{
				if(depositSlip.getDepositSlipDate()==null){
					return depositSlipService.findByTypeAndBillOfReceiverLikeAndAmountAndStatus(type, receiver, amount,Status.UNPROCESSED);
				}else{
					return depositSlipService.findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndAmountAndStatus(depositSlip.getDepositSlipDate(), type, receiver, amount,Status.UNPROCESSED);
				}
			}
		}else if(type==Type.PAYOUT){
			if(amount==null){
				if(depositSlip.getDepositSlipDate()==null){
					return depositSlipService.findByTypeAndBillOfDeptorLikeAndStatus(type, deptor,Status.UNPROCESSED);
				}else{
					return depositSlipService.findByDepositSlipDateAndTypeAndBillOfDeptorLikeAndStatus(depositSlip.getDepositSlipDate(), type, deptor,Status.UNPROCESSED);
				}
			}else{
				if(depositSlip.getDepositSlipDate()==null){
					return depositSlipService.findByTypeAndBillOfDeptorLikeAndAmountAndStatus(type, deptor, amount,Status.UNPROCESSED);
				}else{
					return depositSlipService.findByDepositSlipDateAndTypeAndBillOfDeptorLikeAndAmountAndStatus(depositSlip.getDepositSlipDate(), type, deptor, amount,Status.UNPROCESSED);
				}
			}
		}else if(type==Type.PAYMENTIN || type==Type.TRANSFER){
			if(amount==null){
				if(depositSlip.getDepositSlipDate()==null){
					return depositSlipService.findByTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndStatus(type, receiver, deptor,Status.UNPROCESSED);
				}else{
					return depositSlipService.findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndStatus(depositSlip.getDepositSlipDate(), type, receiver, deptor,Status.UNPROCESSED);
				}
			}else{
				if(depositSlip.getDepositSlipDate()==null){
					return depositSlipService.findByTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmountAndStatus(type, receiver, deptor, amount,Status.UNPROCESSED);
				}else{
					return depositSlipService.findByDepositSlipDateAndTypeAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmountAndStatus(depositSlip.getDepositSlipDate(), type, receiver, deptor, amount,Status.UNPROCESSED);
				}
			}
		}else{
			if(amount==null){
				if(depositSlip.getDepositSlipDate()==null){
					return depositSlipService.findByBillOfReceiverLikeAndBillOfDeptorLikeAndStatus(receiver, deptor,Status.UNPROCESSED);
				}else{
					return depositSlipService.findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLikeAndStatus(depositSlip.getDepositSlipDate(), receiver, deptor,Status.UNPROCESSED);
				}
			}
		}
		return depositSlipService.findByDepositSlipDateAndBillOfReceiverLikeAndBillOfDeptorLikeAndAmountAndStatus(depositSlip.getDepositSlipDate(), receiver, deptor, amount,Status.UNPROCESSED);
	}
	
	
	
	@PostMapping(path = "/upload")
	@ResponseStatus(HttpStatus.OK)
	public int upload(@RequestParam("files") MultipartFile files) {
		try {
		JAXBContext jaxbContext = JAXBContext.newInstance(DepositSlip.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller(); //unmarshaller
		
		//for(int i = 0; i < files.size(); i++ ){
		 File convFile = new File(files.getOriginalFilename());
		 try {
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile); 
			fos.write(files.getBytes());
		    fos.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		DepositSlip depositSlip= (DepositSlip) jaxbUnmarshaller.unmarshal(convFile);
		saveDepositSlip(depositSlip);
		//}
		return 1;

	  } catch (JAXBException e) {
		  return 2;
	  }
	}
	
	/*@PostMapping(path = "/upload")
	@ResponseStatus(HttpStatus.OK)
	public void upload(@RequestBody FormData formData) {
		System.out.println("Pogodio kontroler");
		
		for(int i = 0; i<formData.getAllIds().length; i++){
			System.out.println("vdg"+formData.getAllIds()[0].toString());
		}
		
		for( pair of formData.entries()) {
			   console.log(pair[0]+ ', '+ pair[1]); 
			}
		List<File> files = (List<File>) formData.getAll("file");
		System.out.println("Files" + files.size());
		try {
		JAXBContext jaxbContext = JAXBContext.newInstance(DepositSlip.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller(); //unmarshaller
		
		for(int i = 0; i < files.size(); i++ ){
		 File convFile = new File(files.get(i).getOriginalFilename());
		 try {
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile); 
			fos.write(files.get(i).getBytes());
		    fos.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		DepositSlip depositSlip= (DepositSlip) jaxbUnmarshaller.unmarshal(convFile);
		saveDepositSlip(depositSlip);
		}
	  } catch (JAXBException e) {
		e.printStackTrace();
	  }
	}*/
	
	private void checkBillInBank(long id) {
		List<Bill> billsInBank = bankService.findOne(((Banker)httpSession.getAttribute("user")).getBank().getId()).getBills();
		for(int i=0;i<billsInBank.size();i++) 
			if(id == billsInBank.get(i).getId())
				return;
		throw new BadRequestException();	
	}
	
	private void DepositSlipPayOut(DepositSlip depositSlip) {
		resolveAllForDeptor(depositSlip);
		depositSlip.setStatus(Status.PROCESSED);
		depositSlipService.save(depositSlip);
	}
	private void DepositSlipPaymentIn(DepositSlip depositSlip) {
		depositSlip.setStatus(Status.UNPROCESSED);
		depositSlipService.save(depositSlip);
		
		int bankCodeBillOfReciver = Integer.parseInt(depositSlip.getBillOfReceiver().substring(0, 3));
		int bankCodeBillOfDeptor = Integer.parseInt(depositSlip.getBillOfDeptor().substring(0, 3));
		int bankCode = ((Banker)httpSession.getAttribute("user")).getBank().getCode();

		//da bi se izvrsila transakcija odmah klijenti moraju biti iz iste banke
		if(bankCode == bankCodeBillOfReciver && bankCode == bankCodeBillOfDeptor) {
			resolveAllForDeptor(depositSlip);
			resolveAllForReciver(depositSlip);
		}
		else {
			System.out.println("nije ista banka"); //treba i ovdje vezano za status rijesiti
			//nalog ide u clearing ili rtgs
		}
	}
	private void DepositSlipPaymentOut(DepositSlip depositSlip) {
		
		resolveAllForReciver(depositSlip);
		depositSlip.setStatus(Status.PROCESSED);
		depositSlipService.save(depositSlip);
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
	
	private void exportMT103Message(MT103xml mt103){
		try {

			File file = new File("mt103Poslovna.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(MT103xml.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(mt103, file);
			jaxbMarshaller.marshal(mt103, System.out);
			
			

		      } catch (JAXBException e) {
			e.printStackTrace();
		      }
	}
	
	private MT103xml makeMT103Message(InterbankTransfer interbankTransfer){
		MT103xml mt103 = new MT103xml();
		mt103.setMessageId("fsa1fa5fas");
		mt103.setDebtorBankswiftCode(interbankTransfer.getBankSender().getSwiftCode().toString());
	    mt103.setDebtorBankclearingAccount(interbankTransfer.getBankSender().getClearingAccount().toString());
		mt103.setReceiverBankswiftCode(interbankTransfer.getBankSender().getSwiftCode().toString());
		mt103.setReceiverBankclearingAccount(interbankTransfer.getBankSender().getClearingAccount().toString());
		mt103.setDeptor(interbankTransfer.getDepositSlips().get(0).getDeptor());
		mt103.setPurposeOfPayment(interbankTransfer.getDepositSlips().get(0).getPurposeOfPayment());
		mt103.setReceiver(interbankTransfer.getDepositSlips().get(0).getReceiver());
		
		try {
		    java.sql.Date sqlDepositSlipDate = new java.sql.Date(interbankTransfer.getDepositSlips().get(0).getDepositSlipDate().getTime());
			java.sql.Date sqlCurrencyDate = new java.sql.Date(interbankTransfer.getDepositSlips().get(0).getCurrencyDate().getTime());
			mt103.setDate(sqlDepositSlipDate);
			mt103.setCurrencyDate(sqlCurrencyDate);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		mt103.setBillOfDeptor(interbankTransfer.getDepositSlips().get(0).getBillOfDeptor());
		mt103.setModelAssignment(interbankTransfer.getDepositSlips().get(0).getModelAssignment());
		mt103.setReferenceNumberAssignment(interbankTransfer.getDepositSlips().get(0).getReferenceNumberAssignment());
		mt103.setBillOfReceiver(interbankTransfer.getDepositSlips().get(0).getBillOfReceiver());
		mt103.setModelApproval(interbankTransfer.getDepositSlips().get(0).getModelApproval());
		mt103.setReferenceNumberApproval(interbankTransfer.getDepositSlips().get(0).getReferenceNumberApproval());
		mt103.setAmount(new BigDecimal(interbankTransfer.getDepositSlips().get(0).getAmount()).setScale(2, RoundingMode.CEILING));
		mt103.setCurrencyCode(interbankTransfer.getCurrencyCode());
		
		return mt103;
	}
	

	private InterbankTransfer findInterBankTransfer(Integer bankCodeBillOfReciver,Integer bankCodeBillOfDeptor) {
		List<InterbankTransfer> list = interbankTransferService.findAll();
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getBankReciever().getCode().equals(bankCodeBillOfReciver) && list.get(i).getDateTime() == null) {
				return list.get(i);
			}
		}
		String currencyCode = ((Banker)httpSession.getAttribute("user")).getBank().getCurrencyCode();
		InterbankTransfer retVal = new InterbankTransfer(false,currencyCode);
		retVal.setBankReciever(bankService.findOneByCode(bankCodeBillOfReciver));
		retVal.setBankSender(bankService.findOneByCode(bankCodeBillOfDeptor));
		return retVal;
	}
	
	private DailyBalance createNewDailyBalanceForDeptor(DepositSlip depositSlip) {
		DailyBalance dailyBalance = new DailyBalance();
		dailyBalance.setNewState(depositSlip.getAmount());
		dailyBalance.setTrafficAtExpense(depositSlip.getAmount());
		
		dailyBalance.getDepositSlips().add(depositSlip);
		return dailyBalance;		
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

	
	@PutMapping(path ="/exportBalanceFromDateToDateForBill/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<DepositSlip> exportBalanceFromDateToDateForBill(@PathVariable Long id,  @RequestBody Map<String, String> mapOfDays) {
		
		Bill bill= billService.findOne(id);
		
		List<DailyBalance> allBalances = bill.getDailyBalances();
		
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        Date fromDate = null;
	        Date toDate = null;
	        Date newToDate  = null;
			try {
				fromDate = format.parse(mapOfDays.get("first"));
				toDate = format.parse(mapOfDays.get("last"));
				
				
				Calendar c = Calendar.getInstance(); 
				c.setTime(toDate); 
				c.add(Calendar.DATE, 1);
				newToDate = c.getTime();
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
			List<DepositSlip> slips = new ArrayList<DepositSlip>();
			
			for(int i = 0; i< allBalances.size(); i++){
				java.util.Date utilDate = new java.util.Date(allBalances.get(i).getDate().getTime());
				
				if(compareDates(fromDate,newToDate,utilDate)){
					slips.addAll(allBalances.get(i).getDepositSlips());
				}
			}
	     
			exportAccountStatement(slips,fromDate, toDate);
			return slips;
	     
	}
	
	  private boolean compareDates(Date dateFrom,Date dateTo, Date choosenDate)
	  {
	       	if(dateFrom.before(choosenDate) && dateTo.after(choosenDate)){
	       		return true;
	       	}
	       	
	     return false;
	        
	   }
	  
		private void exportAccountStatement(List<DepositSlip> slips, Date fromDate, Date toDate) {
			
			AccountStatement accountStatement = new AccountStatement();
			accountStatement.setFromDate(new java.sql.Date(fromDate.getTime()));
			accountStatement.setToDate(new java.sql.Date(toDate.getTime()));
			accountStatement.setDepositSlip(slips);
			 
			try {
				File file = new File("generisanIzvod.xml");
				JAXBContext jaxbContext = JAXBContext.newInstance(AccountStatement.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

				jaxbMarshaller.marshal(accountStatement, file);
				jaxbMarshaller.marshal(accountStatement, System.out);

			      } catch (JAXBException e) {
				e.printStackTrace();
			      }
		}
}
