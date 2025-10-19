package com.kuza.kuzasokoni.domain.loan.controllers.query;

import com.kuza.kuzasokoni.domain.loan.dtos.query.RepaymentScheduleView;
import com.kuza.kuzasokoni.domain.loan.services.query.RepaymentScheduleQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/loans/{loanId}/schedules")
@RequiredArgsConstructor
public class RepaymentScheduleQueryController {

    private final RepaymentScheduleQueryService service;

    @GetMapping
    public ResponseEntity<List<RepaymentScheduleView>> getLoanSchedule(@PathVariable Long loanId) {
        return ResponseEntity.ok(service.getByLoan(loanId));
    }
}
