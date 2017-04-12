package app.user.banker;

import java.util.List;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.codeBookActivities.CodeBookActivities;
import app.codeBookActivities.CodeBookActivitiesService;
import app.country.Country;
import app.country.CountryService;

@RestController
@RequestMapping("/banker")
public class BankerController {
	
	private final BankerService bankerService;
	private final CodeBookActivitiesService codeBookActivitiesService;
	private final CountryService countryService;
	private HttpSession httpSession;
	
	@Autowired
	public BankerController(final HttpSession httpSession,final BankerService bankerService, final CodeBookActivitiesService codeBookActivitiesService, 
							final CountryService countryService) {
		this.bankerService = bankerService;
		this.codeBookActivitiesService = codeBookActivitiesService;
		this.countryService = countryService;
		this.httpSession = httpSession;
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
	
	@PutMapping(path = "/update/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Banker update(@PathVariable Long id,@RequestBody Banker banker) {
		Banker bankerForEdit = bankerService.findOneById(id);
		bankerForEdit.setAttributes(banker);
		banker = bankerService.save(bankerForEdit);
		return banker;		
	}
	
	
	@GetMapping("/getAllCodeBookActivities")
	@ResponseStatus(HttpStatus.OK)
	public List<CodeBookActivities> getAllCodeBookActivities() {
		return codeBookActivitiesService.findAll(); 
	}
	
	
	@PostMapping(path = "/addCodeBookActivity")
	@ResponseStatus(HttpStatus.CREATED)
	public void addCodeBookActivity(@RequestBody CodeBookActivities codeBookActivity) {
		codeBookActivitiesService.save(codeBookActivity);
	}
	
	@GetMapping("/getAllCountries")
	@ResponseStatus(HttpStatus.OK)
	public List<Country> getAllCountries() {
		return countryService.findAll(); 
	}
	
	@PostMapping(path = "/addCountry")
	@ResponseStatus(HttpStatus.CREATED)
	public void addCountry(@RequestBody Country country) {
		countryService.save(country);
	}
	
	
}
