package com.kuza.kuzasokoni.domain.loan.controllers.query;

import com.kuza.kuzasokoni.domain.loan.dtos.query.RepaymentScheduleView;
import com.kuza.kuzasokoni.domain.loan.services.query.RepaymentScheduleQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans/{loanId}/schedules")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'FINANCE', 'LOAN_OFFICER')")
public class RepaymentScheduleQueryController {

    private final RepaymentScheduleQueryService service;

    @GetMapping
    public ResponseEntity<Page<RepaymentScheduleView>> getLoanSchedule(
            @PathVariable Long loanId,
            Pageable pageable) {
        return ResponseEntity.ok(service.getByLoan(loanId, pageable));
    }
}