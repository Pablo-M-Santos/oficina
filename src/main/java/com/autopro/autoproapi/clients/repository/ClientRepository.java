package com.autopro.autoproapi.clients.repository;

import com.autopro.autoproapi.clients.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    boolean existsByCpf(String cpf);
    List<Client> findByIsActiveTrue();
}
