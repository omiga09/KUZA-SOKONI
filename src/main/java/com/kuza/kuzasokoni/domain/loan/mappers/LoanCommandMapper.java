package com.kuza.kuzasokoni.domain.loan.mappers;

import com.kuza.kuzasokoni.domain.loan.dtos.command.LoanInitiateCommand;
import com.kuza.kuzasokoni.domain.loan.entities.Loan;

public interface LoanCommandMapper {
    Loan toInitiatedLoan(LoanInitiateCommand cmd);

}
