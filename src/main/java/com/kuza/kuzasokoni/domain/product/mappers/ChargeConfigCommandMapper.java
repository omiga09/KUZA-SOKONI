package com.kuza.kuzasokoni.domain.product.mappers;

import com.kuza.kuzasokoni.domain.product.entities.ChargesConfig;
import com.kuza.kuzasokoni.domain.product.dtos.command.ChargesConfigCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ChargesConfigUpdateCommand;

public interface ChargeConfigCommandMapper {
    ChargesConfig toEntity(ChargesConfigCreateCommand cmd);
    void updateEntity(ChargesConfigUpdateCommand cmd, ChargesConfig config);
}
