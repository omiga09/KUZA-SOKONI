package com.kuza.kuzasokoni.domain.product.controllers.query;

import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.product.dtos.query.ProductView;
import com.kuza.kuzasokoni.domain.product.services.query.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products") // endpoint simplified
@RequiredArgsConstructor
public class ProductQueryController {

    private final ProductQueryService productQueryService;

    /** Get all products with charges and tenures */
    @GetMapping
    public ResponseEntity<StandardResponse<List<ProductView>>> getAllProducts() {
        List<ProductView> products = productQueryService.getAllProducts();
        return ResponseEntity.ok(
                StandardResponse.success(
                        200,
                        "All products fetched successfully",
                        "/api/products",
                        products
                )
        );
    }

    /** Get a single product by ID with charges and tenures */
    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse<ProductView>> getProductById(@PathVariable Long id) {
        ProductView product = productQueryService.getProductById(id);
        return ResponseEntity.ok(
                StandardResponse.success(
                        200,
                        "Product fetched successfully",
                        "/api/products/" + id,
                        product
                )
        );
    }
}
