package com.kuza.kuzasokoni.domain.client.services.command;

import com.kuza.kuzasokoni.domain.client.dtos.command.ClientCreateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.command.ClientUpdateCommand;
import com.kuza.kuzasokoni.domain.client.entities.Client;

public interface ClientCommandService {
    Client createClient(ClientCreateCommand cmd);
    Client updateClient(ClientUpdateCommand cmd);
    void deleteClient(Long id);
    Client submitClient(Long id);
}
