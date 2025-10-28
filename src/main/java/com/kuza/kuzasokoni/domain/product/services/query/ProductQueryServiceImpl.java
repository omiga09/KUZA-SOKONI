package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.domain.product.dtos.query.ProductView;
import com.kuza.kuzasokoni.domain.product.exception.ProductNotFoundException;
import com.kuza.kuzasokoni.domain.product.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductView> getAllProducts() {
        return productRepository.findAllProductViews();
    }

    @Override
    public ProductView getProductById(Long id) {
        return productRepository.findProductViewById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
