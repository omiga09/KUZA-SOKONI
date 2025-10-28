package com.kuza.kuzasokoni.domain.client.services.query;


import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;
import com.kuza.kuzasokoni.domain.client.exceptions.ClientNotFoundException;
import com.kuza.kuzasokoni.domain.client.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientQueryServiceImpl implements ClientQueryService {

    private final ClientRepository clientRepository;

    @Override
    public List<ClientView> getAllClients() {
        return clientRepository.findAllClientViews();
    }

    @Override
    public ClientView getClientById(Long id) {
        return clientRepository.findClientViewById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }
}


