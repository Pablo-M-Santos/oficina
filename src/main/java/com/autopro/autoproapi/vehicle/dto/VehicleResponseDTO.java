package com.autopro.autoproapi.vehicle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class VehicleResponseDTO {

    private UUID id;
    private UUID clientId;
    private String brand;
    private String model;
    private Integer year;
    private String plate;
    private String color;
    private Integer mileage;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}