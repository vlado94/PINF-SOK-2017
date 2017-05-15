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
import org.springframework.web.bind.annotation.DeleteMapping;
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
import app.client.Client.TypeOfClient;
import app.client.ClientService;
import app.closingBill.ClosingBill;
import app.closingBill.ClosingBillService;
import app.codeBookActivities.CodeBookActivities;
import app.codeBookActivities.CodeBookActivitiesService;
import app.country.Country;
import app.country.CountryService;
import app.dailyBalance.DailyBalance;
import app.dailyBalance.DailyBalanceService;
import app.depositSlip.DepositSlip;
import app.depositSlip.DepositSlip.Type;
import app.depositSlip.DepositSlipService;
import app.exchangeRate.ExchangeRate;
import app.exchangeRate.ExchangeRateService;
import app.populatedPlace.PopulatedPlace;
import app.populatedPlace.PopulatedPlaceService;

@RestController
@RequestMapping("/banker")
public class BankerController {
	
	private final BankerService bankerService;
	private final CodeBookActivitiesService codeBookActivitiesService;
	private final CountryService countryService;
	private final ClientService clientService;
	private final PopulatedPlaceService populatedPlaceService;
	private final BillService billService;
	private final BankService bankService;
	private HttpSession httpSession;
	private final ClosingBillService closingBillService;
	private final DailyBalanceService dailyBalanceService;
	private final DepositSlipService depositSlipService;
	private final ExchangeRateService exchangeRateService;
	
	@Autowired
	public BankerController(final HttpSession httpSession,final BankerService bankerService, final CodeBookActivitiesService codeBookActivitiesService, 
							final CountryService countryService, final ClientService clientService, final PopulatedPlaceService populatedPlaceService,
							final BillService billService, final BankService bankService,final ClosingBillService closingBillService,
							final DailyBalanceService dailyBalanceService,DepositSlipService depositSlipService,ExchangeRateService exchangeRateService) {
		this.bankerService = bankerService;
		this.codeBookActivitiesService = codeBookActivitiesService;
		this.countryService = countryService;
		this.clientService = clientService;
		this.populatedPlaceService = populatedPlaceService;
		this.billService = billService;
		this.bankService = bankService;
		this.httpSession = httpSession;
		this.closingBillService = closingBillService;
		this.dailyBalanceService = dailyBalanceService;
		this.depositSlipService = depositSlipService;
		this.exchangeRateService = exchangeRateService;
	}
	
