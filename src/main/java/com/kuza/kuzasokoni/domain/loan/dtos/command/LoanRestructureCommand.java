package com.kuza.kuzasokoni.domain.loan.dtos.command;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LoanRestructureCommand {

        private Long loanId;
        private Long restructurePlanId;
        private BigDecimal newPrincipal;
        private BigDecimal newInterestRate;
        private Integer newNumberOfInstallments;
       // private Tenure newTenure;
        private LocalDate effectiveDate;
        private String reason;

}


