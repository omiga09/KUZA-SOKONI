package com.kuza.kuzasokoni.domain.loan.controllers.query;


import com.kuza.kuzasokoni.domain.loan.dtos.query.LoanRepaymentView;
import com.kuza.kuzasokoni.domain.loan.services.query.LoanQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/loan/loans")
@RequiredArgsConstructor
public class LoanQueryController {

        private final LoanQueryService loanQueryService;

    @GetMapping
    public ResponseEntity<List<LoanRepaymentView>> getAllRepayments() {
        List<LoanRepaymentView> loans = loanQueryService.getRepaymentStatus();
        if (loans.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(loans);
    }


    @GetMapping("/{loanId}")
        public LoanRepaymentView getLoanById(@PathVariable Long loanId) {
            return loanQueryService.getLoanById(loanId);
        }
    }


