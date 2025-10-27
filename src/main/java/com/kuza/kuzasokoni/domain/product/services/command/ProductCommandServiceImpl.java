package com.kuza.kuzasokoni.domain.product.services.command;


import com.kuza.kuzasokoni.domain.product.dtos.command.ProductCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ProductUpdateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Charge;
import com.kuza.kuzasokoni.domain.product.entities.ChargesConfig;
import com.kuza.kuzasokoni.domain.product.entities.Product;
import com.kuza.kuzasokoni.domain.product.exception.ChargeNotFoundException;
import com.kuza.kuzasokoni.domain.product.exception.ProductNotFoundException;
import com.kuza.kuzasokoni.domain.product.mappers.ChargeCommandMapper;
import com.kuza.kuzasokoni.domain.product.mappers.ProductCommandMapper;
import com.kuza.kuzasokoni.domain.product.repositories.ChargeRepository;
import com.kuza.kuzasokoni.domain.product.repositories.ChargesConfigRepository;
import com.kuza.kuzasokoni.domain.product.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;
    private final ChargesConfigRepository chargesConfigRepo;
    private final ChargeRepository chargesRepo;
    private final ProductCommandMapper mapper;
    private final ChargeCommandMapper cMapper;

    @Override
    public Product createProduct(ProductCreateCommand cmd) {
        Product product = mapper.toEntity(cmd);
        productRepository.save(product);

        List<ChargesConfig> cfg = cmd.getChargeConfigId().stream()
                .map(id -> chargesConfigRepo.findById(id)
                        .orElseThrow(() -> new ChargeNotFoundException(id)))
                .toList();

        List<Charge> pCharges = new java.util.ArrayList<>(List.of());

        for (ChargesConfig chargesConfig : cfg) {
            pCharges.add(cMapper.toEntityFromConfig(chargesConfig,product));
        }

        chargesRepo.saveAllAndFlush(pCharges);
        return product;
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