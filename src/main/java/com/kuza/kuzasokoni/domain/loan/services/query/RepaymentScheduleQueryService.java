package com.kuza.kuzasokoni.domain.loan.services.query;

import com.kuza.kuzasokoni.domain.loan.dtos.query.RepaymentScheduleView;

import java.util.List;

public interface RepaymentScheduleQueryService {
    List<RepaymentScheduleView> getByLoan(Long loanId);
}
