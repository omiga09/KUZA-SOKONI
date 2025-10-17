package com.kuza.kuzasokoni.domain.loan.services.query;

import com.kuza.kuzasokoni.domain.loan.dtos.query.LoanRepaymentView;
import com.kuza.kuzasokoni.domain.loan.exceptions.LoanNotFoundException;
import com.kuza.kuzasokoni.domain.loan.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanQueryServiceImpl implements LoanQueryService {

  @Autowired
  LoanRepository loanRepository;

    public List<LoanRepaymentView> getRepaymentStatus() {
        return loanRepository.findAllRepaymentViews();
    }

    public LoanRepaymentView getLoanById(Long loanId) {
        return loanRepository.findRepaymentViewById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(loanId));
    }

}
