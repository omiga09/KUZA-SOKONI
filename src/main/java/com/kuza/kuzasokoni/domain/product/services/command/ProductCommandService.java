package com.kuza.kuzasokoni.domain.product.services.command;

import com.kuza.kuzasokoni.domain.product.dtos.command.ProductCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ProductUpdateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.query.ProductView;
import com.kuza.kuzasokoni.domain.product.entities.Product;

public interface ProductCommandService {
    ProductView createProduct(ProductCreateCommand cmd);
    ProductView updateProduct(ProductUpdateCommand cmd);
    void deleteProduct(Long id);
}

