package com.kuza.kuzasokoni.domain.authentication.services.command;

import com.kuza.kuzasokoni.domain.client.dtos.command.ClientCreateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.command.ClientUpdateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;
import com.kuza.kuzasokoni.domain.client.entities.Client;
import com.kuza.kuzasokoni.domain.client.exceptions.ClientNotFoundException;
import com.kuza.kuzasokoni.domain.client.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerCommandService {

    private final ClientRepository clientRepository;

    @Transactional
    public ClientView createCustomer(ClientCreateCommand cmd) {
        // Validate unique phoneNumber and email
        if (clientRepository.findByPhoneNumber(cmd.getPhoneNumber()).isPresent()) {
            throw new IllegalArgumentException("Phone number already in use");
        }

        // map command to entity
        Client client = new Client();
        client.setFirstName(cmd.getFirstName());
        client.setSecondName(cmd.getSecondName());
        client.setLastName(cmd.getLastName());
        client.setPhoneNumber(cmd.getPhoneNumber());
        client.setEmail(cmd.getEmail());
        client.setDateOfBirth(cmd.getDateOfBirth());
        client.setGender(cmd.getGender());
        client.setAddress(cmd.getAddress());
        client.setStatus(cmd.getStatus()); // e.g., PENDING
        client.setIsVerified(cmd.getIsVerified()); // e.g., UNVERIFIED

        clientRepository.save(client);
        // Optional: convert to DTO
        return clientRepository.findClientViewById(client.getId())
                .orElseThrow(() -> new ClientNotFoundException(client.getId()));
    }

    @Transactional
    public ClientView updateCustomer(Long id, ClientUpdateCommand cmd) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        // Update fields (trim strings to avoid whitespace issues)
        client.setFirstName(cmd.getFirstName().trim());
        client.setSecondName(cmd.getSecondName().trim());
        client.setLastName(cmd.getLastName().trim());
        client.setPhoneNumber(cmd.getPhoneNumber().trim());
        client.setEmail(cmd.getEmail().trim());
        client.setGender(cmd.getGender().trim());
        client.setAddress(cmd.getAddress().trim());
        client.setEntityTypes(cmd.getEntityTypes());

        clientRepository.save(client);
        return clientRepository.findClientViewById(client.getId())
                .orElseThrow(() -> new ClientNotFoundException(client.getId()));
    }

    @Transactional
    public void deleteCustomer(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        clientRepository.delete(client); // hard delete; soft delete if needed
    }
}
