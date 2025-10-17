package com.kuza.kuzasokoni.domain.product.controllers.command;

import com.kuza.kuzasokoni.domain.product.entities.ChargesConfig;
import com.kuza.kuzasokoni.domain.product.dtos.command.ChargesConfigCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ChargesConfigUpdateCommand;
import com.kuza.kuzasokoni.domain.product.services.command.ChargeConfigCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/charges/config")
@RequiredArgsConstructor
public class ChargeConfigCommandController {

        private final ChargeConfigCommandService service;

        @PostMapping
        public ResponseEntity<ChargesConfig> create(@RequestBody ChargesConfigCreateCommand cmd) {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.createConfig(cmd));
        }

        @PutMapping
        public ResponseEntity<ChargesConfig> update(@RequestBody ChargesConfigUpdateCommand cmd) {
            return ResponseEntity.ok(service.updateConfig(cmd));
        }
    }


