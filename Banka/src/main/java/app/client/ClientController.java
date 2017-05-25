package app.client;

import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

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

import app.client.Client.TypeOfClient;

@RestController
@RequestMapping("/client")
public class ClientController {
	
	private final ClientService clientService;
	
	@Autowired
	public ClientController(final ClientService clientService) {
		this.clientService = clientService;
	}
	
	@PostMapping(path = "/saveLegalBill")
	@ResponseStatus(HttpStatus.CREATED)
	public Client saveLegalBill(@Valid @RequestBody Client client) {
		client.setType(TypeOfClient.PRAVNO);
		return clientService.save(client);
	}
	
	@GetMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Client findClientById(@PathVariable Long id) {
		return clientService.findOne(id);
	}	
	
	@PutMapping(path = "/updateLegalClient/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public void updateLegalClient(@PathVariable Long id,@RequestBody Client client) {
		Client clientForUpdate = clientService.findOne(id);
		if(clientForUpdate != null) {
			clientForUpdate.update(client);
			clientService.save(clientForUpdate);
		}
		else {
			throw new NotFoundException();
		}
	}
	
	@PutMapping(path = "/updateIndividualClient/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public void updateIndividualClient(@PathVariable Long id,@RequestBody Client client) {
		Client clientForUpdate = clientService.findOne(id);
		if(clientForUpdate != null) {
			clientForUpdate.update(client);
			clientService.save(clientForUpdate);
		}
		else {
			throw new NotFoundException();
		}
	}
}
