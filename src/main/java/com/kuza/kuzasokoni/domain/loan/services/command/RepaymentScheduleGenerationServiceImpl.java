package com.kuza.kuzasokoni.domain.loan.services.command;

import com.kuza.kuzasokoni.domain.loan.entities.Loan;
import com.kuza.kuzasokoni.domain.loan.entities.RepaymentSchedule;
import com.kuza.kuzasokoni.domain.loan.enums.ScheduleStatus;
import com.kuza.kuzasokoni.domain.loan.repositories.RepaymentScheduleRepository;
import com.kuza.kuzasokoni.domain.product.enums.InterestMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RepaymentScheduleGenerationServiceImpl implements RepaymentScheduleGenerationService {

    private final RepaymentScheduleRepository scheduleRepository;

    @Override
    public List<RepaymentSchedule> generateSchedule(Loan loan, LocalDate startDate) {

        if (scheduleRepository.existsByLoanId(loan.getId())) {
            throw new IllegalStateException("Repayment schedule already exists for this loan.");
        }
        List<RepaymentSchedule> schedules = new ArrayList<>();

        BigDecimal principal = loan.getPrincipal();
        BigDecimal annualRate = loan.getAnnualInterestRate();
        int installments = loan.getNumberOfInstallments();
        InterestMethod method = loan.getInterestMethod();

        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_UP);
        BigDecimal remainingBalance = principal;

        if (method == InterestMethod.FLAT) {
            BigDecimal monthlyPrincipal = principal.divide(BigDecimal.valueOf(installments), 2, RoundingMode.HALF_UP);
            BigDecimal totalInterest = principal.multiply(annualRate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            BigDecimal monthlyInterest = totalInterest.divide(BigDecimal.valueOf(installments), 2, RoundingMode.HALF_UP);

            for (int i = 1; i <= installments; i++) {
                LocalDate dueDate = startDate.plusMonths(i - 1);
                RepaymentSchedule schedule = new RepaymentSchedule();
                schedule.setInstallmentNumber(i);
                schedule.setExpectedDate(dueDate);
                schedule.setPrincipalDue(monthlyPrincipal);
                schedule.setInterest(monthlyInterest);
                schedule.setOutstanding(remainingBalance);
                remainingBalance = remainingBalance.subtract(monthlyPrincipal);
                schedule.setRemainingBalance(remainingBalance);
                schedule.setTotalDue(monthlyPrincipal.add(monthlyInterest));
                schedule.setTotalPaid(BigDecimal.ZERO);
                schedule.setStatus(ScheduleStatus.PENDING);
                schedule.setLoan(loan);
                schedule.setDays(30);
                schedule.setFees(BigDecimal.ZERO);
                schedule.setPenalty(BigDecimal.ZERO);
                schedule.setLateBy(0);
                schedules.add(schedule);
            }
        }

        if (method == InterestMethod.DECLINING_BALANCE) {
            BigDecimal r = monthlyRate;
            BigDecimal n = BigDecimal.valueOf(installments);
            BigDecimal numerator = r.multiply(BigDecimal.ONE.add(r).pow(installments));
            BigDecimal denominator = BigDecimal.ONE.add(r).pow(installments).subtract(BigDecimal.ONE);
            BigDecimal emi = principal.multiply(numerator).divide(denominator, 2, RoundingMode.HALF_UP);

            for (int i = 1; i <= installments; i++) {
                BigDecimal interest = remainingBalance.multiply(r).setScale(2, RoundingMode.HALF_UP);
                BigDecimal principalComponent = emi.subtract(interest).setScale(2, RoundingMode.HALF_UP);
                LocalDate dueDate = startDate.plusMonths(i - 1);

                RepaymentSchedule schedule = new RepaymentSchedule();
                schedule.setInstallmentNumber(i);
                schedule.setExpectedDate(dueDate);
                schedule.setPrincipalDue(principalComponent);
                schedule.setInterest(interest);
                schedule.setOutstanding(remainingBalance);
                remainingBalance = remainingBalance.subtract(principalComponent);
                schedule.setRemainingBalance(remainingBalance.max(BigDecimal.ZERO));
                schedule.setTotalDue(emi);
                schedule.setTotalPaid(BigDecimal.ZERO);
                schedule.setStatus(ScheduleStatus.PENDING);
                schedule.setLoan(loan);
                schedule.setDays(30);
                schedule.setFees(BigDecimal.ZERO);
                schedule.setPenalty(BigDecimal.ZERO);
                schedule.setLateBy(0);
                schedules.add(schedule);
            }
        }

        return scheduleRepository.saveAll(schedules);
    }
}
