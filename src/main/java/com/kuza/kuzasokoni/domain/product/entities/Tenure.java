package com.kuza.kuzasokoni.domain.product.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kuza.kuzasokoni.common.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tenures")
public class Tenure extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Penalty groups (percentages)
    private BigDecimal penaltyGroupOne;
    private BigDecimal penaltyGroupTwo;
    private BigDecimal penaltyGroupThree;
    private BigDecimal penaltyGroupFour;

    // Phase days (integers)
    private Integer phase1Days;
    private Integer phase2Days;
    private Integer phase3Days;
    private Integer phase4Days;

    // Penalty cap (integer)
    private Integer penaltyCap;

    // Grace period (integer)
    private Integer gracePeriodDays;

    private BigDecimal interest;

    // Number of days for this tenure
    private Integer numberOfDays;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}

