package com.kuza.kuzasokoni.domain.client.contollers.command;

import com.kuza.kuzasokoni.common.repository.ImagesRepository;
import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.client.dtos.command.ClientCreateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.command.ClientUpdateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;
import com.kuza.kuzasokoni.domain.client.dtos.query.DocumentationView;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@Validated
public class ClientCommandController {

    private final ClientCommandService clientCommandService;
    private final ClientDocumentationService clientDocumentationService;
    private final ImagesRepository imagesRepo;

    @PostMapping
    @PreAuthorize("hasAnyRole('LOAN_OFFICER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<ClientView>> create(@Valid @RequestBody ClientCreateCommand cmd) {
        ClientView client = clientCommandService.createClient(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                StandardResponse.success(201, "Client created successfully", "/api/clients", client)
        );
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('LOAN_OFFICER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<ClientView>> update(
            @PathVariable @Min(value = 1, message = "Client ID must be greater than 0") Long id,
            @Valid @RequestBody ClientUpdateCommand cmd
    ) {
        cmd.setId(id);
        ClientView updated = clientCommandService.updateClient(cmd);
        return ResponseEntity.ok(
                StandardResponse.success(200, "Client updated successfully", "/api/clients/" + id, updated)
        );
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<Void>> delete(@PathVariable @Min(value = 1, message = "Client ID must be greater than 0") Long id) {
        clientCommandService.deleteClient(id);
        return ResponseEntity.ok(
                StandardResponse.success(200, "Client deleted successfully", "/api/clients/" + id, null)
        );
    }

    @PostMapping(value = "/{id}/documentation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('LOAN_OFFICER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<DocumentationView>> uploadDocumentation(
            @PathVariable @Min(value = 1, message = "Client ID must be greater than 0") Long id,
            @RequestPart("nidaNumber") @NotBlank(message = "NIDA number is required") String nidaNumber,
            @RequestPart("kitambulishoType") @NotBlank(message = "Kitambulisho type is required") String kitambulishoType,
            @RequestPart("kitambulishoFile") @NotNull(message = "Kitambulisho file is required") MultipartFile kitambulishoFile,
            @RequestPart("baruaFile") @NotNull(message = "Barua file is required") MultipartFile baruaFile
    ) {
        DocumentationView documentation = clientDocumentationService.uploadDocumentation(
                id, baruaFile, kitambulishoFile, nidaNumber, kitambulishoType
        );
        return ResponseEntity.ok(
                StandardResponse.success(200, "Documentation uploaded successfully", "/api/clients/" + id + "/documentation", documentation)
        );
    }


    @PostMapping(value = "/{id}/documentation/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('LOAN_OFFICER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<DocumentationView>> updateDocumentation(
            @PathVariable @Min(value = 1, message = "Client ID must be greater than 0") Long id,
            @RequestPart("nidaNumber") @NotBlank(message = "NIDA number is required") String nidaNumber,
            @RequestPart("kitambulishoType") @NotBlank(message = "Kitambulisho type is required") String kitambulishoType,
            @RequestPart("kitambulishoFile") @NotNull(message = "Kitambulisho file is required") MultipartFile kitambulishoFile,
            @RequestPart("baruaFile") @NotNull(message = "Barua file is required") MultipartFile baruaFile
    ) {
        DocumentationView documentation = clientDocumentationService.updateDocumentation(
                id, baruaFile, kitambulishoFile, nidaNumber, kitambulishoType
        );
        return ResponseEntity.ok(
                StandardResponse.success(200, "Documentation updated successfully", "/api/clients/" + id + "/documentation/update", documentation)
        );
    }

}
