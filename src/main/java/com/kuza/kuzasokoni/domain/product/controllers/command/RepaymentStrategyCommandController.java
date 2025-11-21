package com.kuza.kuzasokoni.domain.product.controllers.command;

import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.product.dtos.command.RepaymentStrategyCommand;
import com.kuza.kuzasokoni.domain.product.dtos.query.RepaymentStrategyView;
import com.kuza.kuzasokoni.domain.product.services.command.RepaymentStrategyCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repayment-strategies")
@RequiredArgsConstructor
public class RepaymentStrategyCommandController {

    private final RepaymentStrategyCommandService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<RepaymentStrategyView>> create(@Valid @RequestBody RepaymentStrategyCommand command) {
        RepaymentStrategyView view = service.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                StandardResponse.success(201, "Repayment strategy created successfully", "/api/repayment-strategies/" + view.getId(), view)
        );
    }
}
