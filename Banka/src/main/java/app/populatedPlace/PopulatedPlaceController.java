package app.populatedPlace;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

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
@RequestMapping("/populatedPlaces")
public class PopulatedPlaceController {

	private final PopulatedPlaceService populatedPlaceService;
	
	@Autowired
	public PopulatedPlaceController(final PopulatedPlaceService populatedPlaceService) { 
		this.populatedPlaceService = populatedPlaceService;
	}
	
	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<PopulatedPlace> findAll() {
		return populatedPlaceService.findAll(); 
	}
	
	@GetMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PopulatedPlace findById(@PathVariable Long id) {
		return populatedPlaceService.findOne(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PopulatedPlace save(@Valid @RequestBody PopulatedPlace populatedPlace) {
		return populatedPlaceService.save(populatedPlace);
	}
	
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable Long id) {
		populatedPlaceService.delete(id);
	}
	
	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public PopulatedPlace update(@PathVariable Long id,@RequestBody PopulatedPlace populatedPlace) {
		PopulatedPlace populatedPlaceForUpdate = populatedPlaceService.findOne(id);
		if(populatedPlaceForUpdate != null) {
			populatedPlaceForUpdate.update(populatedPlace);
			return populatedPlaceService.save(populatedPlaceForUpdate);
		}
		else {
			throw new NotFoundException();
		}
	}
	
	@GetMapping(path = "/search")
	@ResponseStatus(HttpStatus.OK)
	public List<PopulatedPlace> search(@RequestBody PopulatedPlace populatedPlace) {
		return populatedPlaceService.findByNameLikeOrPttCodeLikeOrCountry_NameLike(populatedPlace.getName(), populatedPlace.getPttCode(),populatedPlace.getCountry());
	}
}
