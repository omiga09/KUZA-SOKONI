package com.kuza.kuzasokoni.domain.loan.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kuza.kuzasokoni.common.audit.Auditable;
import com.kuza.kuzasokoni.domain.client.entities.Client;
import com.kuza.kuzasokoni.domain.loan.enums.*;
import com.kuza.kuzasokoni.domain.product.entities.Product;
import com.kuza.kuzasokoni.domain.product.enums.InterestMethod;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private InterestMethod interestMethod;

    private BigDecimal annualInterestRate;
    private Integer numberOfInstallments;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @Enumerated(EnumType.STRING)
    private LoanRestructured isLoanRestructured;

     @Column(name = "restructure_plan_id")
     private Long restructurePlanId;

     private String collateral;
     private BigDecimal collateralAmount;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RepaymentSchedule> repaymentSchedules = new ArrayList<>();

}
