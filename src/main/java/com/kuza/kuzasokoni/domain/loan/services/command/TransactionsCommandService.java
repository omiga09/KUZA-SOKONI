package com.kuza.kuzasokoni.domain.loan.services.command;

import com.kuza.kuzasokoni.domain.loan.entities.Loan;
import com.kuza.kuzasokoni.domain.loan.enums.LoanStatus;

import java.math.BigDecimal;

public interface TransactionsCommandService {
    void logDisbursement(Loan loan, BigDecimal amount);;
    void logCharges(Loan loan, BigDecimal penalty, BigDecimal fees, BigDecimal interest, BigDecimal charges);
    void logRestructure(Loan loan);
    void logClosure(Loan loan, LoanStatus status);
    }


