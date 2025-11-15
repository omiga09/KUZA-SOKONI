package com.kuza.kuzasokoni.domain.client.services.query;

import com.kuza.kuzasokoni.domain.client.dtos.query.DocumentationView;
import com.kuza.kuzasokoni.domain.client.exceptions.ClientNotFoundException;
import com.kuza.kuzasokoni.domain.client.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientDocumentationQueryServiceImpl implements ClientDocumentationQueryService {

    private final ClientRepository clientRepository;

    @Override
    public DocumentationView getDocumentationByClientId(Long clientId) {
        return clientRepository.findDocumentationViewByClientId(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));
    }

}
