package com.kuza.kuzasokoni.domain.product.controllers.query;

import com.kuza.kuzasokoni.common.response.PageResponse;
import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.product.dtos.query.ImageView;
import com.kuza.kuzasokoni.domain.product.services.query.ImageQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageQueryController {

    private final ImageQueryService service;

    @GetMapping
    public ResponseEntity<StandardResponse<PageResponse<ImageView>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResponse<ImageView> images = service.getAll(page, size);
        return ResponseEntity.ok(
                StandardResponse.success(
                        200,
                        "Images fetched successfully",
                        "/api/images",
                        images
                )
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse<ImageView>> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(image -> ResponseEntity.ok(
                        StandardResponse.success(200, "Image fetched successfully", "/api/images/" + id, image)
                ))
                .orElse(ResponseEntity.status(404).body(
                        StandardResponse.success(404, "Image not found", "/api/images/" + id, null)
                ));
    }
}

