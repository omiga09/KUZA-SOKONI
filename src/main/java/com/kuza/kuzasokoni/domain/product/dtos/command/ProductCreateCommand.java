package com.kuza.kuzasokoni.domain.product.dtos.command;


import com.kuza.kuzasokoni.domain.product.enums.Currency;
import com.kuza.kuzasokoni.domain.product.enums.InterestMethod;
import com.kuza.kuzasokoni.domain.product.enums.ProductStatus;
import com.kuza.kuzasokoni.domain.product.enums.RepaymentFrequency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class ProductCreateCommand {
        private String productName;
        private String shortName;
        private BigDecimal minimumPrincipal;
        private BigDecimal maximumPrincipal;
        private BigDecimal interest;
        private InterestMethod interestMethod;
        private String tenurePlan;
        private RepaymentFrequency repaidEvery;
        private Integer repaidFrequency;
        private BigDecimal penaltyPercentage;
        private BigDecimal chargesPercentage;
        private BigDecimal fees;
        private Integer gracePeriodDays;
        private Currency currency;
        private ProductStatus status;
        private BigDecimal collateralPercentage;
        private Long repaymentStrategyId;
}
