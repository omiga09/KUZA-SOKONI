package com.kuza.kuzasokoni.domain.loan.controllers.query;

import com.kuza.kuzasokoni.common.response.PageResponse;
import com.kuza.kuzasokoni.domain.loan.dtos.query.LoanRepaymentView;
import com.kuza.kuzasokoni.domain.loan.services.query.LoanQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan-query")
@RequiredArgsConstructor
public class LoanQueryController {

    private final LoanQueryService loanQueryService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'FINANCE', 'LOAN_OFFICER')")
    public ResponseEntity<?> getAllLoans(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        PageResponse<LoanRepaymentView> loans =
                loanQueryService.getAllLoans(page, size, sortBy, sortDir);

        return ResponseEntity.ok(loans);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'FINANCE', 'LOAN_OFFICER')")
    public ResponseEntity<PageResponse<LoanRepaymentView>> searchLoans(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return ResponseEntity.ok(loanQueryService.searchLoans(q, page, size, sortBy, sortDir));
    }

    @GetMapping("/{loanId}")
    public LoanRepaymentView getLoanById(@PathVariable Long loanId) {
        return loanQueryService.getLoanById(loanId);
    }
}