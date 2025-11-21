package com.kuza.kuzasokoni.domain.client.contollers.query;

import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.client.dtos.query.DocumentationView;
import com.kuza.kuzasokoni.domain.client.services.query.ClientDocumentationQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/documentation")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'FINANCE')")
public class ClientDocumentationQueryController {

    private final ClientDocumentationQueryService clientDocumentationQueryService;

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse<DocumentationView>> getDocumentation(@PathVariable Long id) {
        DocumentationView doc = clientDocumentationQueryService.getDocumentationByClientId(id);
        return ResponseEntity.ok(
                StandardResponse.success(200, "Documentation fetched successfully", "/api/clients/" + id + "/documentation", doc)
        );
    }
}