package com.kuza.kuzasokoni.domain.product.mappers;

import com.kuza.kuzasokoni.domain.product.dtos.command.ChargeCreateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Charge;
import com.kuza.kuzasokoni.domain.product.entities.ChargesConfig;
import com.kuza.kuzasokoni.domain.product.entities.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ChargeCommandMapperImpl implements ChargeCommandMapper {


    @Override
    public Charge toEntity(ChargeCreateCommand cmd, Product savedProduct) {
        Charge charge = new Charge();
        charge.setName(cmd.getName());
        charge.setAmount(cmd.getAmount());
        charge.setRepaymentType(cmd.getRepaymentType());
        charge.setCollectedOn(cmd.getCollectedOn());
        charge.setDeductedOn(cmd.getDeductedOn());
        charge.setDueDate(cmd.getDueDate());


        charge.setProduct(savedProduct);

        charge.setIsPaid(false);
        charge.setRemainingAmount(cmd.getAmount());
        charge.setPaidAmount(BigDecimal.ZERO);

        return charge;
    }


    @Override
    public Charge toEntityFromConfig(ChargesConfig cmd,Product p) {
        Charge charge = new Charge();
        charge.setName(cmd.getName());
        charge.setAmount(cmd.getAmount());
        charge.setRepaymentType(cmd.getRepaymentType());
        charge.setCollectedOn(cmd.getCollectedOn());
        charge.setDeductedOn(cmd.getDeductedOn());
        charge.setProduct(p);
        return charge;
    }



}
