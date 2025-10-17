package com.kuza.kuzasokoni.domain.client.services.query;

import com.kuza.kuzasokoni.domain.client.dtos.query.DocumentationView;
import com.kuza.kuzasokoni.domain.client.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientDocumentationQueryServiceImpl implements ClientDocumentationQueryService {

    private final ClientRepository clientRepository;

    @Override
    public Optional<DocumentationView> getDocumentationByClientId(Long clientId) {
        return clientRepository.findDocumentationByClientId(clientId);
    }
}
