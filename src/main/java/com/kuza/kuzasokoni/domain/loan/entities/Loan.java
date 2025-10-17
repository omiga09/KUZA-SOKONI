package com.kuza.kuzasokoni.domain.loan.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kuza.kuzasokoni.common.audit.Auditable;
import com.kuza.kuzasokoni.domain.client.entities.Client;
import com.kuza.kuzasokoni.domain.loan.enums.LoanRestructured;
import com.kuza.kuzasokoni.domain.loan.enums.LoanStatus;
import com.kuza.kuzasokoni.domain.loan.enums.Tenure;
import com.kuza.kuzasokoni.domain.product.entities.Product;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "loans", indexes = {
        @Index(name = "idx_loan_status", columnList = "status"),
        @Index(name = "idx_loan_client", columnList = "client_id"),
        @Index(name = "idx_loan_product", columnList = "product_id")
})
public class Loan extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    private LocalDate disbursedDate;
    private LocalDate approvalDate;
    private LocalDate closedDate;

    private Integer gracePeriodDays;
    private BigDecimal penaltyAmount;
    private BigDecimal principal;
    private BigDecimal outstanding;
    private BigDecimal fees;
    private BigDecimal charges;
    private BigDecimal interest;

    @Enumerated(EnumType.STRING)
    private Tenure tenure;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @Enumerated(EnumType.STRING)
    private LoanRestructured isLoanRestructured;

    @Column(name = "restructure_plan_id")
    private Long restructurePlanId;

    private String collateral;
    private BigDecimal collateralAmount;

    @ManyToOne
    @JoinColumn(name = "repayment_schedule_id")
    private RepaymentSchedule repaymentSchedule;


}
