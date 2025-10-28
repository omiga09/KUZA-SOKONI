package com.kuza.kuzasokoni.domain.product.controllers.query;

import com.kuza.kuzasokoni.common.response.StandardResponse;
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
    public ResponseEntity<StandardResponse<List<ProductView>>> getAllProducts() {
        List<ProductView> products = productQueryService.getAllProducts();
        return ResponseEntity.ok(
                StandardResponse.success(200, "Products fetched successfully", "/api/products", products)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse<ProductView>> getProductById(@PathVariable Long id) {
        ProductView product = productQueryService.getProductById(id);
        return ResponseEntity.ok(
                StandardResponse.success(200, "Product fetched successfully", "/api/products/" + id, product)
        );
    }
}
