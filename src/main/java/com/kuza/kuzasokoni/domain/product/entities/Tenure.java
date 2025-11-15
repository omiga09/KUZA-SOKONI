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

    @Column(name = "phase1_days")
    private Integer phase1Days;

    @Column(name = "phase2_days")
    private Integer phase2Days;

    @Column(name = "phase3_days")
    private Integer phase3Days;

    @Column(name = "phase4_days")
    private Integer phase4Days;

    @Column(name = "penalty_group_one")
    private BigDecimal penaltyGroupOne;

    @Column(name = "penalty_group_two")
    private BigDecimal penaltyGroupTwo;

    @Column(name = "penalty_group_three")
    private BigDecimal penaltyGroupThree;

    @Column(name = "penalty_group_four")
    private BigDecimal penaltyGroupFour;

    @Column(name = "penalty_cap")
    private Double penaltyCap;

    @Column(name = "grace_period_days")
    private Integer gracePeriodDays;

    @Column(name = "number_of_days")
    private Integer numberOfDays;

    @Column(name="interest")
    private BigDecimal interest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}

