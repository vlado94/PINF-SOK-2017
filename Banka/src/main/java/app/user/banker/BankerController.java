package app.user.banker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
import app.depositSlip.DepositSlipService;
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
	
	@Autowired
	public BankerController(final HttpSession httpSession,final BankerService bankerService, final CodeBookActivitiesService codeBookActivitiesService, 
							final CountryService countryService, final ClientService clientService, final PopulatedPlaceService populatedPlaceService,
							final BillService billService, final BankService bankService,final ClosingBillService closingBillService,
							final DailyBalanceService dailyBalanceService,DepositSlipService depositSlipService) {
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
	public void saveCodeBookActivity(@RequestBody CodeBookActivities codeBookActivity) {
		try {
			codeBookActivitiesService.save(codeBookActivity);
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
			clientForUpdate.setDeliveryAddress(client.getDeliveryAddress());
			clientForUpdate.setDeliveryByMail(client.isDeliveryByMail());
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
	public void saveLegalBill(@Valid @RequestBody Client client) {
		client.setType(TypeOfClient.PRAVNO);
		clientService.save(client);
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
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		DailyBalance dailyBalance = new DailyBalance();
		dailyBalance.setDate(date);
		dailyBalance.setPreviousState(0);
		dailyBalance.setNewState(0);
		dailyBalance.setTrafficAtExpense(0);
		dailyBalance.setTrafficToBenefit(0);	
		dailyBalanceService.save(dailyBalance);
		return billService.save(bill);
	}
	
	@PutMapping(path = "/updateBank/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public void updateBank(@PathVariable Long id,@RequestBody Bank bank) {
		Bank bankForUpdate = bankService.findOne(id);
		bankForUpdate.setBills(bank.getBills());
		bankService.save(bankForUpdate);
	}
	
	//zatvaranje racuna i prebacivanje sredstava na racun pravnog nasljednika
	@PostMapping(path = "/closeBill")
	@ResponseStatus(HttpStatus.CREATED)
	public ClosingBill closeBill(@Valid @RequestBody ClosingBill closingBill) {
		Bill billSuccessor = billService.findByAccountNumber(closingBill.getBillSuccessor());
		Date date=new Date();;
		if(billSuccessor!=null){
			/////ISPRAVI OVO DESANKA posto trenutno uzimas posljednje stanje iz DailyBalance
			List<DailyBalance> dailyBalances = dailyBalanceService.findByBill_id(billSuccessor.getId());
			DailyBalance dailyBalance = dailyBalances.get(dailyBalances.size()-1);
			Bill billForClose = closingBill.getBill();
			List<DailyBalance> dailyBalancesForClose = dailyBalanceService.findByBill_id(billForClose.getId());
			DailyBalance dailyBalanceForClose = dailyBalancesForClose.get(dailyBalancesForClose.size()-1);
			double newNewState = dailyBalance.getNewState() + dailyBalanceForClose.getNewState();
			DailyBalance newDailyBalance = new DailyBalance();
			newDailyBalance.setDate(date);
			newDailyBalance.setPreviousState(dailyBalance.getNewState());
			newDailyBalance.setNewState(newNewState);
			//u korist je stanje sa racuna koji se zatvara
			newDailyBalance.setTrafficToBenefit(dailyBalanceForClose.getNewState());
			newDailyBalance.setTrafficAtExpense(0);
			DailyBalance db = dailyBalanceService.save(newDailyBalance);
		}

		closingBill.setDate(date);
		return closingBillService.save(closingBill);
	}
	
	@PostMapping(path = "/saveDepositSlip")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveDepositSlip(@RequestBody DepositSlip depositSlip) {
		depositSlipService.save(depositSlip);
	}
}