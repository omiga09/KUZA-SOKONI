package com.kuza.kuzasokoni.domain.client.mappers;

import com.kuza.kuzasokoni.domain.client.dtos.command.ClientCreateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.command.ClientUpdateCommand;
import com.kuza.kuzasokoni.domain.client.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientCommandMapperImpl implements ClientCommandMapper {

    @Autowired
    DocumentationCommandMapper documentationMapper;


    @Override
    public Client toEntity(ClientCreateCommand cmd) {
        Client client = new Client();
        client.setFirstName(cmd.getFirstName());
        client.setSecondName(cmd.getSecondName());
        client.setLastName(cmd.getLastName());
        client.setPhoneNumber(cmd.getPhoneNumber());
        client.setEmail(cmd.getEmail());
        client.setDateOfBirth(cmd.getDateOfBirth());
        client.setGender(cmd.getGender());
        client.setAddress(cmd.getAddress());
        client.setStatus(cmd.getStatus());
        client.setIsVerified(cmd.getIsVerified());
        client.setDocumentation(documentationMapper.toEntity(cmd.getDocumentation()));


        if (cmd.getGuarantors() != null && !cmd.getGuarantors().isEmpty()) {
            client.setGuarantors(cmd.getGuarantors());
        }

        return client;
    }


    @Override
    public void updateEntity(ClientUpdateCommand cmd, Client client) {
        if (cmd.getFirstName() != null) client.setFirstName(cmd.getFirstName());
        if (cmd.getSecondName() != null) client.setSecondName(cmd.getSecondName());
        if (cmd.getLastName() != null) client.setLastName(cmd.getLastName());
        if (cmd.getPhoneNumber() != null) client.setPhoneNumber(cmd.getPhoneNumber());
        if (cmd.getEmail() != null) client.setEmail(cmd.getEmail());
        if (cmd.getDateOfBirth() != null) client.setDateOfBirth(cmd.getDateOfBirth());
        if (cmd.getGender() != null) client.setGender(cmd.getGender());
        if (cmd.getAddress() != null) client.setAddress(cmd.getAddress());
        if (cmd.getDocumentation() != null) {
            client.setDocumentation(documentationMapper.toEntity(cmd.getDocumentation()));
        }
    }
}

