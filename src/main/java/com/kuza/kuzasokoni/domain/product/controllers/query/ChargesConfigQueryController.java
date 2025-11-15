package com.kuza.kuzasokoni.domain.product.controllers.query;

import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.product.dtos.query.ChargesConfigView;
import com.kuza.kuzasokoni.domain.product.services.query.ChargesConfigQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/charges/config")
@RequiredArgsConstructor
public class ChargesConfigQueryController {

    private final ChargesConfigQueryService service;

    @GetMapping
    public ResponseEntity<StandardResponse<List<ChargesConfigView>>> getAll() {
        List<ChargesConfigView> configs = service.getAllConfigs();
        return ResponseEntity.ok(
                StandardResponse.success(200, "Charge configs fetched successfully", "/api/charges/config", configs)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse<ChargesConfigView>> getById(@PathVariable Long id) {
        return service.getConfigById(id)
                .map(config -> ResponseEntity.ok(
                        StandardResponse.success(200, "Charge config fetched successfully", "/api/charges/config/" + id, config)
                ))
                .orElse(ResponseEntity.status(404).body(
                        StandardResponse.success(404, "Charge config not found", "/api/charges/config/" + id, null)
                ));
    }
}
