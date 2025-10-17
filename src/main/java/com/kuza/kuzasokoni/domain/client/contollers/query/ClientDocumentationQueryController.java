package com.kuza.kuzasokoni.domain.client.contollers.query;

import com.kuza.kuzasokoni.domain.client.dtos.query.DocumentationView;
import com.kuza.kuzasokoni.domain.client.services.query.ClientDocumentationQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/client/clients")
@RequiredArgsConstructor
public class ClientDocumentationQueryController {

    private final ClientDocumentationQueryService clientDocumentationQueryService;

    @GetMapping("/{id}/documentation")
    public ResponseEntity<DocumentationView> getDocumentation(@PathVariable Long id) {
        return clientDocumentationQueryService.getDocumentationByClientId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
