package app.bankAdmin;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;
import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.bank.BankService;
import app.bill.BillService;
import app.client.ClientService;
import app.closingBill.ClosingBillService;
import app.codeBookActivities.CodeBookActivitiesService;
import app.country.CountryService;
import app.dailyBalance.DailyBalanceService;
import app.depositSlip.DepositSlipService;
import app.exchangeInCurrency.ExchangeInCurrencyService;
import app.exchangeRate.ExchangeRateService;
import app.populatedPlace.PopulatedPlaceService;
import app.user.banker.BankerService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	private final AdminService adminService;
	private HttpSession httpSession;
	
	@Autowired
	public AdminController(final HttpSession httpSession,final BankerService bankerService, final CodeBookActivitiesService codeBookActivitiesService, 
							final CountryService countryService, final ClientService clientService, final PopulatedPlaceService populatedPlaceService,
							final BillService billService, final BankService bankService,final ClosingBillService closingBillService,
							final DailyBalanceService dailyBalanceService, final DepositSlipService depositSlipService,final ExchangeRateService exchangeRateService,
							final ExchangeInCurrencyService exchangeInCurrencyService,final AdminService adminService) {
		this.httpSession = httpSession;
		this.adminService = adminService;
	}
	
	@GetMapping("/checkRights")
	@ResponseStatus(HttpStatus.OK)
	public Admin checkRights() throws AuthenticationException {
		try {
			Admin admin = adminService.findOneById(((Admin) httpSession.getAttribute("user")).getId());
			return admin;
		} catch (Exception e) {
			throw new AuthenticationException("Forbidden.");
		}
	}
	
	@PutMapping("/updateProfile/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Admin updateProfile(@PathVariable Long id,@RequestBody Admin admin) {
		Admin adminForEdit = adminService.findOneById(id);
		if(adminForEdit != null) {
			adminForEdit.setAttributes(admin);
			return adminService.save(adminForEdit);		
		}
		else {
			throw new NotFoundException();
		}
	}
}
