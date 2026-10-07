package com.devsu.hackerearth.backend.client.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @SuppressWarnings("null")
    public ResponseEntity<List<ClientDto>> getAll() {
        // GET /api/clients
        return ResponseEntity.ok(clientService.getAll());
    }

    @GetMapping("/{id}")
    @SuppressWarnings("null")
    public ResponseEntity<ClientDto> get(@PathVariable Long id) {
        // GET /api/clients/{id}
        return ResponseEntity.ok(clientService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ClientDto> create(@RequestBody ClientDto clientDto) {
        // POST /api/clients
        ClientDto createdClient = clientService.create(clientDto);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @SuppressWarnings("null")
    public ResponseEntity<ClientDto> update(@PathVariable Long id, @RequestBody ClientDto clientDto) {
        // PUT /api/clients/{id}
        clientDto.setId(id); // Aseguramos que el ID del cuerpo coincida con el de la URL
        return ResponseEntity.ok(clientService.update(clientDto));
    }

    @PatchMapping("/{id}")
    @SuppressWarnings("null")
    public ResponseEntity<ClientDto> partialUpdate(@PathVariable Long id, @RequestBody PartialClientDto partialClientDto) {
        // PATCH /api/clients/{id}
        return ResponseEntity.ok(clientService.partialUpdate(id, partialClientDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // DELETE /api/clients/{id}
        clientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}