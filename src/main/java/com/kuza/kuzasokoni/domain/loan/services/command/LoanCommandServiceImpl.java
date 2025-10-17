package com.kuza.kuzasokoni.domain.loan.services.command;

import com.kuza.kuzasokoni.domain.loan.dtos.command.*;
import com.kuza.kuzasokoni.domain.loan.entities.Loan;
import com.kuza.kuzasokoni.domain.loan.enums.LoanRestructured;
import com.kuza.kuzasokoni.domain.loan.enums.LoanStatus;
import com.kuza.kuzasokoni.domain.loan.exceptions.LoanNotFoundException;
import com.kuza.kuzasokoni.domain.loan.mappers.LoanCommandMapper;
import com.kuza.kuzasokoni.domain.loan.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanCommandServiceImpl implements LoanCommandService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    LoanCommandMapper mapper;

    @Autowired
    TransactionsCommandService transactionService;

    public Loan initiateLoan(LoanInitiateCommand cmd) {
        Loan loan = mapper.toInitiatedLoan(cmd);
        return loanRepository.save(loan);
    }

    public Loan approveLoan(LoanApproveCommand cmd) {
        Loan loan = loanRepository.findById(cmd.getLoanId())
                .orElseThrow(() -> new LoanNotFoundException(cmd.getLoanId()));
        loan.setStatus(LoanStatus.APPROVED);
        loan.setApprovalDate(cmd.getApprovalDate());
        return loanRepository.save(loan);
    }

    public Loan disburseLoan(LoanDisburseCommand cmd) {
        Loan loan = loanRepository.findById(cmd.getLoanId())
                .orElseThrow(() -> new LoanNotFoundException(cmd.getLoanId()));
        loan.setStatus(LoanStatus.DISBURSED);
        loan.setDisbursedDate(cmd.getDisbursedDate());
        loan.setOutstanding(cmd.getAmount());
        transactionService.logDisbursement(loan, cmd.getAmount());
        return loanRepository.save(loan);
    }

    public Loan applyCharges(LoanPenaltyCommand cmd) {
        Loan loan = loanRepository.findById(cmd.getLoanId())
                .orElseThrow(() -> new LoanNotFoundException(cmd.getLoanId()));

        loan.setPenaltyAmount(cmd.getPenalty());
        loan.setFees(cmd.getFees());
        loan.setInterest(cmd.getInterest());
        loan.setCharges(cmd.getCharges());

        transactionService.logCharges(
                loan,
                cmd.getPenalty(),
                cmd.getFees(),
                cmd.getInterest(),
                cmd.getCharges()
        );

        return loanRepository.save(loan);
    }


    public Loan restructureLoan(LoanRestructureCommand cmd) {
        Loan loan = loanRepository.findById(cmd.getLoanId())
                .orElseThrow(() -> new LoanNotFoundException(cmd.getLoanId()));
        loan.setIsLoanRestructured(LoanRestructured.TRUE);
        loan.setRestructurePlanId(cmd.getRestructurePlanId());
        transactionService.logRestructure(loan);
        return loanRepository.save(loan);
    }

    public Loan closeLoan(LoanCloseCommand cmd) {
        Loan loan = loanRepository.findById(cmd.getLoanId())
                .orElseThrow(() -> new LoanNotFoundException(cmd.getLoanId()));
        loan.setStatus(cmd.getStatus());
        loan.setClosedDate(cmd.getClosedDate());
        transactionService.logClosure(loan, cmd.getStatus());
        return loanRepository.save(loan);
    }

    public void deleteLoan(Long loanId) {
        loanRepository.deleteById(loanId);
    }


}
