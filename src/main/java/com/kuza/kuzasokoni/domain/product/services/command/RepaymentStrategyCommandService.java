package com.kuza.kuzasokoni.domain.product.services.command;

import com.kuza.kuzasokoni.domain.product.dtos.command.RepaymentStrategyCommand;
import com.kuza.kuzasokoni.domain.product.entities.RepaymentStrategy;

public interface RepaymentStrategyCommandService {
    public RepaymentStrategy create(RepaymentStrategyCommand command);
            ;
}
