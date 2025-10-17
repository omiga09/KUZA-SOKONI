package com.kuza.kuzasokoni.domain.loan.dtos.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanRestructureCommand {
    private Long loanId;
    private Long restructurePlanId;
}
