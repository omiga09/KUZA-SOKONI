package com.kuza.kuzasokoni.domain.product.dtos.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class TenureUpdateCommand {
        private Long id;
        private BigDecimal penaltyGroupOne;
        private BigDecimal penaltyGroupTwo;
        private BigDecimal penaltyGroupThree;
        private BigDecimal penaltyGroupFour;

        private Integer phase1Days;
        private Integer phase2Days;
        private Integer phase3Days;
        private Integer phase4Days;

        private Integer penaltyCap;
        private Integer gracePeriodDays;

        private BigDecimal interest;

        private Integer numberOfDays;

        private Long productId;
    }


