package com.kuza.kuzasokoni.domain.loan.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanRestructureHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal oldPrincipal;
    private BigDecimal newPrincipal;
    private BigDecimal oldInterestRate;
    private BigDecimal newInterestRate;
    private Integer oldInstallments;
    private Integer newInstallments;
    private LocalDate effectiveDate;
    private String reason;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

}
