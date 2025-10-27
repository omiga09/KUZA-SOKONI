package com.kuza.kuzasokoni.domain.loan.services.command;

import com.kuza.kuzasokoni.domain.loan.entities.Loan;
import com.kuza.kuzasokoni.domain.loan.entities.RepaymentSchedule;
import com.kuza.kuzasokoni.domain.loan.enums.ScheduleStatus;
import com.kuza.kuzasokoni.domain.loan.repositories.LoanRepository;
import com.kuza.kuzasokoni.domain.loan.repositories.RepaymentScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PenaltyMonitorServiceImpl implements PenaltyMonitorService {

    private final RepaymentScheduleRepository scheduleRepository;
    private final LoanRepository loanRepository;
    private final TransactionsCommandService transactionService;

    @Scheduled(cron = "0 0 2 * * *")
    public void checkLateInstallments() {
        List<RepaymentSchedule> overdueSchedules = scheduleRepository.findAllPendingAndOverdue(LocalDate.now());

        List<Loan> allLoans = loanRepository.findAllWithSchedules();


        for (Loan loan : allLoans) {
            List<RepaymentSchedule> schedules = loan.getRepaymentSchedules();

            BigDecimal totalPenalty = schedules.stream()
                    .map(RepaymentSchedule::getPenalty)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalInterest = schedules.stream()
                    .map(RepaymentSchedule::getInterest)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalFees = schedules.stream()
                    .map(RepaymentSchedule::getFees)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);


            BigDecimal charges = loan.getPrincipal();


            loan.setPenaltyAmount(totalPenalty);
            loan.setInterest(totalInterest);
            loan.setFees(totalFees);
            loan.setCharges(charges);

            loanRepository.save(loan);
        }
    }
}
