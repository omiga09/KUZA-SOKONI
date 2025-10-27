package com.kuza.kuzasokoni.domain.product.entities;


import com.kuza.kuzasokoni.common.audit.Auditable;
import com.kuza.kuzasokoni.domain.product.enums.Currency;
import com.kuza.kuzasokoni.domain.product.enums.InterestMethod;
import com.kuza.kuzasokoni.domain.product.enums.ProductStatus;
import com.kuza.kuzasokoni.domain.product.enums.RepaymentFrequency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products", indexes = {
        @Index(name = "idx_product_name", columnList = "productName"),
        @Index(name = "idx_product_status", columnList = "status")
})
public class Product extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String shortName;

    private BigDecimal minimumPrincipal;
    private BigDecimal maximumPrincipal;

    private BigDecimal interest;

    @Enumerated(EnumType.STRING)
    private InterestMethod interestMethod;

    private String tenurePlan;

    @Enumerated(EnumType.STRING)
    private RepaymentFrequency repaidEvery;

    private Integer repaidFrequency;

    private BigDecimal penaltyPercentage;
    private BigDecimal chargesPercentage;
    private BigDecimal fees;

    private Integer gracePeriodDays;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private BigDecimal collateralPercentage;

    private List<Charge> productCharges;

    @Column(name = "repayment_strategy_id")
    private Long repaymentStrategyId;

}
