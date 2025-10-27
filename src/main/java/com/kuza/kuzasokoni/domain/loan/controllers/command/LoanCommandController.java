package com.kuza.kuzasokoni.domain.loan.controllers.command;


import com.kuza.kuzasokoni.domain.loan.dtos.command.*;
import com.kuza.kuzasokoni.domain.loan.entities.Loan;
import com.kuza.kuzasokoni.domain.loan.services.command.LoanCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanCommandController {

    private final LoanCommandService loanCommandService;

    @PostMapping
    public ResponseEntity<Loan> initiate(@RequestBody LoanInitiateCommand cmd) {
        Loan loan = loanCommandService.initiateLoan(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).body(loan);
    }

    @PutMapping("/approve")
    public ResponseEntity<Loan> approve(@RequestBody LoanApproveCommand cmd) {
        Loan loan = loanCommandService.approveLoan(cmd);
        return ResponseEntity.ok(loan);
    }

    @PutMapping("/disburse")
    public ResponseEntity<Loan> disburse(@RequestBody LoanDisburseCommand cmd) {
        Loan loan = loanCommandService.disburseLoan(cmd);
        return ResponseEntity.ok(loan);
    }

    @PutMapping("/restructure")
    public ResponseEntity<Loan> restructure(@RequestBody LoanRestructureCommand cmd) {
        Loan loan = loanCommandService.restructureLoan(cmd);
        return ResponseEntity.ok(loan);
    }

    @PutMapping("/close")
    public ResponseEntity<Loan> close(@RequestBody LoanCloseCommand cmd) {
        Loan loan = loanCommandService.closeLoan(cmd);
        return ResponseEntity.ok(loan);
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> delete(@PathVariable Long loanId) {
        loanCommandService.deleteLoan(loanId);
        return ResponseEntity.noContent().build();
    }
}
