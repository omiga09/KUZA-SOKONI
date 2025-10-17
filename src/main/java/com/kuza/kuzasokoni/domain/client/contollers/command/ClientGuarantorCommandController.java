package com.kuza.kuzasokoni.domain.client.contollers.command;

import com.kuza.kuzasokoni.domain.client.dtos.command.GuarantorCreateCommand;
import com.kuza.kuzasokoni.domain.client.dtos.command.GuarantorUpdateCommand;
import com.kuza.kuzasokoni.domain.client.services.command.GuarantorCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients/{clientId}/guarantors")
@RequiredArgsConstructor
public class ClientGuarantorCommandController {

    private final GuarantorCommandService clientGuarantorService;

    @PutMapping
    public ResponseEntity<Void> updateGuarantors(
            @PathVariable Long clientId,
            @RequestBody List<GuarantorCreateCommand> guarantors
    ) {
        GuarantorUpdateCommand cmd = GuarantorUpdateCommand.builder()
                .clientId(clientId)
                .guarantors(guarantors)
                .build();

        clientGuarantorService.updateGuarantors(cmd);
        return ResponseEntity.noContent().build();
    }
}
