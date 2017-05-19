package app.country;


import java.util.List;

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

@RestController
@RequestMapping("/country")
public class CountryController {

	private final CountryService countryService;
	
	@Autowired
	public CountryController(final CountryService countryService) { 
		this.countryService = countryService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Country> findAll() {
		return countryService.findAll(); 
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Country save(@RequestBody Country country) {
		return countryService.save(country);
	}
	
	@GetMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Country findById(@PathVariable Long id) {
		return countryService.findOne(id);
	}
	
	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Country update(@PathVariable Long id,@RequestBody Country country) {
		Country countryForUpdate = countryService.findOne(id);
		countryForUpdate.update(country);
		return countryService.save(countryForUpdate);
	}
	
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable Long id) {
		countryService.delete(id);
	}
	
	@PostMapping(path = "/search")
	@ResponseStatus(HttpStatus.CREATED)
	public List<Country> search(@RequestBody Country country) {
		return countryService.findByCodeLikeOrNameLike(country.getCode(), country.getName());
	}
}
