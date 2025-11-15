package com.kuza.kuzasokoni.domain.loan.services.command;

import com.kuza.kuzasokoni.domain.loan.dtos.command.UpdateRepaymentScheduleCommand;
import com.kuza.kuzasokoni.domain.loan.entities.Loan;
import com.kuza.kuzasokoni.domain.loan.entities.RepaymentSchedule;
import com.kuza.kuzasokoni.domain.loan.enums.ScheduleStatus;
import com.kuza.kuzasokoni.domain.loan.repositories.RepaymentScheduleRepository;
import com.kuza.kuzasokoni.domain.product.enums.CollectedOn;
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
    public List<RepaymentSchedule> generateSchedule(Loan loan, LocalDate repaymentStartDate) {

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

        // Calculate monthly fee from product charges
        BigDecimal monthlyFee = BigDecimal.ZERO;
        if (loan.getProduct() != null && loan.getProduct().getCharges() != null) {
            monthlyFee = loan.getProduct().getCharges().stream()
                    .filter(charge -> charge.getCollectedOn() == CollectedOn.INSTALLMENT)
                    .map(charge -> charge.getAmount().divide(BigDecimal.valueOf(installments), 2, RoundingMode.HALF_UP))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        if (method == InterestMethod.FLAT) {
            BigDecimal monthlyPrincipal = principal.divide(BigDecimal.valueOf(installments), 2, RoundingMode.HALF_UP);
            BigDecimal totalInterest = principal.multiply(annualRate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            BigDecimal monthlyInterest = totalInterest.divide(BigDecimal.valueOf(installments), 2, RoundingMode.HALF_UP);

            for (int i = 1; i <= installments; i++) {
                LocalDate dueDate = repaymentStartDate.plusMonths(i - 1); // ← fix
                RepaymentSchedule schedule = new RepaymentSchedule();
                schedule.setInstallmentNumber(i);
                schedule.setExpectedDate(dueDate);
                schedule.setPrincipalDue(monthlyPrincipal);
                schedule.setInterest(monthlyInterest);
                schedule.setFees(monthlyFee);
                schedule.setOutstanding(remainingBalance);
                remainingBalance = remainingBalance.subtract(monthlyPrincipal);
                schedule.setRemainingBalance(remainingBalance.max(BigDecimal.ZERO));
                schedule.setTotalDue(monthlyPrincipal.add(monthlyInterest).add(monthlyFee));
                schedule.setTotalPaid(BigDecimal.ZERO);
                schedule.setStatus(ScheduleStatus.PENDING);
                schedule.setLoan(loan);
                schedule.setDays(30);
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
                LocalDate dueDate = repaymentStartDate.plusMonths(i - 1); // ← fix

                RepaymentSchedule schedule = new RepaymentSchedule();
                schedule.setInstallmentNumber(i);
                schedule.setExpectedDate(dueDate);
                schedule.setPrincipalDue(principalComponent);
                schedule.setInterest(interest);
                schedule.setFees(monthlyFee);
                schedule.setOutstanding(remainingBalance);
                remainingBalance = remainingBalance.subtract(principalComponent);
                schedule.setRemainingBalance(remainingBalance.max(BigDecimal.ZERO));
                schedule.setTotalDue(principalComponent.add(interest).add(monthlyFee));
                schedule.setTotalPaid(BigDecimal.ZERO);
                schedule.setStatus(ScheduleStatus.PENDING);
                schedule.setLoan(loan);
                schedule.setDays(30);
                schedule.setPenalty(BigDecimal.ZERO);
                schedule.setLateBy(0);
                schedules.add(schedule);
            }
        }

        return scheduleRepository.saveAll(schedules);
    }

    public void updateRepaymentSchedule(UpdateRepaymentScheduleCommand cmd) {
        RepaymentSchedule schedule = scheduleRepository.findById(cmd.scheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        // Ongeza malipo
        schedule.setTotalPaid(schedule.getTotalPaid().add(cmd.paymentAmount()));

        // Angalia kama installment imelipwa kikamilifu
        if (schedule.getTotalPaid().compareTo(schedule.getTotalDue()) >= 0) {
            schedule.setStatus(ScheduleStatus.PAID);
        } else {
            schedule.setStatus(ScheduleStatus.PENDING);
        }

        // Angalia overdue
        if (cmd.paymentDate().isAfter(schedule.getExpectedDate())
                && schedule.getTotalPaid().compareTo(schedule.getTotalDue()) < 0) {
            schedule.setStatus(ScheduleStatus.OVERDUE);
            schedule.setLateBy((int) java.time.temporal.ChronoUnit.DAYS
                    .between(schedule.getExpectedDate(), cmd.paymentDate()));
        }

        scheduleRepository.save(schedule);
    }

}
