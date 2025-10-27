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

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "charges_config", indexes = {
        @Index(name = "idx_config_collected_on", columnList = "collectedOn")
})
public class ChargesConfig extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Charge config name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    @Column(nullable = false)
    private BigDecimal amount;

    @NotNull(message = "Repayment type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RepaymentType repaymentType;

    @NotNull(message = "CollectedOn is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CollectedOn collectedOn;

    @Enumerated(EnumType.STRING)
    private ChargeDeductionOn deductedOn;


}
