package com.kuza.kuzasokoni.domain.client.services.query;

import com.kuza.kuzasokoni.domain.client.dtos.query.ClientGuarantorView;

public interface ClientGuarantorQueryService {
    ClientGuarantorView getGuarantorsByClientId(Long clientId);
}
