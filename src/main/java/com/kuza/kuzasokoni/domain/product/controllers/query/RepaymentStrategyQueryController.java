package com.kuza.kuzasokoni.domain.product.controllers.query;

import com.kuza.kuzasokoni.domain.product.dtos.query.RepaymentStrategyView;
import com.kuza.kuzasokoni.domain.product.services.query.RepaymentStrategyQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/repayment/repayment-strategies")
@RequiredArgsConstructor
public class RepaymentStrategyQueryController {

    private final RepaymentStrategyQueryService service;

    @GetMapping
    public ResponseEntity<List<RepaymentStrategyView>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepaymentStrategyView> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
