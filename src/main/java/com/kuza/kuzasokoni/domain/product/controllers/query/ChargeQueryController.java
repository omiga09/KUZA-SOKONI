package com.kuza.kuzasokoni.domain.product.controllers.query;

import com.kuza.kuzasokoni.domain.product.dtos.query.ChargeView;
import com.kuza.kuzasokoni.domain.product.services.query.ChargeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/charges")
@RequiredArgsConstructor
public class ChargeQueryController {

    private final ChargeQueryService service;

    @GetMapping
    public ResponseEntity<List<ChargeView>> getAll() {
        return ResponseEntity.ok(service.getAllCharges());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargeView> getById(@PathVariable Long id) {
        return service.getChargeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
