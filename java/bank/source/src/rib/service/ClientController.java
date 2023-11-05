package rib.service;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rib.entity.Client;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    
    @GetMapping
    public Iterable<Client> getAllClients() throws Exception {
    	ClientService clientService = new ClientService();
    	return clientService.showAllClients();
    }
    
    @GetMapping("/all")
    public Iterable<Client> getAllClients2() throws Exception {
    	ClientService clientService = new ClientService();
    	// this will not work , just for testing
    	return clientService.getAllClientsUsingJDBC(null);
    }
    
    

    
    @PostMapping
    public Client createClient(@RequestBody Client client) throws Exception {
    	ClientService clientService = new ClientService();
    	clientService.addClient(client);
		return client;
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable int id, @RequestBody Client updatedClient) throws Exception {
    	ClientService clientService = new ClientService();
    	clientService.addClient(updatedClient);
		return updatedClient;
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Client client) {
    	System.out.println("client is " + client);
    }
}
