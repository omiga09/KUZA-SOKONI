package com.kuza.kuzasokoni.domain.loan.dtos.command;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateRepaymentScheduleCommand(
        Long scheduleId,
        BigDecimal paymentAmount,
        LocalDate paymentDate
) {}
