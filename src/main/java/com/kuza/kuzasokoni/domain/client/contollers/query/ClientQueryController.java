package com.kuza.kuzasokoni.domain.client.contollers.query;

import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;
import com.kuza.kuzasokoni.domain.client.services.query.ClientQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client/clients")
@RequiredArgsConstructor
public class ClientQueryController {

    private final ClientQueryService clientQueryService;

    @GetMapping
    public ResponseEntity<StandardResponse<List<ClientView>>> getAllClients() {
        List<ClientView> clients = clientQueryService.getAllClients();
        return ResponseEntity.ok(
                StandardResponse.success(200, "Clients fetched successfully", "/api/client/clients", clients)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse<ClientView>> getClientById(@PathVariable Long id) {
        ClientView client = clientQueryService.getClientById(id);
        return ResponseEntity.ok(
                StandardResponse.success(200, "Client fetched successfully", "/api/client/clients/" + id, client)
        );
    }

}
