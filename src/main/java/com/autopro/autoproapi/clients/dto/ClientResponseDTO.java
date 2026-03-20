package com.autopro.autoproapi.clients.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ClientResponseDTO {
    private UUID id;

    private String name;

    private String email;

    private String phone;

    private String cpf;

    private String address;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

