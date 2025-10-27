package com.kuza.kuzasokoni.domain.client.contollers.command;

import com.kuza.kuzasokoni.domain.client.entities.Client;
import com.kuza.kuzasokoni.domain.client.enums.ClientStatus;
import com.kuza.kuzasokoni.domain.client.enums.VerificationStatus;
import com.kuza.kuzasokoni.domain.client.exceptions.ClientNotFoundException;
import com.kuza.kuzasokoni.domain.client.repositories.ClientRepository;
import com.kuza.kuzasokoni.domain.client.services.command.ClientCommandService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/clients")
@RequiredArgsConstructor
@Validated
public class AdminClientCommandController {

    private final ClientRepository clientRepository;
    private final ClientCommandService clientCommandService;

    @PutMapping("/{id}/activate")
    public ResponseEntity<Client> activateClient(@PathVariable @Min(value = 1, message = "Client ID must be greater than 0") Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        if (client.getStatus() == ClientStatus.ACTIVATED) {
            throw new IllegalStateException("Client is already activated");
        }

        client.setStatus(ClientStatus.ACTIVATED);
        clientRepository.save(client);

        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<Client> verifyClient(@PathVariable @Min(value = 1, message = "Client ID must be greater than 0") Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        if (client.getIsVerified() == VerificationStatus.VERIFIED) {
            throw new IllegalStateException("Client is already verified");
        }

        client.setIsVerified(VerificationStatus.VERIFIED);
        clientRepository.save(client);

        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}/submit")
    public ResponseEntity<Client> submitClient(@PathVariable @Min(value = 1, message = "Client ID must be greater than 0") Long id) {
        Client submitted = clientCommandService.submitClient(id);
        return ResponseEntity.ok(submitted);
    }
}
