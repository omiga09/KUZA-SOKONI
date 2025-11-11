package com.kuza.kuzasokoni.domain.loan.services.command;

import com.kuza.kuzasokoni.domain.loan.dtos.command.*;
import com.kuza.kuzasokoni.domain.loan.entities.Loan;
import com.kuza.kuzasokoni.domain.loan.entities.LoanRestructureHistory;
import com.kuza.kuzasokoni.domain.loan.entities.RepaymentSchedule;
import com.kuza.kuzasokoni.domain.loan.enums.LoanRestructured;
import com.kuza.kuzasokoni.domain.loan.enums.LoanStatus;
import com.kuza.kuzasokoni.domain.loan.exceptions.LoanNotFoundException;
import com.kuza.kuzasokoni.domain.loan.mappers.LoanCommandMapper;
import com.kuza.kuzasokoni.domain.loan.repositories.LoanRepository;
import com.kuza.kuzasokoni.domain.loan.repositories.LoanRestructureHistoryRepository;
import com.kuza.kuzasokoni.domain.loan.repositories.RepaymentScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanCommandServiceImpl implements LoanCommandService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    LoanCommandMapper mapper;

    @Autowired
    TransactionsCommandService transactionService;

    private final RepaymentScheduleGenerationService repaymentScheduleGenerationService;
    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final LoanRestructureHistoryRepository loanRestructureHistoryRepository;

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

        LocalDate repaymentStartDate = cmd.getDisbursedDate()
                .plusDays(loan.getGracePeriodDays());

// sasa tumia repaymentStartDate kwenye generator
        List<RepaymentSchedule> schedules =
                repaymentScheduleGenerationService.generateSchedule(loan, repaymentStartDate);

        loan.setRepaymentSchedules(schedules);

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

    @Transactional
    public Loan restructureLoan(LoanRestructureCommand cmd) {
        Loan loan = loanRepository.findById(cmd.getLoanId())
                .orElseThrow(() -> new LoanNotFoundException(cmd.getLoanId()));

        // --- Hifadhi historia ya mabadiliko ---
        LoanRestructureHistory history = new LoanRestructureHistory();
        history.setLoan(loan);
        history.setOldPrincipal(loan.getPrincipal());
        history.setNewPrincipal(cmd.getNewPrincipal());
        history.setOldInterestRate(loan.getAnnualInterestRate());
        history.setNewInterestRate(cmd.getNewInterestRate());
        history.setOldInstallments(loan.getNumberOfInstallments());
        history.setNewInstallments(cmd.getNewNumberOfInstallments());
        history.setEffectiveDate(cmd.getEffectiveDate());
        history.setReason(cmd.getReason());
        loanRestructureHistoryRepository.save(history);

        // --- Update loan details ---
        loan.setIsLoanRestructured(LoanRestructured.TRUE);
        loan.setRestructurePlanId(cmd.getRestructurePlanId());

        if (cmd.getNewPrincipal() != null) {
            loan.setPrincipal(cmd.getNewPrincipal());
            loan.setOutstanding(cmd.getNewPrincipal());
        }
        if (cmd.getNewInterestRate() != null) {
            loan.setAnnualInterestRate(cmd.getNewInterestRate());
        }
        if (cmd.getNewNumberOfInstallments() != null) {
            loan.setNumberOfInstallments(cmd.getNewNumberOfInstallments());
        }

        if (cmd.getEffectiveDate() != null) {
            loan.setApprovalDate(cmd.getEffectiveDate());
        }

        // --- Hesabu repayment start date ---
        LocalDate repaymentStartDate = (loan.getDisbursedDate() != null)
                ? loan.getDisbursedDate().plusDays(
                loan.getGracePeriodDays() != null ? loan.getGracePeriodDays() : 0)
                : cmd.getEffectiveDate();

        // --- Futa repayment schedules za zamani kwa usalama ---
        // 1. Toa reference kutoka persistence context
        loan.getRepaymentSchedules().clear();
        loanRepository.flush(); // force Hibernate kupersist clear() kabla ya kuunda mpya

        // 2. Futa kwenye DB
        repaymentScheduleRepository.deleteAllByLoanId(loan.getId());

        // --- Tengeneza repayment schedules mpya ---
        List<RepaymentSchedule> newSchedules =
                repaymentScheduleGenerationService.generateSchedule(loan, repaymentStartDate);

        // Link kila schedule na loan
        newSchedules.forEach(s -> s.setLoan(loan));
        loan.getRepaymentSchedules().addAll(newSchedules);

        // --- Rekodi transaction ya restructure ---
        transactionService.logRestructure(loan);

        // --- Save marekebisho yote ---
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
