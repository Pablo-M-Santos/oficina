package com.autopro.autoproapi.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleUpdateDTO {

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
