package com.kuza.kuzasokoni.domain.client.contollers.query;

import com.kuza.kuzasokoni.common.response.PageResponse;
import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;
import com.kuza.kuzasokoni.domain.client.services.query.ClientQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientQueryController {

    private final ClientQueryService clientQueryService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'FINANCE')")
    public ResponseEntity<?> getAllClients(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir
    ) {
        PageResponse<ClientView> clients = clientQueryService.getAllClients(page, size, sortBy, sortDir);

        return ResponseEntity.ok(
                StandardResponse.success(200, "Clients fetched successfully", "/api/client/clients", clients)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'FINANCE')")
    public ResponseEntity<StandardResponse<ClientView>> getClientById(@PathVariable Long id) {
        ClientView client = clientQueryService.getClientById(id);
        return ResponseEntity.ok(
                StandardResponse.success(200, "Client fetched successfully", "/api/client/clients/" + id, client)
        );
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'FINANCE')")
    public ResponseEntity<?> searchClients(
            @RequestParam("q") String q,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        PageResponse<ClientView> data = clientQueryService.searchClients(q, page, size);

        return ResponseEntity.ok(
                StandardResponse.success(200, "Search results fetched", "/api/client/clients/search", data)
        );
    }
}