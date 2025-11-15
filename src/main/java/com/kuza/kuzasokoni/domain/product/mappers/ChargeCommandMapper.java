package com.kuza.kuzasokoni.domain.product.mappers;

import com.kuza.kuzasokoni.domain.product.dtos.command.ChargeCreateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Charge;
import com.kuza.kuzasokoni.domain.product.entities.ChargesConfig;
import com.kuza.kuzasokoni.domain.product.entities.Product;

public interface ChargeCommandMapper {
    Charge toEntity(ChargeCreateCommand cmd, Product savedProduct);
    Charge toEntityFromConfig(ChargesConfig cmd, Product p);
}
