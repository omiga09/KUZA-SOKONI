package com.kuza.kuzasokoni.domain.product.controllers.query;


import com.kuza.kuzasokoni.domain.product.dtos.query.ChargesConfigView;
import com.kuza.kuzasokoni.domain.product.services.query.ChargesConfigQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
    @RequestMapping("/api/charges/config")
    @RequiredArgsConstructor
    public class ChargesConfigQueryController {

        private final ChargesConfigQueryService service;

        @GetMapping
        public ResponseEntity<List<ChargesConfigView>> getAll() {
            return ResponseEntity.ok(service.getAllConfigs());
        }

        @GetMapping("/{id}")
        public ResponseEntity<ChargesConfigView> getById(@PathVariable Long id) {
            return service.getConfigById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
    }


