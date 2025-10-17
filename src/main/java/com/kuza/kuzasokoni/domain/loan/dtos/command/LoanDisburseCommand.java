package com.kuza.kuzasokoni.domain.loan.dtos.command;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LoanDisburseCommand {
    private Long loanId;
    private LocalDate disbursedDate;
    private BigDecimal amount;
}
