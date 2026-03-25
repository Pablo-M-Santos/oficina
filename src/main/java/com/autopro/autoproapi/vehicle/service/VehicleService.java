package com.autopro.autoproapi.vehicle.service;


import com.autopro.autoproapi.vehicle.dto.VehicleCreateDTO;
import com.autopro.autoproapi.vehicle.dto.VehicleResponseDTO;
import com.autopro.autoproapi.vehicle.dto.VehicleUpdateDTO;
import com.autopro.autoproapi.vehicle.mapper.VehicleMapper;
import com.autopro.autoproapi.vehicle.model.Vehicle;
import com.autopro.autoproapi.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {


    private final VehicleRepository vehicleRepository;
    private final VehicleMapper mapper;


    public VehicleResponseDTO create(VehicleCreateDTO dto) {
        vehicleRepository.findByPlate(dto.getPlate())
                .ifPresent(v -> {
                    throw new RuntimeException("Placa já cadastrada");
                });
        Vehicle vehicle = mapper.toEntity(dto);

        vehicle = vehicleRepository.save(vehicle);

        return mapper.toResponse(vehicle);

    }

    public VehicleResponseDTO findById(UUID id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        return mapper.toResponse(vehicle);
    }

    public List<VehicleResponseDTO> getAllVehicles() {
        List<Vehicle> vehicle = vehicleRepository.findAllByIsActiveTrue();

        return vehicle.stream()
                .map(mapper::toResponse)
                .toList();
    }

    public VehicleResponseDTO update(UUID id, VehicleUpdateDTO updateDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        mapper.updateEntity(vehicle, updateDTO);

        Vehicle saved = vehicleRepository.save(vehicle);

        return mapper.toResponse(saved);
    }

    public void delete(UUID id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        vehicle.setIsActive(false);

        vehicleRepository.save(vehicle);
    }
}

