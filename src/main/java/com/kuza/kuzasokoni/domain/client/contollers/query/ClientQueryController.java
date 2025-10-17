package com.kuza.kuzasokoni.domain.client.contollers.query;


import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;
import com.kuza.kuzasokoni.domain.client.services.query.ClientQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/client/clients")
@RequiredArgsConstructor
public class ClientQueryController {

    private final ClientQueryService clientQueryService;

    @GetMapping
    public ResponseEntity<List<ClientView>> getAllClients() {
        return ResponseEntity.ok(clientQueryService.getAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientView> getClientById(@PathVariable Long id) {
        return clientQueryService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}