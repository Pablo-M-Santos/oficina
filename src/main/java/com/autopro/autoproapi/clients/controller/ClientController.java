package com.autopro.autoproapi.clients.controller;

import com.autopro.autoproapi.clients.dto.ClientCreateDTO;
import com.autopro.autoproapi.clients.dto.ClientResponseDTO;

import com.autopro.autoproapi.clients.dto.ClientUpdateDTO;
import com.autopro.autoproapi.clients.service.ClientService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService usuarioService) {
        this.clientService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@RequestBody @Valid ClientCreateDTO dto) {

        ClientResponseDTO response = clientService.createClient(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> update(@PathVariable UUID id, @RequestBody  ClientUpdateDTO dto) {

        ClientResponseDTO response = clientService.update(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        clientService.delete(id);

        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAll() {
        List<ClientResponseDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getById(@PathVariable UUID id) {
        ClientResponseDTO client =clientService.getClientById(id);

        return ResponseEntity.ok(client);
    }

}

