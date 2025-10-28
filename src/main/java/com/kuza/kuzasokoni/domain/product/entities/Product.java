package com.kuza.kuzasokoni.domain.product.entities;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.kuza.kuzasokoni.common.audit.Auditable;
import com.kuza.kuzasokoni.domain.product.enums.Currency;
import com.kuza.kuzasokoni.domain.product.enums.InterestMethod;
import com.kuza.kuzasokoni.domain.product.enums.ProductStatus;
import com.kuza.kuzasokoni.domain.product.enums.RepaymentFrequency;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @NotBlank(message = "product name is required")
    private String productName;

    @NotBlank(message = "short name is required")
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

    private Integer gracePeriodDays;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private BigDecimal collateralPercentage;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Charge> charges;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repayment_strategy_id", nullable = false)
    private RepaymentStrategy repaymentStrategy;





}
