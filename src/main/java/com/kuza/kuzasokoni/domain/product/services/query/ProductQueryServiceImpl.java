package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.domain.product.dtos.query.ProductView;
import com.kuza.kuzasokoni.domain.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ProductView> getAllProducts() {
        return productRepository.findAllProductViews();
    }

    @Override
    public Optional<ProductView> getProductById(Long id) {
        return productRepository.findProductViewById(id);
    }
}
