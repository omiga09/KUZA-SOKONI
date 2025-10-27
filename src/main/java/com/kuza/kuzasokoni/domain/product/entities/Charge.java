package com.kuza.kuzasokoni.domain.product.entities;

import com.kuza.kuzasokoni.common.audit.Auditable;
import com.kuza.kuzasokoni.domain.product.enums.ChargeDeductionOn;
import com.kuza.kuzasokoni.domain.product.enums.CollectedOn;
import com.kuza.kuzasokoni.domain.product.enums.RepaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "charges", indexes = {
        @Index(name = "idx_charge_product", columnList = "product_id"),
        @Index(name = "idx_charge_collected_on", columnList = "collectedOn")
})
public class Charge extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private RepaymentType repaymentType;

    @Enumerated(EnumType.STRING)
    private CollectedOn collectedOn;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    private ChargeDeductionOn deductedOn;

    private Boolean isPaid = false;
    private BigDecimal paidAmount;
    private BigDecimal remainingAmount;
    private LocalDate dueDate;


}

