package com.kuza.kuzasokoni.domain.loan.controllers.command;

import com.kuza.kuzasokoni.domain.loan.dtos.command.UpdateRepaymentScheduleCommand;
import com.kuza.kuzasokoni.domain.loan.entities.Loan;
import com.kuza.kuzasokoni.domain.loan.entities.RepaymentSchedule;
import com.kuza.kuzasokoni.domain.loan.repositories.LoanRepository;
import com.kuza.kuzasokoni.domain.loan.services.command.RepaymentScheduleGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class RepaymentScheduleCommandController {

    private final LoanRepository loanRepository;
    private final RepaymentScheduleGenerationService repaymentScheduleService;

    @PostMapping("/{loanId}/generate-schedule")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<RepaymentSchedule>> generateSchedule(
            @PathVariable Long loanId,
            @RequestParam(required = false) LocalDate startDate
    ) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        LocalDate effectiveStartDate = startDate != null ? startDate : loan.getDisbursedDate();


        List<RepaymentSchedule> schedules = repaymentScheduleService.generateSchedule(loan, effectiveStartDate);

        return ResponseEntity.ok(schedules);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Void> updateSchedule(@RequestBody UpdateRepaymentScheduleCommand cmd) {
        repaymentScheduleService.updateRepaymentSchedule(cmd);
        return ResponseEntity.ok().build();
    }
}
