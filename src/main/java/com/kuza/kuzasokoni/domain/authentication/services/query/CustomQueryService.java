package com.kuza.kuzasokoni.domain.authentication.services.query;

import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;
import com.kuza.kuzasokoni.domain.client.entities.Client;
import com.kuza.kuzasokoni.domain.client.exceptions.ClientNotFoundException;
import com.kuza.kuzasokoni.domain.client.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomQueryService {

    private final ClientRepository clientRepository;


    public Page<ClientView> getAllCustomers(Pageable pageable) {
        return clientRepository.findAllClientViews(pageable);
    }

    public Page<ClientView> searchCustomers(String search, Pageable pageable) {
        return clientRepository.searchClientViews(search, pageable);
    }

    public ClientView getCustomerById(Long id) {
        return clientRepository.findClientViewById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    // Enable / disable customer
    @Transactional
    public ClientView setCustomerActive(Long id, boolean active) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        client.setActive(active);
        clientRepository.save(client);
        return clientRepository.findClientViewById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }
}

