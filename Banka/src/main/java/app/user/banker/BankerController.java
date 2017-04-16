package app.user.banker;

import java.util.List;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import app.client.Client;
import app.client.Client.TypeOfClient;
import app.client.ClientService;
import app.codeBookActivities.CodeBookActivities;
import app.codeBookActivities.CodeBookActivitiesService;
import app.country.Country;
import app.country.CountryService;
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
	private HttpSession httpSession;
	
	@Autowired
	public BankerController(final HttpSession httpSession,final BankerService bankerService, final CodeBookActivitiesService codeBookActivitiesService, 
							final CountryService countryService, final ClientService clientService, final PopulatedPlaceService populatedPlaceService) {
		this.bankerService = bankerService;
		this.codeBookActivitiesService = codeBookActivitiesService;
		this.countryService = countryService;
		this.clientService = clientService;
		this.populatedPlaceService = populatedPlaceService;
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
	
	@GetMapping("/findAllCodeBookActivities")
	@ResponseStatus(HttpStatus.OK)
	public List<CodeBookActivities> findAllCodeBookActivities() {
		return codeBookActivitiesService.findAll(); 
	}
	
	@PostMapping(path = "/saveCodeBookActivity")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveCodeBookActivity(@RequestBody CodeBookActivities codeBookActivity) {
		codeBookActivitiesService.save(codeBookActivity);
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
	
	@PostMapping(path = "/saveCountry")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveCountry(@RequestBody Country country) {
		countryService.save(country);
	}
	
	@PostMapping(path = "/saveIndividualPerson")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveIndividualPerson(@Valid @RequestBody Client client) {
		client.setType(TypeOfClient.FIZICKO);
		clientService.save(client);
	}
	
	@PostMapping(path = "/saveLegalPerson")
	@ResponseStatus(HttpStatus.CREATED)
	public void saveLegalPerson(@Valid @RequestBody Client client) {
		client.setType(TypeOfClient.PRAVNO);
		clientService.save(client);
	}
	
	@GetMapping("/findAllPopulatedPlaces")
	@ResponseStatus(HttpStatus.OK)
	public List<PopulatedPlace> findAllPopulatedPlaces() {
		return populatedPlaceService.findAll(); 
	}
	
	@PostMapping(path = "/savePopulatedPlace")
	@ResponseStatus(HttpStatus.CREATED)
	public void savePopulatedPlace(@Valid @RequestBody PopulatedPlace populatedPlace) {
		populatedPlaceService.save(populatedPlace);
	}
	
	
}
