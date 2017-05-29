package app.exchangeRate;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.bank.Bank;
import app.bank.BankService;
import app.bankAdmin.Admin;
import app.exchangeInCurrency.ExchangeInCurrency;
import app.exchangeInCurrency.ExchangeInCurrencyService;

@RestController
@RequestMapping("/exchangeRate")
public class ExchangeRateController {

	private final ExchangeRateService exchangeRateService;

	private final BankService bankService;
	private final HttpSession httpSession;
	private final ExchangeInCurrencyService exchangeInCurrencyService;
	@Autowired
	public ExchangeRateController(final ExchangeRateService exchangeRateService,final ExchangeInCurrencyService exchangeInCurrencyService
			,final BankService bankService,HttpSession httpSession) { 
		this.httpSession = httpSession;
		this.exchangeRateService = exchangeRateService;
		this.exchangeInCurrencyService = exchangeInCurrencyService;
		this.bankService = bankService;
	}
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<ExchangeRate> findAll() {
		return exchangeRateService.findAll(); 
	}
	
	@GetMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ExchangeRate findById(@PathVariable Long id) {
		return exchangeRateService.findOne(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ExchangeRate save(@Valid @RequestBody ExchangeRate exchangeRate) {
		exchangeRate.setNumberOfExchangeRate(111);
		for(int i=0;i<exchangeRate.getExchangeInCurrencies().size();i++) {
			ExchangeInCurrency exchangeInCurrency = exchangeInCurrencyService.save(exchangeRate.getExchangeInCurrencies().get(i));
			exchangeRate.getExchangeInCurrencies().set(i, exchangeInCurrency);
		}
		exchangeRate = exchangeRateService.save(exchangeRate);
		Bank bank = bankService.findOne(((Admin)httpSession.getAttribute("user")).getBank().getId());
		bank.getExchangeRates().add(exchangeRate);
		bankService.save(bank);
		return exchangeRate;
	}
	
	@PostMapping("/search")
	@ResponseStatus(HttpStatus.CREATED)
	public List<ExchangeRate> search(@RequestBody ExchangeRate exchangeRate) {
		Date date=exchangeRate.getDate();
		Date startDate = exchangeRate.getStartDate();
		Integer number = exchangeRate.getNumberOfExchangeRate();
		System.out.println(number+"  "+date+"  "+startDate);
		if(date==null){
			if(startDate==null){
				if(number==null){
					return exchangeRateService.findAll();
				}else{
					return exchangeRateService.findByNumberOfExchangeRate(number);
				}
			}else{
				if(number==null){
					return exchangeRateService.findByStartDate(startDate);
				}else{
					return exchangeRateService.findByNumberOfExchangeRateAndStartDate(number, startDate);
				}
			}
		}else{
			if(startDate==null){
				if(number==null){
					return exchangeRateService.findByDate(date);
				}else{
					return exchangeRateService.findByNumberOfExchangeRateAndDate(number, date);
				}
			}else{
				if(number==null){
					return exchangeRateService.findByDateAndStartDate(date, startDate);
				}else{
					return exchangeRateService.findByNumberOfExchangeRateAndDateAndStartDate(number, date, startDate);
				}
			}
		}
	}
}
