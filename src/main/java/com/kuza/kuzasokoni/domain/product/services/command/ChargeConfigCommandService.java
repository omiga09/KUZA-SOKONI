package com.kuza.kuzasokoni.domain.product.services.command;

import com.kuza.kuzasokoni.domain.product.dtos.query.ChargesConfigView;
import com.kuza.kuzasokoni.domain.product.entities.ChargesConfig;
import com.kuza.kuzasokoni.domain.product.dtos.command.ChargesConfigCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.command.ChargesConfigUpdateCommand;

public interface ChargeConfigCommandService {
    ChargesConfigView createConfig(ChargesConfigCreateCommand cmd);
    ChargesConfigView updateConfig(ChargesConfigUpdateCommand cmd);
}
