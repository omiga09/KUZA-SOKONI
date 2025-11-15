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
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateCommand {

    private String productName;
    private String shortName;

    private BigDecimal minimumPrincipal;
    private BigDecimal maximumPrincipal;

    private InterestMethod interestMethod;
    private RepaymentFrequency repaidEvery;
    private Currency currency;
    private ProductStatus status;
    private Integer overdue_days;
    private Integer npa_days;


    private BigDecimal collateralPercentage;
    private BigDecimal interestMin;
    private BigDecimal interestMax;

    private Long repaymentStrategyId;

    private List<ChargeCreateCommand> charges;
    private List<TenureCreateCommand> tenures;
}
