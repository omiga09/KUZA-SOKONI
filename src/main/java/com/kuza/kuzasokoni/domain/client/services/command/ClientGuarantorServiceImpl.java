package com.kuza.kuzasokoni.domain.client.services.command;

import com.kuza.kuzasokoni.domain.client.dtos.command.GuarantorUpdateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;
import com.kuza.kuzasokoni.domain.client.entities.Client;
import com.kuza.kuzasokoni.domain.client.entities.Guarantor;
import com.kuza.kuzasokoni.domain.client.exceptions.ClientNotFoundException;
import com.kuza.kuzasokoni.domain.client.mappers.GuarantorCommandMapper;
import com.kuza.kuzasokoni.domain.client.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientGuarantorServiceImpl implements GuarantorCommandService {

    private final ClientRepository clientRepository;
    private final GuarantorCommandMapper guarantorMapper;

    @Override
    public ClientView updateGuarantors(GuarantorUpdateCommand cmd) {
        Client client = clientRepository.findById(cmd.getClientId())
                .orElseThrow(() -> new ClientNotFoundException(cmd.getClientId()));

        List<Guarantor> updatedGuarantors = cmd.getGuarantors().stream()
                .map(guarantorMapper::toEntity)
                .toList();

        client.setGuarantors(updatedGuarantors);
        clientRepository.save(client);

        return clientRepository.findClientViewById(client.getId())
                .orElseThrow(() -> new ClientNotFoundException(client.getId()));
    }
}
