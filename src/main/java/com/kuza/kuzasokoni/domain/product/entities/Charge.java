package com.kuza.kuzasokoni.domain.product.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kuza.kuzasokoni.common.audit.Auditable;
import com.kuza.kuzasokoni.domain.product.enums.ChargeDeductionOn;
import com.kuza.kuzasokoni.domain.product.enums.CollectedOn;
import com.kuza.kuzasokoni.domain.product.enums.RepaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @NotBlank(message = "Charge name is required")
    @Size(max = 100, message = "Charge name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Charge amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Repayment type is required")
    @Enumerated(EnumType.STRING)
    private RepaymentType repaymentType;

    @NotNull(message = "CollectedOn is required")
    @Enumerated(EnumType.STRING)
    private CollectedOn collectedOn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    private ChargeDeductionOn deductedOn;

    @NotNull(message = "isPaid status is required")
    private Boolean isPaid = false;

    @DecimalMin(value = "0.0", message = "Paid amount must be zero or positive")
    private BigDecimal paidAmount;

    @DecimalMin(value = "0.0", message = "Remaining amount must be zero or positive")
    private BigDecimal remainingAmount;

    private LocalDate dueDate;


}

