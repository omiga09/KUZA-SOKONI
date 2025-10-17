package com.kuza.kuzasokoni.domain.product.mappers;

import com.kuza.kuzasokoni.domain.product.dtos.command.ProductCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ProductUpdateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Product;
import org.springframework.stereotype.Component;


@Component
public class ProductCommandMapperImpl implements ProductCommandMapper {


        @Override
        public Product toEntity(ProductCreateCommand cmd) {
        Product product = new Product();
        product.setProductName(cmd.getProductName());
        product.setShortName(cmd.getShortName());
        product.setMinimumPrincipal(cmd.getMinimumPrincipal());
        product.setMaximumPrincipal(cmd.getMaximumPrincipal());
        product.setInterest(cmd.getInterest());
        product.setInterestMethod(cmd.getInterestMethod());
        product.setTenurePlan(cmd.getTenurePlan());
        product.setRepaidEvery(cmd.getRepaidEvery());
        product.setRepaidFrequency(cmd.getRepaidFrequency());
        product.setPenaltyPercentage(cmd.getPenaltyPercentage());
        product.setChargesPercentage(cmd.getChargesPercentage());
        product.setFees(cmd.getFees());
        product.setGracePeriodDays(cmd.getGracePeriodDays());
        product.setCurrency(cmd.getCurrency());
        product.setStatus(cmd.getStatus());
        product.setCollateralPercentage(cmd.getCollateralPercentage());
        product.setRepaymentStrategyId(cmd.getRepaymentStrategyId());
        return product;
    }

        @Override
        public void updateEntity(ProductUpdateCommand cmd, Product product) {
        if (cmd.getProductName() != null) product.setProductName(cmd.getProductName());
        if (cmd.getShortName() != null) product.setShortName(cmd.getShortName());
        if (cmd.getMinimumPrincipal() != null) product.setMinimumPrincipal(cmd.getMinimumPrincipal());
        if (cmd.getMaximumPrincipal() != null) product.setMaximumPrincipal(cmd.getMaximumPrincipal());
        if (cmd.getInterest() != null) product.setInterest(cmd.getInterest());
        if (cmd.getInterestMethod() != null) product.setInterestMethod(cmd.getInterestMethod());
        if (cmd.getTenurePlan() != null) product.setTenurePlan(cmd.getTenurePlan());
        if (cmd.getRepaidEvery() != null) product.setRepaidEvery(cmd.getRepaidEvery());
        if (cmd.getRepaidFrequency() != null) product.setRepaidFrequency(cmd.getRepaidFrequency());
        if (cmd.getPenaltyPercentage() != null) product.setPenaltyPercentage(cmd.getPenaltyPercentage());
        if (cmd.getChargesPercentage() != null) product.setChargesPercentage(cmd.getChargesPercentage());
        if (cmd.getFees() != null) product.setFees(cmd.getFees());
        if (cmd.getGracePeriodDays() != null) product.setGracePeriodDays(cmd.getGracePeriodDays());
        if (cmd.getCurrency() != null) product.setCurrency(cmd.getCurrency());
        if (cmd.getStatus() != null) product.setStatus(cmd.getStatus());
        if (cmd.getCollateralPercentage() != null) product.setCollateralPercentage(cmd.getCollateralPercentage());
        if (cmd.getRepaymentStrategyId() != null) product.setRepaymentStrategyId(cmd.getRepaymentStrategyId());
    }

}
