package com.kuza.kuzasokoni.domain.product.mappers;

import com.kuza.kuzasokoni.domain.product.dtos.command.ProductCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ProductUpdateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.TenureCreateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Charge;
import com.kuza.kuzasokoni.domain.product.entities.Product;
import com.kuza.kuzasokoni.domain.product.entities.Tenure;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductCommandMapperImpl implements ProductCommandMapper {

    @Override
    public Product toEntity(ProductCreateCommand cmd) {
        Product product = new Product();
        product.setProductName(cmd.getProductName());
        product.setShortName(cmd.getShortName());
        product.setMinimumPrincipal(cmd.getMinimumPrincipal());
        product.setMaximumPrincipal(cmd.getMaximumPrincipal());
        product.setInterestMethod(cmd.getInterestMethod());
        product.setOverdue_days(cmd.getOverdue_days());
        product.setNpa_days(cmd.getNpa_days());
        product.setRepaidEvery(cmd.getRepaidEvery());
        product.setCurrency(cmd.getCurrency());
        product.setStatus(cmd.getStatus());
        product.setInterestMin(cmd.getInterestMin());
        product.setInterestMax(cmd.getInterestMax());
        product.setCollateralPercentage(cmd.getCollateralPercentage());

        // ✅ Map charges if present
        if (cmd.getCharges() != null) {
            Set<Charge> charges = cmd.getCharges().stream()
                    .map(c -> {
                        Charge charge = new Charge();
                        charge.setName(c.getName());
                        charge.setAmount(c.getAmount());
                        charge.setRepaymentType(c.getRepaymentType());
                        charge.setCollectedOn(c.getCollectedOn());
                        charge.setDeductedOn(c.getDeductedOn());
                        charge.setProduct(product);
                        return charge;
                    }).collect(Collectors.toSet());
            product.setCharges(charges);
        }

        // ✅ Map tenures if present
        if (cmd.getTenures() != null) {
            Set<Tenure> tenures = cmd.getTenures().stream()
                    .map(t -> {
                        Tenure tenure = new Tenure();
                        tenure.setNumberOfDays(t.getNumberOfDays());
                        tenure.setInterest(t.getInterest());
                        tenure.setPhase1Days(t.getPhase1Days());
                        tenure.setPhase2Days(t.getPhase2Days());
                        tenure.setPhase3Days(t.getPhase3Days());
                        tenure.setPhase4Days(t.getPhase4Days());
                        tenure.setPenaltyGroupOne(t.getPenaltyGroupOne());
                        tenure.setPenaltyGroupTwo(t.getPenaltyGroupTwo());
                        tenure.setPenaltyGroupThree(t.getPenaltyGroupThree());
                        tenure.setPenaltyGroupFour(t.getPenaltyGroupFour());
                        tenure.setPenaltyCap(t.getPenaltyCap());
                        tenure.setGracePeriodDays(t.getGracePeriodDays());
                        tenure.setProduct(product);
                        return tenure;
                    })
                    .collect(Collectors.toSet()); // collect as Set instead of List
            product.setTenures(tenures);
        }

        return product;
    }

    @Override
    public void updateEntity(ProductUpdateCommand cmd, Product product) {
        if (cmd.getProductName() != null) product.setProductName(cmd.getProductName());
        if (cmd.getShortName() != null) product.setShortName(cmd.getShortName());
        if (cmd.getMinimumPrincipal() != null) product.setMinimumPrincipal(cmd.getMinimumPrincipal());
        if (cmd.getMaximumPrincipal() != null) product.setMaximumPrincipal(cmd.getMaximumPrincipal());
        if (cmd.getInterestMethod() != null) product.setInterestMethod(cmd.getInterestMethod());
        if (cmd.getOverdue_days() != null) product.setOverdue_days(cmd.getOverdue_days());
        if (cmd.getNpa_days() != null) product.setNpa_days(cmd.getNpa_days());
        if (cmd.getRepaidEvery() != null) product.setRepaidEvery(cmd.getRepaidEvery());
        if (cmd.getCurrency() != null) product.setCurrency(cmd.getCurrency());
        if (cmd.getStatus() != null) product.setStatus(cmd.getStatus());
        if (cmd.getCollateralPercentage() != null) product.setCollateralPercentage(cmd.getCollateralPercentage());
        if (cmd.getInterestMin() != null) product.setInterestMin(cmd.getInterestMin());
        if (cmd.getInterestMax() != null) product.setInterestMax(cmd.getInterestMax());

        // ✅ Update charges if provided (replace all or customize logic)
        if (cmd.getCharges() != null) {
            product.getCharges().clear();
            List<Charge> updatedCharges = cmd.getCharges().stream()
                    .map(c -> {
                        Charge charge = new Charge();
                        charge.setName(c.getName());
                        charge.setAmount(c.getAmount());
                        charge.setRepaymentType(c.getRepaymentType());
                        charge.setCollectedOn(c.getCollectedOn());
                        charge.setDeductedOn(c.getDeductedOn());
                        charge.setProduct(product);
                        return charge;
                    }).collect(Collectors.toList());
            product.getCharges().addAll(updatedCharges);
        }

        // ✅ Update tenures if provided
        if (cmd.getTenures() != null) {
            product.getTenures().clear();
            List<Tenure> updatedTenures = cmd.getTenures().stream()
                    .map(t -> {
                        Tenure tenure = new Tenure();
                        tenure.setNumberOfDays(t.getNumberOfDays());
                        tenure.setInterest(t.getInterest());
                        tenure.setPhase1Days(t.getPhase1Days());
                        tenure.setPhase2Days(t.getPhase2Days());
                        tenure.setPhase3Days(t.getPhase3Days());
                        tenure.setPhase4Days(t.getPhase4Days());
                        tenure.setPenaltyGroupOne(t.getPenaltyGroupOne());
                        tenure.setPenaltyGroupTwo(t.getPenaltyGroupTwo());
                        tenure.setPenaltyGroupThree(t.getPenaltyGroupThree());
                        tenure.setPenaltyGroupFour(t.getPenaltyGroupFour());
                        tenure.setPenaltyCap(t.getPenaltyCap());
                        tenure.setGracePeriodDays(t.getGracePeriodDays());
                        tenure.setProduct(product);
                        return tenure;
                    }).collect(Collectors.toList());
            product.getTenures().addAll(updatedTenures);
        }
    }

}
