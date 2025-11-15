package com.kuza.kuzasokoni.domain.loan.dtos.command;

import com.kuza.kuzasokoni.domain.loan.enums.LoanStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class LoanCloseCommand {
    private Long loanId;
    private LoanStatus status;
    private LocalDate closedDate;
}
