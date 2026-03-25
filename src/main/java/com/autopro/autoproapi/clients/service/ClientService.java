package com.autopro.autoproapi.clients.service;


import com.autopro.autoproapi.clients.dto.ClientCreateDTO;
import com.autopro.autoproapi.clients.dto.ClientResponseDTO;
import com.autopro.autoproapi.clients.dto.ClientUpdateDTO;
import com.autopro.autoproapi.clients.mapper.ClientMapper;
import com.autopro.autoproapi.clients.model.Client;
import com.autopro.autoproapi.clients.repository.ClientRepository;
import com.autopro.autoproapi.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository repository;

    private final ClientMapper mapper;


    public ClientResponseDTO createClient(ClientCreateDTO dto) {

        String normalizedCpf = normalize(dto.getCpf());
        String normalizedPhone = normalize(dto.getPhone());

        validateCpfAlreadyExists(normalizedCpf);

        Client client = mapper.toEntity(dto, normalizedCpf, normalizedPhone);

        Client saved = repository.save(client);

        return mapper.toResponseDTO(saved);
    }

    public ClientResponseDTO update(UUID id, ClientUpdateDTO dto) {
        Client client = findClientOrThrow(id);

        if (dto.getName() != null) {
            client.setName(dto.getName());
        }

        if (dto.getEmail() != null) {
            client.setEmail(dto.getEmail());
        }

        if (dto.getPhone() != null) {
            client.setPhone(normalize(dto.getPhone()));
        }

        if (dto.getAddress() != null) {
            client.setAddress(dto.getAddress());
        }

        client.setUpdatedAt(LocalDateTime.now());

        Client saved = repository.save(client);

        return mapper.toResponseDTO(saved);
    }

    public void delete(UUID id) {
        Client client = findClientOrThrow(id);

        client.setIsActive(false);
        client.setUpdatedAt(LocalDateTime.now());

        repository.save(client);
    }

    public List<ClientResponseDTO> getAllClients() {
        List<Client> clients = repository.findByIsActiveTrue();

        return clients.stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public ClientResponseDTO getClientById(UUID id) {
        Client client = findClientOrThrow(id);

        return mapper.toResponseDTO(client);
    }

    private String normalize(String value) {
        if (value == null) return null;
        return value.replaceAll("\\D", "");
    }


    private Client findClientOrThrow(UUID id) {
        return repository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }


    private void validateCpfAlreadyExists(String cpf) {
        if (repository.existsByCpf(cpf)) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
    }
}
