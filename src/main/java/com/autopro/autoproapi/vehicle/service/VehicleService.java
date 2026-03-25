package com.autopro.autoproapi.vehicle.service;

import com.autopro.autoproapi.clients.repository.ClientRepository;
import com.autopro.autoproapi.exception.ResourceNotFoundException;
import com.autopro.autoproapi.vehicle.dto.VehicleCreateDTO;
import com.autopro.autoproapi.vehicle.dto.VehicleResponseDTO;
import com.autopro.autoproapi.vehicle.dto.VehicleUpdateDTO;
import com.autopro.autoproapi.vehicle.mapper.VehicleMapper;
import com.autopro.autoproapi.vehicle.model.Vehicle;
import com.autopro.autoproapi.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {


    private final VehicleRepository vehicleRepository;
    private final ClientRepository clientRepository;
    private final VehicleMapper mapper;


    public VehicleResponseDTO create(VehicleCreateDTO dto) {

        if (!clientRepository.existsById(dto.getClientId())) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        }

        String plate = normalize(dto.getPlate());

        validatePlateAlreadyExists(plate);

        Vehicle vehicle = mapper.toEntity(dto);
        vehicle.setPlate(plate);

        Vehicle saved = vehicleRepository.save(vehicle);

        return mapper.toResponse(saved);
    }

    public VehicleResponseDTO getVehicleById(UUID id) {
        Vehicle vehicle = findVehicleOrThrow(id);

        return mapper.toResponse(vehicle);
    }

    public List<VehicleResponseDTO> getAllVehicles() {
        List<Vehicle> vehicle = vehicleRepository.findByIsActiveTrue();

        return vehicle.stream()
                .map(mapper::toResponse)
                .toList();
    }

    public VehicleResponseDTO update(UUID id, VehicleUpdateDTO dto) {

        Vehicle vehicle = findVehicleOrThrow(id);

        if (dto.getPlate() != null) {
            String plate = normalize(dto.getPlate());

            if (vehicleRepository.existsByPlateAndIdNot(plate, id)) {
                throw new IllegalArgumentException("Placa já cadastrada");
            }

            vehicle.setPlate(plate);
        }

        mapper.updateEntity(vehicle, dto);

        vehicle.setUpdatedAt(LocalDateTime.now());

        Vehicle saved = vehicleRepository.save(vehicle);

        return mapper.toResponse(saved);
    }

    public void delete(UUID id) {
        Vehicle vehicle = findVehicleOrThrow(id);

        vehicle.setIsActive(false);
        vehicle.setUpdatedAt(LocalDateTime.now());

        vehicleRepository.save(vehicle);
    }

    private Vehicle findVehicleOrThrow(UUID id) {
        return vehicleRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veiculo não encontrado"));
    }

    private String normalize(String value) {
        if (value == null) return null;
        return value.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
    }

    private void validatePlateAlreadyExists(String plate) {
        if (vehicleRepository.existsByPlate(plate)) {
            throw new IllegalArgumentException("Placa  já cadastrado");
        }
    }

}

