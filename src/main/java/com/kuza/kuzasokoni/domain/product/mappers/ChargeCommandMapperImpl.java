package com.kuza.kuzasokoni.domain.product.mappers;

import com.kuza.kuzasokoni.domain.product.dtos.command.ChargeCreateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Charge;
import com.kuza.kuzasokoni.domain.product.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ChargeCommandMapperImpl implements ChargeCommandMapper {


    @Override
    public Charge toEntity(ChargeCreateCommand cmd) {
        Charge charge = new Charge();
        charge.setName(cmd.getName());
        charge.setAmount(cmd.getAmount());
        charge.setRepaymentType(cmd.getRepaymentType());
        charge.setCollectedOn(cmd.getCollectedOn());
        charge.setDueDate(cmd.getDueDate());

        if (cmd.getProductId() != null) {
            Product product = new Product();
            product.setId(cmd.getProductId());
            charge.setProduct(product);
        }

        return charge;
    }

}
