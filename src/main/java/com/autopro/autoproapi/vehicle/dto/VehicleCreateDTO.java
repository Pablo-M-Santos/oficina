package com.autopro.autoproapi.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class VehicleCreateDTO {

    @NotNull
    private UUID clientId;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    private Integer year;

    @NotBlank
    private String plate;

    private String color;

    private Integer mileage;



}
