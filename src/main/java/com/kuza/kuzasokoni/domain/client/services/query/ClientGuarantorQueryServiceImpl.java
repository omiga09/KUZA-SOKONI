package com.kuza.kuzasokoni.domain.client.services.query;

import com.kuza.kuzasokoni.domain.client.dtos.query.ClientGuarantorView;
import com.kuza.kuzasokoni.domain.client.exceptions.ClientNotFoundException;
import com.kuza.kuzasokoni.domain.client.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientGuarantorQueryServiceImpl implements ClientGuarantorQueryService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public ClientGuarantorView getGuarantorsByClientId(Long clientId) {
        return clientRepository.findClientGuarantors(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));
    }

}
