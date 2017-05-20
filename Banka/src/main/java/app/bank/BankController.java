package app.bank;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
public class BankController {
	
	private final BankService bankService;
	private HttpSession httpSession;
	
	@Autowired
	public BankController(final BankService bankService,HttpSession httpSession) {
		this.bankService = bankService;
		this.httpSession = httpSession;
	}
}
