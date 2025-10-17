package com.kuza.kuzasokoni.domain.loan.entities;


import com.kuza.kuzasokoni.common.audit.Auditable;
import com.kuza.kuzasokoni.domain.loan.enums.ScheduleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "repayment_schedule", indexes = {
        @Index(name = "idx_schedule_expected_date", columnList = "expectedDate"),
        @Index(name = "idx_schedule_paid_date", columnList = "paidDate")
})
public class RepaymentSchedule extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer days;

    private LocalDate expectedDate;
    private LocalDate paidDate;

    private BigDecimal principalDue;
    private BigDecimal outstanding;
    private BigDecimal interest;
    private BigDecimal fees;
    private BigDecimal penalty;
    private Integer lateBy;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    private BigDecimal totalDue;
    private BigDecimal totalPaid;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;



}
