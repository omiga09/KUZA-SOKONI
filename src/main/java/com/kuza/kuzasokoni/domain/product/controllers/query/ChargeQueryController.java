package com.kuza.kuzasokoni.domain.product.controllers.query;

import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.product.dtos.query.ChargeView;
import com.kuza.kuzasokoni.domain.product.services.query.ChargeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/charges")
@RequiredArgsConstructor
public class ChargeQueryController {

    private final ChargeQueryService service;

    @GetMapping
    public ResponseEntity<StandardResponse<List<ChargeView>>> getAll() {
        List<ChargeView> charges = service.getAllCharges();
        return ResponseEntity.ok(
                StandardResponse.success(200, "Charges fetched successfully", "/api/charges", charges)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse<ChargeView>> getById(@PathVariable Long id) {
        return service.getChargeById(id)
                .map(charge -> ResponseEntity.ok(
                        StandardResponse.success(200, "Charge fetched successfully", "/api/charges/" + id, charge)
                ))
                .orElse(ResponseEntity.status(404).body(
                        StandardResponse.success(404, "Charge not found", "/api/charges/" + id, null)
                ));
    }
}
