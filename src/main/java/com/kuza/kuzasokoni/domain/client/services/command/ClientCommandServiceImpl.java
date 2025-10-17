package com.kuza.kuzasokoni.domain.client.services.command;

import com.kuza.kuzasokoni.domain.client.dtos.command.ClientCreateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.command.ClientUpdateCommand;
import com.kuza.kuzasokoni.domain.client.entities.Client;
import com.kuza.kuzasokoni.domain.client.exceptions.ClientNotFoundException;
import com.kuza.kuzasokoni.domain.client.mappers.ClientCommandMapper;
import com.kuza.kuzasokoni.domain.client.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientCommandServiceImpl implements ClientCommandService {

    private final ClientRepository clientRepository;
    private final ClientCommandMapper mapper;

    @Transactional
    @Override
    public Client createClient(ClientCreateCommand cmd) {
        Client client = mapper.toEntity(cmd);
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(ClientUpdateCommand cmd) {
        Client client = clientRepository.findById(cmd.getId())
                .orElseThrow(() -> new ClientNotFoundException(cmd.getId()));
        mapper.updateEntity(cmd, client);
        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        clientRepository.delete(client);
    }
}
