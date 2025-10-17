package com.kuza.kuzasokoni.domain.client.contollers.command;

import com.kuza.kuzasokoni.domain.client.dtos.command.ClientCreateCommand;
import com.kuza.kuzasokoni.domain.client.entities.Client;
import com.kuza.kuzasokoni.domain.client.services.command.ClientCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientCommandController {

    private final ClientCommandService clientCommandService;

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody ClientCreateCommand cmd) {
        Client client = clientCommandService.createClient(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }
}
