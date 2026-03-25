package com.autopro.autoproapi.vehicle.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;
import java.util.UUID;

@Getter
@Setter
public class VehicleCreateDTO {

    @NotNull(message = "Cliente é obrigatório")
    private UUID clientId;

    @NotBlank(message = "Marca é obrigatória")
    @Size(max = 50, message = "Marca deve ter no máximo 50 caracteres")
    private String brand;

    @NotBlank(message = "Modelo é obrigatório")
    @Size(max = 50, message = "Modelo deve ter no máximo 50 caracteres")
    private String model;

    @Min(value = 1900, message = "Ano inválido")
    @Max(value = Year.MAX_VALUE, message = "Ano inválido")
    private Integer year;

    @NotBlank(message = "Placa é obrigatória")
    private String plate;

    @Size(max = 30, message = "Cor muito longa")
    private String color;

    @PositiveOrZero(message = "Quilometragem não pode ser negativa")
    private Integer mileage;
}
