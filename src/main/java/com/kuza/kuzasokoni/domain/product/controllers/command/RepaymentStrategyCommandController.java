package com.kuza.kuzasokoni.domain.product.controllers.command;


import com.kuza.kuzasokoni.domain.product.dtos.command.RepaymentStrategyCommand;
import com.kuza.kuzasokoni.domain.product.entities.RepaymentStrategy;
import com.kuza.kuzasokoni.domain.product.services.command.RepaymentStrategyCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/repayment-strategies")
@RequiredArgsConstructor
public class RepaymentStrategyCommandController {


    private final RepaymentStrategyCommandService service;

    @PostMapping
    public ResponseEntity<RepaymentStrategy> create(@RequestBody RepaymentStrategyCommand command) {
        return ResponseEntity.ok(service.create(command));
    }
}
