package com.kuza.kuzasokoni.domain.product.controllers.command;

import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.product.dtos.command.ChargeCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.query.ChargeView;
import com.kuza.kuzasokoni.domain.product.services.command.ChargeCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/charges")
@RequiredArgsConstructor
public class ChargeCommandController {

    private final ChargeCommandService chargeCommandService;

    @PostMapping
    public ResponseEntity<StandardResponse<ChargeView>> createCharge(@Valid @RequestBody ChargeCreateCommand cmd) {
        ChargeView charge = chargeCommandService.createCharge(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                StandardResponse.success(201, "Charge created successfully", "/api/charges/" + charge.getId(), charge)
        );
    }

}
