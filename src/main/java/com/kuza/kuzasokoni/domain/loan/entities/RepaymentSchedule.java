package com.kuza.kuzasokoni.domain.loan.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kuza.kuzasokoni.common.audit.Auditable;
import com.kuza.kuzasokoni.domain.loan.enums.ScheduleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "repayment_schedule", indexes = {
        @Index(name = "idx_schedule_expected_date", columnList = "expectedDate"),
        @Index(name = "idx_schedule_paid_date", columnList = "paidDate"),
        @Index(name = "idx_schedule_installment_number", columnList = "installmentNumber")
})
public class RepaymentSchedule extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer installmentNumber;
    private Integer days;

    private LocalDate expectedDate;
    private LocalDate paidDate;

    private BigDecimal principalDue;
    private BigDecimal interest;
    private BigDecimal fees;
    private BigDecimal penalty;

    private BigDecimal remainingBalance;
    private BigDecimal outstanding;

    private Integer lateBy;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    private BigDecimal totalDue;
    private BigDecimal totalPaid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
    @JsonBackReference
    private Loan loan;
}
