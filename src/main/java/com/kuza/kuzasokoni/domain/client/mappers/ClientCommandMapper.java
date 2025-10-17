package com.kuza.kuzasokoni.domain.client.mappers;

import com.kuza.kuzasokoni.domain.client.dtos.command.ClientCreateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.command.ClientUpdateCommand;
import com.kuza.kuzasokoni.domain.client.entities.Client;

public interface ClientCommandMapper {
    Client toEntity(ClientCreateCommand cmd);
    void updateEntity(ClientUpdateCommand cmd, Client client);
}
