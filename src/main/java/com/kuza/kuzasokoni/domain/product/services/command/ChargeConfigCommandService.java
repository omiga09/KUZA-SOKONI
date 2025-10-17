package com.kuza.kuzasokoni.domain.product.services.command;

import com.kuza.kuzasokoni.domain.product.entities.ChargesConfig;
import com.kuza.kuzasokoni.domain.product.dtos.command.ChargesConfigCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ChargesConfigUpdateCommand;

public interface ChargeConfigCommandService {
    ChargesConfig createConfig(ChargesConfigCreateCommand cmd);
    ChargesConfig updateConfig(ChargesConfigUpdateCommand cmd);
}
