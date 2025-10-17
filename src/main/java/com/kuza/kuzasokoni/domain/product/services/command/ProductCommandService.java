package com.kuza.kuzasokoni.domain.product.services.command;

import com.kuza.kuzasokoni.domain.product.dtos.command.ProductCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ProductUpdateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Product;

public interface ProductCommandService {
    Product createProduct(ProductCreateCommand cmd);
    Product updateProduct(ProductUpdateCommand cmd);
    void deleteProduct(Long id);
}
