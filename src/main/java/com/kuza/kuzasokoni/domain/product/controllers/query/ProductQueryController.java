package com.kuza.kuzasokoni.domain.product.controllers.query;

import com.kuza.kuzasokoni.domain.product.dtos.query.ProductView;
import com.kuza.kuzasokoni.domain.product.services.query.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product/products")
@RequiredArgsConstructor
public class ProductQueryController {

    private final ProductQueryService productQueryService;

    @GetMapping
    public ResponseEntity<List<ProductView>> getAllProducts() {
        return ResponseEntity.ok(productQueryService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductView> getProductById(@PathVariable Long id) {
        return productQueryService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
