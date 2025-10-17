package com.kuza.kuzasokoni.domain.product.dtos.command;

import com.kuza.kuzasokoni.domain.product.enums.CollectedOn;
import com.kuza.kuzasokoni.domain.product.enums.RepaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargesConfigCreateCommand {
    private Long id;
    private String name;
    private BigDecimal amount;
    private RepaymentType repaymentType;
    private CollectedOn collectedOn;
}
