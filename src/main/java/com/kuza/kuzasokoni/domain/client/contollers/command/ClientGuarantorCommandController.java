package com.kuza.kuzasokoni.domain.client.contollers.command;

import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.client.dtos.command.GuarantorCreateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.command.GuarantorUpdateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.query.ClientView;
import com.kuza.kuzasokoni.domain.client.services.command.GuarantorCommandService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guarantors")
@RequiredArgsConstructor
@Validated
public class ClientGuarantorCommandController {

    private final GuarantorCommandService clientGuarantorService;

    @PutMapping
    @PreAuthorize("hasAnyRole('LOAN_OFFICER', 'ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<ClientView>> updateGuarantors(
            @PathVariable @Min(value = 1, message = "Client ID must be greater than 0") Long clientId,
            @RequestBody @Valid List<GuarantorCreateCommand> guarantors
    ) {
        GuarantorUpdateCommand cmd = GuarantorUpdateCommand.builder()
                .clientId(clientId)
                .guarantors(guarantors)
                .build();

        ClientView updatedClient = clientGuarantorService.updateGuarantors(cmd);

        return ResponseEntity.ok(
                StandardResponse.success(200, "Guarantors updated successfully", "/api/clients/" + clientId + "/guarantors", updatedClient)
        );
    }
}
