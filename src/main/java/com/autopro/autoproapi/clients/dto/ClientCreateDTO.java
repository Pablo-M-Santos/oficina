package com.autopro.autoproapi.clients.dto;
import com.autopro.autoproapi.validations.CPF.ValidCPF;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientCreateDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 a 100 caracteres")
    private String name;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(
            regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$",
            message = "Telefone inválido"
    )
    private String phone;


    @NotBlank(message = "CPF é obrigatório")
    @ValidCPF
    private String cpf;

    @Size(max = 255, message = "Endereço muito longo")
    private String address;

}
