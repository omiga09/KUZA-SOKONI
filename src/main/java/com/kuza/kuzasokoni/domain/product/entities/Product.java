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
import java.util.HashSet;
import java.util.Set;

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

        private String shortName;

        private BigDecimal minimumPrincipal;
        private BigDecimal maximumPrincipal;

        @Enumerated(EnumType.STRING)
        private InterestMethod interestMethod;

        @Enumerated(EnumType.STRING)
        private RepaymentFrequency repaidEvery;

        @Enumerated(EnumType.STRING)
        private Currency currency;

        @Enumerated(EnumType.STRING)
        private ProductStatus status;

        private BigDecimal collateralPercentage;
        private BigDecimal interestMin;
        private BigDecimal interestMax;

        private Integer overdue_days;
        private Integer npa_days;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "repayment_strategy_id", nullable = false)
        private RepaymentStrategy repaymentStrategy;

       @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
       private Set<Charge> charges = new HashSet<>();

       @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
       private Set<Tenure> tenures = new HashSet<>();


    @PrePersist
        @PreUpdate
        private void ensureShortName() {
            if (this.shortName == null || this.shortName.isBlank()) {
                this.shortName = generateShortName(this.productName);
            }
        }
        private String generateShortName(String name) {
            if (name == null || name.isBlank()) return null;


            StringBuilder sb = new StringBuilder();
            for (String part : name.split("\\s+")) {
                sb.append(part.charAt(0));
            }
            return sb.toString().toUpperCase();
        }
    }

