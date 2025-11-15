package com.kuza.kuzasokoni.domain.loan.dtos.command;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class LoanApproveCommand {
    private Long loanId;
    private LocalDate approvalDate;
}
