package com.kuza.kuzasokoni.domain.product.services.command;


import com.kuza.kuzasokoni.domain.product.dtos.command.ChargeCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ProductCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ProductUpdateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.TenureCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.query.ProductView;
import com.kuza.kuzasokoni.domain.product.entities.*;;
import com.kuza.kuzasokoni.domain.product.exception.ProductNotFoundException;
import com.kuza.kuzasokoni.domain.product.mappers.ChargeCommandMapper;
import com.kuza.kuzasokoni.domain.product.mappers.ProductCommandMapper;
import com.kuza.kuzasokoni.domain.product.mappers.TenureCommandMapper;

import com.kuza.kuzasokoni.domain.product.repositories.ChargeRepository;
import com.kuza.kuzasokoni.domain.product.repositories.ProductRepository;
import com.kuza.kuzasokoni.domain.product.repositories.RepaymentStrategyRepository;
import com.kuza.kuzasokoni.domain.product.repositories.TenureRepository;
import com.kuza.kuzasokoni.domain.product.services.query.ProductQueryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;
     private final ChargeRepository chargesRepository;
    private final TenureRepository tenureRepository;
    private final ProductCommandMapper mapper;
    private final RepaymentStrategyRepository repaymentStrategyRepository;
    private final ChargeCommandMapper cMapper;
    private final TenureCommandMapper tenureMapper;
    private final ProductQueryService productQueryService;

    @Override
    @Transactional
    public ProductView createProduct(ProductCreateCommand cmd) {
        // 1. Hakikisha repayment strategy ipo
        RepaymentStrategy strategy = repaymentStrategyRepository.findById(cmd.getRepaymentStrategyId())
                .orElseThrow(() -> new RuntimeException("Repayment strategy not found"));

        // 2. Map command -> product entity
        Product product = mapper.toEntity(cmd);
        product.setRepaymentStrategy(strategy);

        // 3. Save product kwanza
        Product savedProduct = productRepository.saveAndFlush(product);

        // 4. Handle charges
        List<ChargeCreateCommand> chargeCommands =
                Optional.ofNullable(cmd.getCharges()).orElse(Collections.emptyList());

        if (!chargeCommands.isEmpty()) {
            List<Charge> pCharges = chargeCommands.stream()
                    .map(chargeCmd -> cMapper.toEntity(chargeCmd, savedProduct))
                    .toList();
            chargesRepository.saveAllAndFlush(pCharges);
        }

        // 5. Handle tenures
        List<TenureCreateCommand> tenureCommands =
                Optional.ofNullable(cmd.getTenures()).orElse(Collections.emptyList());

        if (!tenureCommands.isEmpty()) {
            List<Tenure> pTenures = tenureCommands.stream()
                    .map(tenureCmd -> tenureMapper.toEntity(tenureCmd, savedProduct))
                    .toList();
            tenureRepository.saveAllAndFlush(pTenures);
        }

        // 6. Return product view (query side)
        return productQueryService.getProductById(savedProduct.getId());
    }



    @Override
    public ProductView updateProduct(ProductUpdateCommand cmd) {
        Product product = productRepository.findById(cmd.getId())
                .orElseThrow(() -> new ProductNotFoundException(cmd.getId()));
        mapper.updateEntity(cmd, product);
        Product updated = productRepository.save(product);
        return productQueryService.getProductById(updated.getId());
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }
}

