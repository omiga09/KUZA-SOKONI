package com.kuza.kuzasokoni.domain.loan.dtos.query;

import com.kuza.kuzasokoni.domain.loan.enums.ScheduleStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RepaymentScheduleView {
    Long getId();
    Integer getInstallmentNumber();
    Integer getDays();
    LocalDate getExpectedDate();
    LocalDate getPaidDate();
    BigDecimal getPrincipalDue();
    BigDecimal getOutstanding();
    BigDecimal getRemainingBalance();
    BigDecimal getInterest();
    BigDecimal getFees();
    BigDecimal getPenalty();
    BigDecimal getTotalDue();
    BigDecimal getTotalPaid();
    Integer getLateBy();
    ScheduleStatus getStatus();
}
