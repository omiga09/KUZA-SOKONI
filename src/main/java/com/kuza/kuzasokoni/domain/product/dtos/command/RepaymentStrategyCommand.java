package com.kuza.kuzasokoni.domain.product.dtos.command;

public record RepaymentStrategyCommand(
        String name,
        Integer principal,
        Integer interest,
        Integer penalty,
        Integer fees,
        Integer charges
) {}
