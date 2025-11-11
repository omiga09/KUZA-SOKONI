package com.kuza.kuzasokoni.domain.product.dtos.command;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenureCreateCommand {

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
