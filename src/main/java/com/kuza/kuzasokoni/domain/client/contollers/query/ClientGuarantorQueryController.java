package com.kuza.kuzasokoni.domain.client.contollers.query;


import com.kuza.kuzasokoni.domain.client.dtos.query.ClientGuarantorView;
import com.kuza.kuzasokoni.domain.client.services.query.ClientGuarantorQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients/{clientId}/guarantors")
@RequiredArgsConstructor
public class ClientGuarantorQueryController {

    private final ClientGuarantorQueryService clientGuarantorQueryService;

    @GetMapping
    public ResponseEntity<ClientGuarantorView> getGuarantors(@PathVariable Long clientId) {
        return clientGuarantorQueryService.getGuarantorsByClientId(clientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
