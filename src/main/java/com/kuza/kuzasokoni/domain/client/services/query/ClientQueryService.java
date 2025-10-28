package com.kuza.kuzasokoni.domain.client.services.query;

import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;

import java.util.List;
import java.util.Optional;

public interface ClientQueryService {
    List<ClientView> getAllClients();
    ClientView getClientById(Long id);
}
