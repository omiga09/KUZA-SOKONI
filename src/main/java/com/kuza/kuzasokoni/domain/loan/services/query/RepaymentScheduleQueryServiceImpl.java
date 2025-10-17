package com.kuza.kuzasokoni.domain.loan.services.query;

import com.kuza.kuzasokoni.domain.loan.dtos.query.RepaymentScheduleView;
import com.kuza.kuzasokoni.domain.loan.repositories.RepaymentScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RepaymentScheduleQueryServiceImpl implements RepaymentScheduleQueryService {

    private final RepaymentScheduleRepository repository;

    @Override
    public List<RepaymentScheduleView> getByLoan(Long loanId) {
        return repository.findByLoanId(loanId);
    }
}
