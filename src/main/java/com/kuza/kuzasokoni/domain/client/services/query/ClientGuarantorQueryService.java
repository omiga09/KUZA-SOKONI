package com.kuza.kuzasokoni.domain.client.services.query;

import com.kuza.kuzasokoni.domain.client.dtos.query.ClientGuarantorView;

import java.util.Optional;

public interface ClientGuarantorQueryService {
    Optional<ClientGuarantorView> getGuarantorsByClientId(Long clientId);
}
