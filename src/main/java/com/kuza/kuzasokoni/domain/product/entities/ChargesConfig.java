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

@Entity
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

    private String name;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private RepaymentType repaymentType;

    @Enumerated(EnumType.STRING)
    private CollectedOn collectedOn;

    @Enumerated(EnumType.STRING)
    private ChargeDeductionOn deductedOn;


}
