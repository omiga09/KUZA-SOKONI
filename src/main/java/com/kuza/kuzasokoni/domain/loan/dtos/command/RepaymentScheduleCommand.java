package com.kuza.kuzasokoni.domain.loan.dtos.command;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RepaymentScheduleCommand(
        Long loanId,
        Integer installmentNumber,
        Integer days,
        LocalDate expectedDate,
        BigDecimal principalDue,
        BigDecimal outstanding,
        BigDecimal remainingBalance,
        BigDecimal interest,
        BigDecimal fees,
        BigDecimal penalty
) {}
