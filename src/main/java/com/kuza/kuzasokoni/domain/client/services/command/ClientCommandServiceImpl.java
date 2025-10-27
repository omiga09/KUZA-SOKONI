package com.kuza.kuzasokoni.domain.client.services.command;

import com.kuza.kuzasokoni.domain.client.dtos.command.ClientCreateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.command.ClientUpdateCommand;
import com.kuza.kuzasokoni.domain.client.entities.Client;
import com.kuza.kuzasokoni.domain.client.enums.ClientStatus;
import com.kuza.kuzasokoni.domain.client.enums.VerificationStatus;
import com.kuza.kuzasokoni.domain.client.exceptions.ClientNotFoundException;
import com.kuza.kuzasokoni.domain.client.mappers.ClientCommandMapper;
import com.kuza.kuzasokoni.domain.client.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClientCommandServiceImpl implements ClientCommandService {

    private final ClientRepository clientRepository;
    private final ClientCommandMapper mapper;

    @Transactional
    @Override
    public Client createClient(ClientCreateCommand cmd) {
        if (cmd == null) {
            throw new IllegalArgumentException("Client creation data cannot be null");
        }

        // Optional: Trim key fields
        cmd.setFirstName(cmd.getFirstName().trim());
        cmd.setSecondName(cmd.getSecondName().trim());
        cmd.setLastName(cmd.getLastName().trim());
        cmd.setPhoneNumber(cmd.getPhoneNumber().trim());
        cmd.setEmail(cmd.getEmail().trim());
        cmd.setGender(cmd.getGender().trim());
        cmd.setAddress(cmd.getAddress().trim());

        Client client = mapper.toEntity(cmd);
        client.setStatus(ClientStatus.PENDING);
        client.setIsVerified(VerificationStatus.UNVERIFIED);

        return clientRepository.save(client);
    }

    @Transactional
    @Override
    public Client updateClient(ClientUpdateCommand cmd) {
        if (cmd == null || cmd.getId() == null) {
            throw new IllegalArgumentException("Client update data or ID cannot be null");
        }

        Client client = clientRepository.findById(cmd.getId())
                .orElseThrow(() -> new ClientNotFoundException(cmd.getId()));


        cmd.setFirstName(cmd.getFirstName().trim());
        cmd.setSecondName(cmd.getSecondName().trim());
        cmd.setLastName(cmd.getLastName().trim());
        cmd.setPhoneNumber(cmd.getPhoneNumber().trim());
        cmd.setEmail(cmd.getEmail().trim());
        cmd.setGender(cmd.getGender().trim());
        cmd.setAddress(cmd.getAddress().trim());

        mapper.updateEntity(cmd, client);
        client.setUpdatedAt(LocalDateTime.now());

        return clientRepository.save(client);
    }

    @Transactional
    @Override
    public void deleteClient(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Client ID must be greater than 0");
        }

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        clientRepository.delete(client);
    }

    @Transactional
    @Override
    public Client submitClient(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Client ID must be greater than 0");
        }

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        if (client.getSubmittedAt() != null) {
            throw new IllegalStateException("Client has already been submitted");
        }

        client.setSubmittedAt(LocalDateTime.now());

        return clientRepository.save(client);
    }
}
