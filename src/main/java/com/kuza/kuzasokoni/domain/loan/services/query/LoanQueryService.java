package com.kuza.kuzasokoni.domain.loan.services.query;

import com.kuza.kuzasokoni.common.response.PageResponse;
import com.kuza.kuzasokoni.domain.loan.dtos.query.LoanRepaymentView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LoanQueryService {
    LoanRepaymentView getLoanById(Long loanId);
    PageResponse<LoanRepaymentView> getAllLoans(int page, int size, String sortBy, String sortDir);
    PageResponse<LoanRepaymentView> searchLoans(String q, int page, int size, String sortBy, String sortDir);
}

