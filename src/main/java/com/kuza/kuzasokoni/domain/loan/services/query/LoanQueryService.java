package com.kuza.kuzasokoni.domain.loan.services.query;

import com.kuza.kuzasokoni.domain.loan.dtos.query.LoanRepaymentView;

import java.util.List;

public interface LoanQueryService {
    List<LoanRepaymentView> getRepaymentStatus();
    LoanRepaymentView getLoanById(Long loanId);
}
