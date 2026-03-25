package com.autopro.autoproapi.vehicle.controller;

import com.autopro.autoproapi.vehicle.dto.VehicleCreateDTO;
import com.autopro.autoproapi.vehicle.dto.VehicleResponseDTO;
import com.autopro.autoproapi.vehicle.dto.VehicleUpdateDTO;
import com.autopro.autoproapi.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private  final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> create(@RequestBody @Valid VehicleCreateDTO dto) {
        VehicleResponseDTO response = vehicleService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> update(@PathVariable UUID id, @RequestBody VehicleUpdateDTO dto) {
        VehicleResponseDTO response = vehicleService.update(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> getAll() {
        List<VehicleResponseDTO> vehicle = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getById(@PathVariable UUID id) {
        VehicleResponseDTO vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicle);
    }
}
