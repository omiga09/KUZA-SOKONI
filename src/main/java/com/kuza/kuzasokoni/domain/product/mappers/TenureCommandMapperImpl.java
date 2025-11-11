package com.kuza.kuzasokoni.domain.product.mappers;

import com.kuza.kuzasokoni.domain.product.dtos.command.TenureCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.TenureUpdateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Product;
import com.kuza.kuzasokoni.domain.product.entities.Tenure;

public class TenureCommandMapperImpl implements TenureCommandMapper{

    @Override
    public TenureCreateCommand toDto(Tenure tenure) {
        TenureCreateCommand dto = new TenureCreateCommand();
        dto.setId(tenure.getId());
        dto.setPenaltyGroupOne(tenure.getPenaltyGroupOne());
        dto.setPenaltyGroupTwo(tenure.getPenaltyGroupTwo());
        dto.setPenaltyGroupThree(tenure.getPenaltyGroupThree());
        dto.setPenaltyGroupFour(tenure.getPenaltyGroupFour());

        dto.setPhase1Days(tenure.getPhase1Days());
        dto.setPhase2Days(tenure.getPhase2Days());
        dto.setPhase3Days(tenure.getPhase3Days());
        dto.setPhase4Days(tenure.getPhase4Days());

        dto.setPenaltyCap(tenure.getPenaltyCap());
        dto.setGracePeriodDays(tenure.getGracePeriodDays());

        dto.setInterest(tenure.getInterest());

        dto.setNumberOfDays(tenure.getNumberOfDays());

        dto.setProductId(tenure.getProduct() != null ? tenure.getProduct().getId() : null);

        return dto;
    }

    @Override
    public Tenure toEntity(TenureCreateCommand dto, Product product) {
        Tenure tenure = new Tenure();
        tenure.setId(dto.getId());
        tenure.setPenaltyGroupOne(dto.getPenaltyGroupOne());
        tenure.setPenaltyGroupTwo(dto.getPenaltyGroupTwo());
        tenure.setPenaltyGroupThree(dto.getPenaltyGroupThree());
        tenure.setPenaltyGroupFour(dto.getPenaltyGroupFour());

        tenure.setPhase1Days(dto.getPhase1Days());
        tenure.setPhase2Days(dto.getPhase2Days());
        tenure.setPhase3Days(dto.getPhase3Days());
        tenure.setPhase4Days(dto.getPhase4Days());

        tenure.setPenaltyCap(dto.getPenaltyCap());
        tenure.setGracePeriodDays(dto.getGracePeriodDays());

        tenure.setInterest(dto.getInterest());

        tenure.setNumberOfDays(dto.getNumberOfDays());

        tenure.setProduct(product);

        return tenure;
    }
    public Tenure toEntity(TenureUpdateCommand cmd, Product product) {
        Tenure tenure = new Tenure();
        tenure.setId(cmd.getId());
        tenure.setPenaltyGroupOne(cmd.getPenaltyGroupOne());
        tenure.setPenaltyGroupTwo(cmd.getPenaltyGroupTwo());
        tenure.setPenaltyGroupThree(cmd.getPenaltyGroupThree());
        tenure.setPenaltyGroupFour(cmd.getPenaltyGroupFour());
        tenure.setPhase1Days(cmd.getPhase1Days());
        tenure.setPhase2Days(cmd.getPhase2Days());
        tenure.setPhase3Days(cmd.getPhase3Days());
        tenure.setPhase4Days(cmd.getPhase4Days());
        tenure.setPenaltyCap(cmd.getPenaltyCap());
        tenure.setGracePeriodDays(cmd.getGracePeriodDays());
        tenure.setInterest(cmd.getInterest());
        tenure.setNumberOfDays(cmd.getNumberOfDays());
        tenure.setProduct(product);
        return tenure;
    }

}
