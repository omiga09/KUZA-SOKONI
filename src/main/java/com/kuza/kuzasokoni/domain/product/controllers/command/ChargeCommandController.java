package com.kuza.kuzasokoni.domain.product.controllers.command;


import com.kuza.kuzasokoni.domain.product.dtos.command.ChargeCreateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Charge;
import com.kuza.kuzasokoni.domain.product.services.command.ChargeCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/charges")
@RequiredArgsConstructor
public class ChargeCommandController {

    private final ChargeCommandService chargeCommandService;

    @PostMapping
    public ResponseEntity<Charge> createCharge(@Valid @RequestBody ChargeCreateCommand cmd) {
        Charge charge = chargeCommandService.createCharge(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).body(charge);
    }

}