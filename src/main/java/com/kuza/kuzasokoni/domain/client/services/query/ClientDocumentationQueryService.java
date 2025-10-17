package com.kuza.kuzasokoni.domain.client.services.query;

import com.kuza.kuzasokoni.domain.client.dtos.query.DocumentationView;

import java.util.Optional;

public interface ClientDocumentationQueryService {
    Optional<DocumentationView> getDocumentationByClientId(Long clientId);
}
