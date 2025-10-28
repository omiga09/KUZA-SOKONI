package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.domain.product.dtos.query.ProductView;

import java.util.List;

public interface ProductQueryService {
    List<ProductView> getAllProducts();
    ProductView getProductById(Long id);
}
