package com.kuza.kuzasokoni.domain.product.dtos.command;

import com.kuza.kuzasokoni.domain.product.enums.Currency;
import com.kuza.kuzasokoni.domain.product.enums.InterestMethod;
import com.kuza.kuzasokoni.domain.product.enums.ProductStatus;
import com.kuza.kuzasokoni.domain.product.enums.RepaymentFrequency;
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
public class ProductCreateCommand {

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name must not exceed 100 characters")
    private String productName;

    @NotBlank(message = "Short name is required")
    @Size(max = 50, message = "Short name must not exceed 50 characters")
    private String shortName;

    @NotNull(message = "Minimum principal is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Minimum principal must be greater than 0")
    private BigDecimal minimumPrincipal;

    @NotNull(message = "Maximum principal is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Maximum principal must be greater than 0")
    private BigDecimal maximumPrincipal;

    @NotNull(message = "Interest is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Interest must be greater than 0")
    private BigDecimal interest;

    @NotNull(message = "Interest method is required")
    private InterestMethod interestMethod;

    @NotBlank(message = "Tenure plan is required")
    private String tenurePlan;

    @NotNull(message = "Repayment frequency is required")
    private RepaymentFrequency repaidEvery;

    @NotNull(message = "Repaid frequency is required")
    @Min(value = 1, message = "Repaid frequency must be at least 1")
    private Integer repaidFrequency;

    @DecimalMin(value = "0.0", message = "Penalty percentage must be at least 0")
    private BigDecimal penaltyPercentage;

    @DecimalMin(value = "0.0", message = "Charges percentage must be at least 0")
    private BigDecimal chargesPercentage;

    @DecimalMin(value = "0.0", message = "Fees must be at least 0")
    private BigDecimal fees;

    @NotNull(message = "Grace period days is required")
    @Min(value = 0, message = "Grace period days must be 0 or more")
    private Integer gracePeriodDays;

    @NotNull(message = "Currency is required")
    private Currency currency;

    @NotNull(message = "Status is required")
    private ProductStatus status;

    @DecimalMin(value = "0.0", message = "Collateral percentage must be at least 0")
    @DecimalMax(value = "100.0", message = "Collateral percentage must not exceed 100")
    private BigDecimal collateralPercentage;

    @NotNull(message = "Repayment strategy ID is required")
    private Long repaymentStrategyId;

    // private List<Long> charges;
    // private List<Long> overDues;
}
