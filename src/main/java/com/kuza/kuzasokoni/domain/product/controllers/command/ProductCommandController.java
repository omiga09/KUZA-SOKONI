package com.kuza.kuzasokoni.domain.product.controllers.command;


import com.kuza.kuzasokoni.domain.product.dtos.command.ProductCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ProductUpdateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Product;
import com.kuza.kuzasokoni.domain.product.services.command.ProductCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/api/products")
    @RequiredArgsConstructor
    public class ProductCommandController {

        private final ProductCommandService productCommandService;

        @PostMapping
        public ResponseEntity<Product> create(@RequestBody ProductCreateCommand cmd) {
            Product product = productCommandService.createProduct(cmd);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody ProductUpdateCommand cmd) {
            cmd.setId(id);
            Product product = productCommandService.updateProduct(cmd);
            return ResponseEntity.ok(product);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            productCommandService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }
    }

