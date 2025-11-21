package com.kuza.kuzasokoni.domain.client.services.query;

import com.kuza.kuzasokoni.common.response.PageResponse;
import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ClientQueryService {
    PageResponse<ClientView> getAllClients(int page, int size, String sortBy, String sortDir);
    ClientView getClientById(Long id);
    PageResponse<ClientView> searchClients(String search, int page, int size);

}
