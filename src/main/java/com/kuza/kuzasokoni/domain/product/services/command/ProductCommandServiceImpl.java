package com.kuza.kuzasokoni.domain.product.services.command;


import com.kuza.kuzasokoni.domain.product.dtos.command.ProductCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ProductUpdateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Product;
import com.kuza.kuzasokoni.domain.product.exception.ProductNotFoundException;
import com.kuza.kuzasokoni.domain.product.mappers.ProductCommandMapper;
import com.kuza.kuzasokoni.domain.product.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;
    private final ProductCommandMapper mapper;

    @Override
    public Product createProduct(ProductCreateCommand cmd) {
        Product product = mapper.toEntity(cmd);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(ProductUpdateCommand cmd) {
        Product product = productRepository.findById(cmd.getId())
                .orElseThrow(() -> new ProductNotFoundException(cmd.getId()));
        mapper.updateEntity(cmd, product);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }
}