package app.user.clientPersonal;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientPersonal")
public class ClientPersonalController {
	private final ClientPersonalService clientService;
	private HttpSession httpSession;
	
	@Autowired
	public ClientPersonalController(final HttpSession httpSession,final ClientPersonalService clientService) {
		this.clientService = clientService;
		this.httpSession = httpSession;
	}
	
	@GetMapping("/checkRights")
	@ResponseStatus(HttpStatus.OK)
	public ClientPersonal checkRights() throws AuthenticationException {
		try {
			return ((ClientPersonal) httpSession.getAttribute("user"));
		} catch (Exception e) {
			throw new AuthenticationException("Forbidden.");
		}
	}
	

}
