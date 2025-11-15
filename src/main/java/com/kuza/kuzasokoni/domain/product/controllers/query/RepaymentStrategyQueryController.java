package com.kuza.kuzasokoni.domain.product.controllers.query;

import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.product.dtos.query.RepaymentStrategyView;
import com.kuza.kuzasokoni.domain.product.services.query.RepaymentStrategyQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repayment/repayment-strategies")
@RequiredArgsConstructor
public class RepaymentStrategyQueryController {

    private final RepaymentStrategyQueryService service;

    @GetMapping
    public ResponseEntity<StandardResponse<List<RepaymentStrategyView>>> getAll() {
        List<RepaymentStrategyView> strategies = service.getAll();
        return ResponseEntity.ok(
                StandardResponse.success(200, "Repayment strategies fetched successfully", "/api/repayment/repayment-strategies", strategies)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse<RepaymentStrategyView>> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(strategy -> ResponseEntity.ok(
                        StandardResponse.success(200, "Repayment strategy fetched successfully", "/api/repayment/repayment-strategies/" + id, strategy)
                ))
                .orElse(ResponseEntity.status(404).body(
                        StandardResponse.success(404, "Repayment strategy not found", "/api/repayment/repayment-strategies/" + id, null)
                ));
    }
}
