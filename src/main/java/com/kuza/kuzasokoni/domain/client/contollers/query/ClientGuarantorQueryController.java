package com.kuza.kuzasokoni.domain.client.contollers.query;

import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.client.dtos.query.ClientGuarantorView;
import com.kuza.kuzasokoni.domain.client.services.query.ClientGuarantorQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients/{clientId}/guarantors")
@RequiredArgsConstructor
public class ClientGuarantorQueryController {

    private final ClientGuarantorQueryService clientGuarantorQueryService;

    @GetMapping
    public ResponseEntity<StandardResponse<ClientGuarantorView>> getGuarantors(@PathVariable Long clientId) {
        ClientGuarantorView guarantors = clientGuarantorQueryService.getGuarantorsByClientId(clientId);
        return ResponseEntity.ok(
                StandardResponse.success(200, "Guarantors fetched successfully", "/api/clients/" + clientId + "/guarantors", guarantors)
        );
    }

}
