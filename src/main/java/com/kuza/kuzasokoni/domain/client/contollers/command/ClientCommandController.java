package com.kuza.kuzasokoni.domain.client.contollers.command;

import com.kuza.kuzasokoni.domain.client.dtos.command.ClientCreateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.command.ClientUpdateCommand;
import com.kuza.kuzasokoni.domain.client.entities.Client;
import com.kuza.kuzasokoni.domain.client.entities.Documentation;
import com.kuza.kuzasokoni.domain.client.exceptions.ClientNotFoundException;
import com.kuza.kuzasokoni.domain.client.services.command.ClientCommandService;
import com.kuza.kuzasokoni.domain.client.services.command.ClientDocumentationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Validated
public class ClientCommandController {

    private final ClientCommandService clientCommandService;
    private final ClientDocumentationService clientDocumentationService;


    @PostMapping
    public ResponseEntity<Client> create(@Valid @RequestBody ClientCreateCommand cmd) {
        Client client = clientCommandService.createClient(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }


    @PostMapping(value = "/{id}/documentation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Documentation> uploadDocumentation(
            @PathVariable @Min(value = 1, message = "Client ID must be greater than 0") Long id,
            @RequestPart("nidaNumber") @NotBlank(message = "NIDA number is required") String nidaNumber,
            @RequestPart("kitambulishoType") @NotBlank(message = "Kitambulisho type is required") String kitambulishoType,
            @RequestPart("kitambulishoFile") @NotNull(message = "Kitambulisho file is required") MultipartFile kitambulishoFile,
            @RequestPart("baruaFile") @NotNull(message = "Barua file is required") MultipartFile baruaFile
    ) {
        Documentation documentation = clientDocumentationService.uploadDocumentation(
                id, baruaFile, kitambulishoFile, nidaNumber, kitambulishoType
        );
        return ResponseEntity.ok(documentation);
    }

    @PostMapping(value = "/{id}/documentation/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Documentation> updateDocumentation(
            @PathVariable @Min(value = 1, message = "Client ID must be greater than 0") Long id,
            @RequestPart("nidaNumber") @NotBlank(message = "NIDA number is required") String nidaNumber,
            @RequestPart("kitambulishoType") @NotBlank(message = "Kitambulisho type is required") String kitambulishoType,
            @RequestPart("kitambulishoFile") @NotNull(message = "Kitambulisho file is required") MultipartFile kitambulishoFile,
            @RequestPart("baruaFile") @NotNull(message = "Barua file is required") MultipartFile baruaFile
    ) {
        Documentation documentation = clientDocumentationService.updateDocumentation(
                id, baruaFile, kitambulishoFile, nidaNumber, kitambulishoType
        );
        return ResponseEntity.ok(documentation);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Client> update(
            @PathVariable @Min(value = 1, message = "Client ID must be greater than 0") Long id,
            @Valid @RequestBody ClientUpdateCommand cmd
    ) {
        cmd.setId(id);
        Client updated = clientCommandService.updateClient(cmd);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(value = 1, message = "Client ID must be greater than 0") Long id) {
        clientCommandService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
