package com.autopro.autoproapi.vehicle.dto;

import com.autopro.autoproapi.validations.Plate.ValidPlate;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
public class VehicleUpdateDTO {

    @Size(max = 50, message = "Marca deve ter no máximo 50 caracteres")
    private String brand;

    @Size(max = 50, message = "Modelo deve ter no máximo 50 caracteres")
    private String model;

    @Min(value = 1900, message = "Ano inválido")
    @Max(value = Year.MAX_VALUE, message = "Ano inválido")
    private Integer year;

    @ValidPlate
    private String plate;

    @Size(max = 30, message = "Cor muito longa")
    private String color;

    @PositiveOrZero(message = "Quilometragem não pode ser negativa")
    private Integer mileage;
}
