package com.kuza.kuzasokoni.domain.product.dtos.command;

import com.kuza.kuzasokoni.domain.product.enums.CollectedOn;
import com.kuza.kuzasokoni.domain.product.enums.RepaymentType;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "Charge name is required")
    @Size(max = 100, message = "Charge name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Charge amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Charge amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Repayment type is required")
    private RepaymentType repaymentType;

    @NotNull(message = "CollectedOn value is required")
    private CollectedOn collectedOn;
}
