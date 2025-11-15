package com.kuza.kuzasokoni.domain.loan.services.command;

import com.kuza.kuzasokoni.domain.loan.dtos.command.*;
import com.kuza.kuzasokoni.domain.loan.entities.Loan;

public interface LoanCommandService {
    Loan initiateLoan(LoanInitiateCommand cmd);
    Loan approveLoan(LoanApproveCommand cmd);
    Loan disburseLoan(LoanDisburseCommand cmd);
    Loan applyCharges(LoanPenaltyCommand cmd);
    Loan restructureLoan(LoanRestructureCommand cmd);
    Loan closeLoan(LoanCloseCommand cmd);
    void deleteLoan(Long loanId);

}
