package com.autopro.autoproapi.clients.mapper;

import com.autopro.autoproapi.clients.dto.ClientCreateDTO;
import com.autopro.autoproapi.clients.dto.ClientResponseDTO;
import com.autopro.autoproapi.clients.dto.ClientUpdateDTO;
import com.autopro.autoproapi.clients.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {


    public ClientResponseDTO toResponseDTO(Client client) {
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

    public Client toEntity(ClientCreateDTO dto, String normalizedCpf, String normalizedPhone) {
        return Client.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(normalizedPhone)
                .cpf(normalizedCpf)
                .address(dto.getAddress())
                .build();
    }

    public void updateEntity(Client client, ClientUpdateDTO dto) {
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());
        client.setAddress(dto.getAddress());
    }




}

