package com.autopro.autoproapi.clients.controller;

import com.autopro.autoproapi.clients.dto.ClientCreateDTO;
import com.autopro.autoproapi.clients.dto.ClientResponseDTO;

import com.autopro.autoproapi.clients.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



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

}

