package MS_CLIENTE.MS_CLIENTE.controller;

import MS_CLIENTE.MS_CLIENTE.domain.ClientDto;
import MS_CLIENTE.MS_CLIENTE.entity.ClientEntity;
import MS_CLIENTE.MS_CLIENTE.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @PostMapping()
    @Operation(summary = "create a client")
    public ResponseEntity<ClientEntity> createClient(@RequestBody ClientDto clientDTO) {
        ClientEntity createClient = clientService.createClient(clientDTO);
        return new ResponseEntity<>(createClient, HttpStatus.CREATED);
    }

    @GetMapping()
    @Operation(summary = "Search all clients")
    public ResponseEntity <List<ClientEntity>> getAllClients() {
        List<ClientEntity> clients = this.clientService.getAllClients();
        return ResponseEntity.ok().body(clients);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a client by ID")
    public ResponseEntity<ClientEntity> updateClient(@PathVariable("id") Long id, @RequestBody ClientDto clientDTO) {
        ClientEntity updatedClient = clientService.updateClient(id, clientDTO);
        return ResponseEntity.ok().body(updatedClient);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Search for a client by ID")
    public ResponseEntity<Optional<ClientEntity>> getClientById(@PathVariable("id") Long id) {
        Optional<ClientEntity> client = this.clientService.getById(id);
        return ResponseEntity.ok().body(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
