package com.autopro.autoproapi.clients.dto;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientCreateDTO {

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;

    @Email
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String cpf;

    private String address;

}
