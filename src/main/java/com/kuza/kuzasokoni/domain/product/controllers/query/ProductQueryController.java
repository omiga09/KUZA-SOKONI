package com.kuza.kuzasokoni.domain.product.controllers.query;

import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.product.dtos.query.ProductView;
import com.kuza.kuzasokoni.domain.product.services.query.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductQueryController {

    private final ProductQueryService productQueryService;

    @GetMapping

    public ResponseEntity<?> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        return ResponseEntity.ok(
                StandardResponse.success(
                        200,
                        "Fetched successfully",
                        "/api/products",
                        productQueryService.getAllProducts(page, size, sortBy, sortDir)
                )
        );
    }

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
