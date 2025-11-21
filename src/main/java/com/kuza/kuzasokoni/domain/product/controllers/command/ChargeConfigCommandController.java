package com.kuza.kuzasokoni.domain.product.controllers.command;

import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.product.dtos.query.ChargesConfigView;
import com.kuza.kuzasokoni.domain.product.dtos.command.ChargesConfigCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ChargesConfigUpdateCommand;
import com.kuza.kuzasokoni.domain.product.services.command.ChargeConfigCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/charges/config")
@RequiredArgsConstructor
public class ChargeConfigCommandController {

    private final ChargeConfigCommandService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<ChargesConfigView>> create(@Valid @RequestBody ChargesConfigCreateCommand cmd) {
        ChargesConfigView view = service.createConfig(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                StandardResponse.success(201, "Charge config created successfully", "/api/charges/config/" + view.getId(), view)
        );
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<ChargesConfigView>> update(@Valid @RequestBody ChargesConfigUpdateCommand cmd) {
        ChargesConfigView view = service.updateConfig(cmd);
        return ResponseEntity.ok(
                StandardResponse.success(200, "Charge config updated successfully", "/api/charges/config/" + view.getId(), view)
        );
    }
}
