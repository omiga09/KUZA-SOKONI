package com.kuza.kuzasokoni.domain.loan.dtos.command;

import com.kuza.kuzasokoni.domain.loan.enums.Tenure;
import com.kuza.kuzasokoni.domain.product.enums.InterestMethod;
import lombok.*;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanInitiateCommand {
    private Long clientId;
    private Long productId;
    private BigDecimal principal;
    private Tenure tenure;
    private Integer gracePeriodDays;
    private String collateral;
    private BigDecimal collateralAmount;
    private Long repaymentScheduleId;
    private BigDecimal annualInterestRate;
    private InterestMethod interestMethod;
    private Integer numberOfInstallments;

}
