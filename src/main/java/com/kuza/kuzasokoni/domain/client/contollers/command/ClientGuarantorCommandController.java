package com.kuza.kuzasokoni.domain.client.contollers.command;

import com.kuza.kuzasokoni.domain.client.dtos.command.GuarantorCreateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.command.GuarantorUpdateCommand;
import com.kuza.kuzasokoni.domain.client.services.command.GuarantorCommandService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients/{clientId}/guarantors")
@RequiredArgsConstructor
@Validated
public class ClientGuarantorCommandController {

    private final GuarantorCommandService clientGuarantorService;

    @PutMapping
    public ResponseEntity<Void> updateGuarantors(
            @PathVariable @Min(value = 1, message = "Client ID must be greater than 0") Long clientId,
            @RequestBody @Valid List<GuarantorCreateCommand> guarantors
    ) {
        GuarantorUpdateCommand cmd = GuarantorUpdateCommand.builder()
                .clientId(clientId)
                .guarantors(guarantors)
                .build();

        clientGuarantorService.updateGuarantors(cmd);
        return ResponseEntity.noContent().build();
    }
}
