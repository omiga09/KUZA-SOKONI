package com.kuza.kuzasokoni.domain.product.mappers;

import com.kuza.kuzasokoni.domain.product.dtos.command.ChargeCreateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Charge;

public interface ChargeCommandMapper {
    Charge toEntity(ChargeCreateCommand cmd);
}
