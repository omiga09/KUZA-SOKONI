package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.domain.product.dtos.query.ProductView;

import java.util.List;
import java.util.Optional;

public interface ProductQueryService {
    List<ProductView> getAllProducts();
    Optional<ProductView> getProductById(Long id);

}
