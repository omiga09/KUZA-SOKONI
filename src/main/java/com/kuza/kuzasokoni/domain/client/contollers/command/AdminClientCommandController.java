package com.kuza.kuzasokoni.domain.client.contollers.command;

import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;
import com.kuza.kuzasokoni.domain.client.entities.Client;
import com.kuza.kuzasokoni.domain.client.enums.ClientStatus;
import com.kuza.kuzasokoni.domain.client.enums.VerificationStatus;
import com.kuza.kuzasokoni.domain.client.exceptions.ClientNotFoundException;
import com.kuza.kuzasokoni.domain.client.repositories.ClientRepository;
import com.kuza.kuzasokoni.domain.client.services.command.ClientCommandService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
public class AdminClientCommandController {

    private final ClientRepository clientRepository;
    private final ClientCommandService clientCommandService;

    @PutMapping("/{id}/activate")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<ClientView>> activateClient(
            @PathVariable @Min(value = 1, message = "Client ID must be greater than 0") Long id) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        if (client.getStatus() == ClientStatus.ACTIVATED) {
            throw new IllegalStateException("Client is already activated");
        }

        client.setStatus(ClientStatus.ACTIVATED);
        clientRepository.save(client);

        ClientView view = clientRepository.findClientViewById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        return ResponseEntity.ok(
                StandardResponse.success(200, "Client activated successfully", "/api/admin/clients/" + id + "/activate", view)
        );
    }


    @PutMapping("/{id}/verify")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<ClientView>> verifyClient(
            @PathVariable @Min(value = 1, message = "Client ID must be greater than 0") Long id) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        if (client.getIsVerified() == VerificationStatus.VERIFIED) {
            throw new IllegalStateException("Client is already verified");
        }

        client.setIsVerified(VerificationStatus.VERIFIED);
        clientRepository.save(client);

        ClientView view = clientRepository.findClientViewById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        return ResponseEntity.ok(
                StandardResponse.success(200, "Client verified successfully", "/api/admin/clients/" + id + "/verify", view)
        );
    }

    @PutMapping("/{id}/submit")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<ClientView>> submitClient(
            @PathVariable @Min(value = 1, message = "Client ID must be greater than 0") Long id) {

        ClientView submitted = clientCommandService.submitClient(id);

        return ResponseEntity.ok(
                StandardResponse.success(200, "Client submitted successfully", "/api/admin/clients/" + id + "/submit", submitted)
        );
    }

}
