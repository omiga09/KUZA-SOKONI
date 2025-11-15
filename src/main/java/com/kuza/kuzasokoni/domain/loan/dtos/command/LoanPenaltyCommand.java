package com.kuza.kuzasokoni.domain.loan.dtos.command;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LoanPenaltyCommand {
    private Long loanId;
    private BigDecimal penalty;
    private BigDecimal fees;
    private BigDecimal interest;
    private BigDecimal charges;
}
