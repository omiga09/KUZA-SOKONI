package com.kuza.kuzasokoni.domain.product.controllers.command;

import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.product.dtos.command.ImageCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.query.ImageView;
import com.kuza.kuzasokoni.domain.product.services.command.ImageCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageCommandController {

    private final ImageCommandService service;

    @PostMapping
    public ResponseEntity<StandardResponse<ImageView>> create(@Valid @RequestBody ImageCreateCommand command) {
        ImageView image = service.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                StandardResponse.success(201, "Image created successfully", "/api/images", image)
        );
    }
}
