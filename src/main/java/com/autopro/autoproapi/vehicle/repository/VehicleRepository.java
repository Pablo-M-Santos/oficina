package com.autopro.autoproapi.vehicle.repository;

import com.autopro.autoproapi.clients.model.Client;
import com.autopro.autoproapi.vehicle.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    boolean existsByPlate(String plate);

    List<Vehicle> findByIsActiveTrue();

    Optional<Vehicle> findByIdAndIsActiveTrue(UUID id);

    boolean existsByPlateAndIdNot(String plate, UUID id);
}