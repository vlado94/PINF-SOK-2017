package app.common;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.User;
import app.user.banker.BankerService;
import app.user.clientPersonal.ClientPersonalService;

@RestController
@RequestMapping("/start")
public class CommonController {

	private HttpSession httpSession;

	private BankerService bankerService;
	private ClientPersonalService clientService;

	@Autowired
	public CommonController(final HttpSession httpSession, final ClientPersonalService clientService,
			final BankerService bankerService) {
		this.httpSession = httpSession;
		this.bankerService = bankerService;
		this.clientService = clientService;
	}
	
	@PostMapping(path = "/logIn")
	@ResponseStatus(HttpStatus.OK)
	public String logIn(@RequestBody User userInput) {
		User user = null;
		String userType = "";
		if (bankerService.findOneByMailAndPassword(userInput.getMail(), userInput.getPassword()) != null) {
			user = bankerService.findOneByMailAndPassword(userInput.getMail(), userInput.getPassword());
			userType = "banker";
		} else if (clientService.findOneByMailAndPassword(userInput.getMail(),userInput.getPassword()) != null) {
			user = clientService.findOneByMailAndPassword(userInput.getMail(), userInput.getPassword());
			userType = "clientPersonal";
		}
		if (user != null) {
			httpSession.setAttribute("user", user);
			return userType;
		} else
			throw new NoSuchElementException("Ne postoji korisnik sa tim parametrima.");
	}

	@GetMapping(path = "/logOut")
	public void logOut() {
		httpSession.invalidate();
	}

	@GetMapping(path = "/getLoggedUser")
	public User getLoggedUser() {
		return (User) httpSession.getAttribute("user");
	}
}