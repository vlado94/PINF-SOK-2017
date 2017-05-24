package app.codeBookActivities;

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
@RequestMapping("/codeBookActivities")
public class CodeBookActivitiesController {
	
	private final CodeBookActivitiesService codeBookService;
	
	@Autowired
	public CodeBookActivitiesController(final CodeBookActivitiesService codeBookService) { 
		this.codeBookService = codeBookService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<CodeBookActivities> findAll() {
		return codeBookService.findAll(); 
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) 	
	public CodeBookActivities save(@RequestBody CodeBookActivities codeBookActivities) {
		return codeBookService.save(codeBookActivities);
	}
	
	@GetMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CodeBookActivities findById(@PathVariable Long id) {
		return codeBookService.findOne(id);
	}
	
	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public CodeBookActivities update(@PathVariable Long id,@RequestBody CodeBookActivities codeBookActivities) {
		CodeBookActivities codeBookActivitiesForUpdate = codeBookService.findOne(id);
		codeBookActivitiesForUpdate.update(codeBookActivities);
		return codeBookService.save(codeBookActivitiesForUpdate);
	}
	
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable Long id) {
		codeBookService.delete(id);
	}	
	
	@PostMapping(path = "/search")
	@ResponseStatus(HttpStatus.CREATED)
	public List<CodeBookActivities> searchCodeBookActivity(@RequestBody CodeBookActivities codeBookActivity) {
		Integer code = codeBookActivity.getCode();
		if(code==null){
			code=-1;
		}
		String name = codeBookActivity.getName();
		if(name==null){
			name = "-1";
		}else{
			name = "%"+codeBookActivity.getName()+"%";
		}
		return codeBookService.findByCodeLikeAndNameLike(code, name);
	}
	
	@GetMapping(path = "/findByName/{name}")
	@ResponseStatus(HttpStatus.OK)
	public CodeBookActivities findByName(@PathVariable String name) {
		return codeBookService.findByName(name);		
	}
}
