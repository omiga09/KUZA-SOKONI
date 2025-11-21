package com.kuza.kuzasokoni.domain.client.contollers.query;

import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.client.dtos.query.ClientGuarantorView;
import com.kuza.kuzasokoni.domain.client.services.query.ClientGuarantorQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LOAN_OFFICER', 'FINANCE')")
public class ClientGuarantorQueryController {

    private final ClientGuarantorQueryService clientGuarantorQueryService;

    @GetMapping("/{clientId}/guarantors")  // ← Fixed path (ilikuwa @GetMapping bila path)
    public ResponseEntity<StandardResponse<ClientGuarantorView>> getGuarantors(@PathVariable Long clientId) {
        ClientGuarantorView guarantors = clientGuarantorQueryService.getGuarantorsByClientId(clientId);
        return ResponseEntity.ok(
                StandardResponse.success(200, "Guarantors fetched successfully", "/api/clients/" + clientId + "/guarantors", guarantors)
        );
    }
}