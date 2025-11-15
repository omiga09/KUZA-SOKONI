package com.kuza.kuzasokoni.domain.product.mappers;

import com.kuza.kuzasokoni.domain.product.entities.ChargesConfig;
import com.kuza.kuzasokoni.domain.product.dtos.command.ChargesConfigCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ChargesConfigUpdateCommand;
import org.springframework.stereotype.Component;

@Component
public class ChargeConfigCommandMapperImpl implements ChargeConfigCommandMapper {

    @Override
    public ChargesConfig toEntity(ChargesConfigCreateCommand cmd) {
        ChargesConfig config = new ChargesConfig();
        config.setName(cmd.getName());
        config.setAmount(cmd.getAmount());
        config.setRepaymentType(cmd.getRepaymentType());
        config.setCollectedOn(cmd.getCollectedOn());
        return config;
    }

    @Override
    public void updateEntity(ChargesConfigUpdateCommand cmd, ChargesConfig config) {
        config.setName(cmd.getName());
        config.setAmount(cmd.getAmount());
        config.setRepaymentType(cmd.getRepaymentType());
        config.setCollectedOn(cmd.getCollectedOn());
    }
}
