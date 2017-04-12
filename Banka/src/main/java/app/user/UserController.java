package app.user;

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

import app.user.banker.BankerService;

@RestController
@RequestMapping("/user")
public class UserController {
	private HttpSession httpSession;
	private BankerService bankerService;

	@Autowired
	public UserController(final HttpSession httpSession,
			final BankerService bankerService) {
		this.httpSession = httpSession;
		this.bankerService = bankerService;
	}
	
	@GetMapping(path = "/logIn")
	@ResponseStatus(HttpStatus.OK)
	public String logIn(@RequestBody User userInput) {
		User user = null;
		System.out.println("Loged user: "+ userInput.getMail() +" pass" + userInput.getPassword());
		String userType = "";
		if (bankerService.findOneByMailAndPassword(userInput.getMail(), userInput.getPassword()) != null) {
			user = bankerService.findOneByMailAndPassword(userInput.getMail(), userInput.getPassword());
			userType = "banker";
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
