package com.autopro.autoproapi.clients.service;


import com.autopro.autoproapi.clients.dto.ClientCreateDTO;
import com.autopro.autoproapi.clients.dto.ClientResponseDTO;
import com.autopro.autoproapi.clients.model.Client;
import com.autopro.autoproapi.clients.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public ClientResponseDTO createClient(ClientCreateDTO dto) {

        String normalizedCpf = normalize(dto.getCpf());
        String normalizedPhone = normalize(dto.getPhone());

        if (repository.existsByCpf(normalizedCpf)) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        Client client =  new Client();
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setPhone(normalizedPhone);
        client.setCpf(normalizedCpf);
        client.setAddress(dto.getAddress());

        client.setIsActive(true);
        client.setCreatedAt(LocalDateTime.now());
        client.setUpdatedAt(LocalDateTime.now());

        Client saved = repository.save(client);

        return toResponseDTO(saved);

    }

    private String normalize(String value) {
        if (value == null) return null;
        return value.replaceAll("\\D", "");
    }

    private ClientResponseDTO toResponseDTO(Client client) {
        ClientResponseDTO dto = new ClientResponseDTO();

        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setPhone(client.getPhone());
        dto.setCpf(client.getCpf());
        dto.setAddress(client.getAddress());
        dto.setIsActive(client.getIsActive());
        dto.setCreatedAt(client.getCreatedAt());
        dto.setUpdatedAt(client.getUpdatedAt());

        return dto;
    }


}
