package com.kuza.kuzasokoni.domain.product.dtos.command;

public record RepaymentStrategyCommand(
        String name,
        Boolean includePrincipal,
        Boolean includeInterest,
        Boolean includePenalty,
        Boolean includeFees,
        Boolean includeCharges
) {}