	@GetMapping("/checkRights")
	@ResponseStatus(HttpStatus.OK)
	public Banker checkRights() throws AuthenticationException {
		try {
			return ((Banker) httpSession.getAttribute("user"));
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
	
	@GetMapping("/findAllCodeBookActivities")
	@ResponseStatus(HttpStatus.OK)
	public List<CodeBookActivities> findAllCodeBookActivities() {
		return codeBookActivitiesService.findAll(); 
	}
	
	@GetMapping(path = "/findActivityById/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CodeBookActivities findActivityById(@PathVariable Long id) {
		return codeBookActivitiesService.findOne(id);
	}
	
	
	@GetMapping(path = "/findActivityByName/{name}")
	@ResponseStatus(HttpStatus.OK)
	public CodeBookActivities findActivityByName(@PathVariable String name) {
		return codeBookActivitiesService.findByName(name);
		
	}
	
	
	@PostMapping(path = "/saveCodeBookActivity")
	@ResponseStatus(HttpStatus.CREATED)
	public CodeBookActivities saveCodeBookActivity(@RequestBody CodeBookActivities codeBookActivity) {
		try {
			return codeBookActivitiesService.save(codeBookActivity);
		}
		catch(Exception ex) {
			throw new NotFoundException();
		}

	}
	
	@PutMapping(path = "/updateCodeBookActivity/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public CodeBookActivities updateCodeBookActivity(@PathVariable Long id,@RequestBody CodeBookActivities codeBookActivity) {
		CodeBookActivities codeBookActivityForUpdate = codeBookActivitiesService.findOne(id);
		if(codeBookActivityForUpdate != null) {
			codeBookActivityForUpdate.setCode(codeBookActivity.getCode());
			codeBookActivityForUpdate.setName(codeBookActivity.getName());
			return codeBookActivitiesService.save(codeBookActivity);
		}
		else {
			throw new NotFoundException();
		}
		
	}
	
	
	@DeleteMapping(path = "/deleteCodeBookActivity/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteCodeBookActivity(@PathVariable Long id) {
		codeBookActivitiesService.delete(id);
	}
	
	@GetMapping("/findAllCountries")
	@ResponseStatus(HttpStatus.OK)
	public List<Country> findAllCountries() {
		return countryService.findAll(); 
	}
	
	
	@GetMapping(path = "/findCountryById/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Country findCountryById(@PathVariable Long id) {
		return countryService.findOne(id);
	}
	
	@GetMapping(path = "/findCountryByName/{name}")
	@ResponseStatus(HttpStatus.OK)
	public Country findCountryByName(@PathVariable String name) {
		return countryService.findByName(name);
		
	}
	
	@PostMapping(path = "/saveCountry")
	@ResponseStatus(HttpStatus.CREATED)
	public Country saveCountry(@RequestBody Country country) {
		return countryService.save(country);
	}
	
	@PutMapping(path = "/updateCountry/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Country updateCountry(@PathVariable Long id,@RequestBody Country country) {
		Country countryForUpdate = countryService.findOne(id);
		countryForUpdate.setCode(country.getCode());
		countryForUpdate.setName(country.getName());
		return countryService.save(countryForUpdate);
	}
	
	@DeleteMapping(path = "/deleteCountry/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteCountry(@PathVariable Long id) {
		countryService.delete(id);
	}
	
	@GetMapping(path = "/searchCountry")
	@ResponseStatus(HttpStatus.OK)
	public List<Country> searchCountry(@RequestBody Country country) {
		System.out.println(country.getCode()+" "+country.getName());
		
		return null;
	}
	
	@GetMapping(path = "/exchangeRateDetails/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ExchangeRate exchangeRateDetails(@PathVariable Long id) {
		return exchangeRateService.findOne(id);
	}
	
	@GetMapping("/findAllIndividualBills")
	@ResponseStatus(HttpStatus.OK)
	public List<Client> findAllIndividualBills() {
		return clientService.findAllIndividualBills(); 
	}
	
	@PostMapping(path = "/saveIndividualBill")
	@ResponseStatus(HttpStatus.CREATED)
	public Client saveIndividualBill(@Valid @RequestBody Client client) {
		client.setType(TypeOfClient.FIZICKO);
		return clientService.save(client);
	}
	
	
	@GetMapping(path = "/findClientById/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Client findClientById(@PathVariable Long id) {
		return clientService.findOne(id);
	}
	
	@PutMapping(path = "/updateIndividualClient/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public void updateIndividualClient(@PathVariable Long id,@RequestBody Client client) {
		Client clientForUpdate = clientService.findOne(id);
		if(clientForUpdate != null) {
			clientForUpdate.setApplicant(client.getApplicant());
			clientForUpdate.setJmbg(client.getJmbg());
			clientForUpdate.setAddress(client.getAddress());
			clientForUpdate.setPhone(client.getPhone());
			clientForUpdate.setFax(client.getFax());
			clientForUpdate.setMail(client.getMail());
			clientForUpdate.setDeliveryAddress(client.getDeliveryAddress());
			clientForUpdate.setDeliveryByMail(client.isDeliveryByMail());
			clientForUpdate.setShortName(client.getShortName());
			clientForUpdate.setPib(client.getPib());
			clientForUpdate.setMib(client.getMib());
			clientForUpdate.setTaxAuthority(client.getTaxAuthority());
			clientForUpdate.setResponsiblePerson(client.getResponsiblePerson());
			clientForUpdate.setCodeBookActivities(client.getCodeBookActivities());
			clientService.save(clientForUpdate);
		}
		else {
			throw new NotFoundException();
		}
	}
	
	
	@PutMapping(path = "/updateLegalClient/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public void updateLegalClient(@PathVariable Long id,@RequestBody Client client) {
		Client clientForUpdate = clientService.findOne(id);
		if(clientForUpdate != null) {
			clientForUpdate.setApplicant(client.getApplicant());
			clientForUpdate.setJmbg(client.getJmbg());
			clientForUpdate.setAddress(client.getAddress());
			clientForUpdate.setPhone(client.getPhone());
			clientForUpdate.setFax(client.getFax());
			clientForUpdate.setMail(client.getMail());
			clientForUpdate.setShortName(client.getShortName());
			clientForUpdate.setPib(client.getPib());
			clientForUpdate.setMib(client.getMib());
			clientForUpdate.setTaxAuthority(client.getTaxAuthority());
			clientForUpdate.setDeliveryAddress(client.getDeliveryAddress());
			clientForUpdate.setDeliveryByMail(client.isDeliveryByMail());
			clientForUpdate.setResponsiblePerson(client.getResponsiblePerson());
			clientForUpdate.setCodeBookActivities(client.getCodeBookActivities());
			clientService.save(clientForUpdate);
		}
		else {
			throw new NotFoundException();
		}
	}
	
	@GetMapping("/findAllLegalBills")
	@ResponseStatus(HttpStatus.OK)
	public List<Client> findAllLegalBills() {
		return clientService.findAllLegalBills(); 
	}
	
	@PostMapping(path = "/saveLegalBill")
	@ResponseStatus(HttpStatus.CREATED)
	public Client saveLegalBill(@Valid @RequestBody Client client) {
		client.setType(TypeOfClient.PRAVNO);
		return clientService.save(client);
	}
	
	@GetMapping("/findAllPopulatedPlaces")
	@ResponseStatus(HttpStatus.OK)
	public List<PopulatedPlace> findAllPopulatedPlaces() {
		return populatedPlaceService.findAll(); 
	}
	
	
	@GetMapping(path = "/findPopulatedPlaceById/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PopulatedPlace findPopulatedPlaceById(@PathVariable Long id) {
		return populatedPlaceService.findOne(id);
	}
	
	@PostMapping(path = "/savePopulatedPlace")
	@ResponseStatus(HttpStatus.CREATED)
	public PopulatedPlace savePopulatedPlace(@Valid @RequestBody PopulatedPlace populatedPlace) {
		return populatedPlaceService.save(populatedPlace);
	}
	
	@DeleteMapping(path = "/deletePopulatedPlace/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deletePopulatedPlace(@PathVariable Long id) {
		populatedPlaceService.delete(id);
	}
	
	@PutMapping(path = "/updatePopulatedPlace/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public PopulatedPlace updatePopulatedPlace(@PathVariable Long id,@RequestBody PopulatedPlace populatedPlace) {
		PopulatedPlace populatedPlaceForUpdate = populatedPlaceService.findOne(id);
		if(populatedPlaceForUpdate != null) {
			populatedPlaceForUpdate.setName(populatedPlace.getName());
			populatedPlaceForUpdate.setPttCode(populatedPlace.getPttCode());
			populatedPlaceForUpdate.setCountry(populatedPlace.getCountry());
			return populatedPlaceService.save(populatedPlaceForUpdate);
		}
		else {
			throw new NotFoundException();
		}
	}
	
	
	@PostMapping(path = "/saveBill")
	@ResponseStatus(HttpStatus.CREATED)
	public Bill saveBill(@Valid @RequestBody Bill bill) {
		
		//desa dodala da cim se kreira racun, doda u DailyBalance red saza taj racun, 
		//sa vrijednostima za prethodno i tekuce stanje 0
		/*DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		DailyBalance dailyBalance = new DailyBalance();
		dailyBalance.setDate(date);
		dailyBalance.setPreviousState(0);
		dailyBalance.setNewState(0);
		dailyBalance.setTrafficAtExpense(0);
		dailyBalance.setTrafficToBenefit(0);	
		dailyBalanceService.save(dailyBalance);*/
		
		bill.setStatus(true);//postavi da je racun otvoren
		return billService.save(bill);
	}
	
	@PutMapping(path = "/updateBank/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public void updateBank(@PathVariable Long id,@RequestBody Bank bank) {
		Bank bankForUpdate = bankService.findOne(id);
		bankForUpdate.setBills(bank.getBills());
		bankService.save(bankForUpdate);
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