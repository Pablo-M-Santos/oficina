package com.autopro.autoproapi.vehicle.mapper;

import com.autopro.autoproapi.vehicle.dto.VehicleCreateDTO;
import com.autopro.autoproapi.vehicle.dto.VehicleResponseDTO;
import com.autopro.autoproapi.vehicle.dto.VehicleUpdateDTO;
import com.autopro.autoproapi.vehicle.model.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    public Vehicle toEntity(VehicleCreateDTO dto) {
        return Vehicle.builder()
                .clientId(dto.getClientId())
                .brand(dto.getBrand())
                .model(dto.getModel())
                .year(dto.getYear())
                .plate(dto.getPlate())
                .color(dto.getColor())
                .mileage(dto.getMileage())
                .build();
    }

    public VehicleResponseDTO toResponse(Vehicle entity) {
        return VehicleResponseDTO.builder()
                .id(entity.getId())
                .clientId(entity.getClientId())
                .brand(entity.getBrand())
                .model(entity.getModel())
                .year(entity.getYear())
                .plate(entity.getPlate())
                .color(entity.getColor())
                .mileage(entity.getMileage())
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public void updateEntity(Vehicle vehicle, VehicleUpdateDTO dto) {
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setYear(dto.getYear());
        vehicle.setPlate(dto.getPlate());
        vehicle.setColor(dto.getColor());
        vehicle.setMileage(dto.getMileage());
    }

}