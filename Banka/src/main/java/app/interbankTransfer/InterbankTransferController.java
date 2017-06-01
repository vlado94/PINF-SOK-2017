package app.interbankTransfer;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.bank.Bank;
import app.user.banker.Banker;

@RestController
@RequestMapping("/interbankTransfer")
public class InterbankTransferController {

	private final InterbankTransferService interbankTransferService;
	//private final Int dailyBalanceService;
	private HttpSession httpSession;
	//private final BillService billService;
	
	@Autowired
	public InterbankTransferController(final InterbankTransferService interbankTransferService,HttpSession httpSession) {
		this.interbankTransferService = interbankTransferService;
		this.httpSession = httpSession;
		//this.dailyBalanceService = dailyBalanceService;
		//this.billService = billService;
	}
	
	@GetMapping("/findAllUnsentInterbankTransfer")
	@ResponseStatus(HttpStatus.OK)
	public List<InterbankTransfer> findAllUnsentInterbankTransfer() {
		Banker banker = (Banker) httpSession.getAttribute("user");
		Bank currentBank = banker.getBank();
		return interbankTransferService.findAllByBankCodeAndDateTimeIsNull(currentBank.getCode());
	}
	
	
	
	
}
