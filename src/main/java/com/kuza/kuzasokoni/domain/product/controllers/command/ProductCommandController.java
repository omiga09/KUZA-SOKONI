package com.kuza.kuzasokoni.domain.product.controllers.command;

import com.kuza.kuzasokoni.common.response.StandardResponse;
import com.kuza.kuzasokoni.domain.product.dtos.command.ProductCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ProductUpdateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.query.ProductView;
import com.kuza.kuzasokoni.domain.product.entities.Product;
import com.kuza.kuzasokoni.domain.product.services.command.ProductCommandService;
import com.kuza.kuzasokoni.domain.product.services.query.ProductQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductCommandController {

    private final ProductCommandService productCommandService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<ProductView>> create(@Valid @RequestBody ProductCreateCommand cmd) {
        ProductView view = productCommandService.createProduct(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                StandardResponse.success(201, "Product created successfully", "/api/products/" + view.getId(), view)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<ProductView>> update(@PathVariable Long id, @Valid @RequestBody ProductUpdateCommand cmd) {
        cmd.setId(id);
        ProductView view = productCommandService.updateProduct(cmd);
        return ResponseEntity.ok(
                StandardResponse.success(200, "Product updated successfully", "/api/products/" + view.getId(), view)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<StandardResponse<?>> delete(@PathVariable Long id) {
        productCommandService.deleteProduct(id);
        return ResponseEntity.ok(
                StandardResponse.success(200, "Product deleted successfully", "/api/products/" + id, null)
        );
    }
}

