package com.kuza.kuzasokoni.domain.client.services.command;

import com.kuza.kuzasokoni.domain.client.dtos.command.ClientCreateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.command.ClientUpdateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;
import com.kuza.kuzasokoni.domain.client.entities.Client;

public interface ClientCommandService {
    ClientView createClient(ClientCreateCommand cmd);
    ClientView updateClient(ClientUpdateCommand cmd);
    void deleteClient(Long id);
    ClientView submitClient(Long id);
}
