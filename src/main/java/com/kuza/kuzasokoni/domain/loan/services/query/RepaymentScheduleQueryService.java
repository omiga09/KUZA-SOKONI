package com.kuza.kuzasokoni.domain.loan.services.query;

import com.kuza.kuzasokoni.domain.loan.dtos.query.RepaymentScheduleView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RepaymentScheduleQueryService {
    Page<RepaymentScheduleView> getByLoan(Long loanId, Pageable pageable);
}
