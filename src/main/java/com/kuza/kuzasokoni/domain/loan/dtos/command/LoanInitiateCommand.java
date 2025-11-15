package com.kuza.kuzasokoni.domain.loan.dtos.command;

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
    private String collateral;
    private BigDecimal collateralAmount;
    private Long repaymentScheduleId;
    private BigDecimal annualInterestRate;
    private InterestMethod interestMethod;
    private Integer numberOfInstallments;

}
