package com.kuza.kuzasokoni.domain.client.mappers;

import com.kuza.kuzasokoni.domain.client.dtos.command.ClientCreateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.command.ClientUpdateCommand;
import com.kuza.kuzasokoni.domain.client.entities.Client;
import com.kuza.kuzasokoni.domain.client.entities.Guarantor;
import com.kuza.kuzasokoni.domain.client.enums.ClientStatus;
import com.kuza.kuzasokoni.domain.client.enums.VerificationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        client.setEntityTypes(cmd.getEntityTypes());


        if (cmd.getGuarantors() != null && !cmd.getGuarantors().isEmpty()) {
            List<Guarantor> mappedGuarantors = cmd.getGuarantors().stream()
                    .map(g -> Guarantor.builder()
                            .name(g.getName())
                            .relationship(g.getRelationship())
                            .phoneNumber(g.getPhoneNumber())
                            .address(g.getAddress())
                            .isVerified(g.getIsVerified())
                            .build())
                    .collect(Collectors.toList());

            client.setGuarantors(mappedGuarantors);
        }

        client.setStatus(ClientStatus.PENDING);
        client.setIsVerified(VerificationStatus.UNVERIFIED);

        return client;
    }

    @Override
    public void updateEntity(ClientUpdateCommand cmd, Client client) {
        client.setFirstName(cmd.getFirstName());
        client.setSecondName(cmd.getSecondName());
        client.setLastName(cmd.getLastName());
        client.setPhoneNumber(cmd.getPhoneNumber());
        client.setEmail(cmd.getEmail());
        client.setDateOfBirth(cmd.getDateOfBirth());
        client.setGender(cmd.getGender());
        client.setAddress(cmd.getAddress());

        if (cmd.getGuarantors() != null && !cmd.getGuarantors().isEmpty()) {
            List<Guarantor> mappedGuarantors = cmd.getGuarantors().stream()
                    .map(g -> Guarantor.builder()
                            .name(g.getName())
                            .relationship(g.getRelationship())
                            .phoneNumber(g.getPhoneNumber())
                            .address(g.getAddress())
                            .isVerified(g.getIsVerified())
                            .build())
                    .collect(Collectors.toList());

            client.setGuarantors(mappedGuarantors);
        }
    }
}



