package com.kuza.kuzasokoni.domain.product.services.command;

import com.kuza.kuzasokoni.domain.loan.entities.Loan;
import com.kuza.kuzasokoni.domain.product.dtos.command.ChargeCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.query.ChargeView;
import com.kuza.kuzasokoni.domain.product.entities.Charge;

public interface ChargeCommandService {
    ChargeView updatePaymentStatus(Long chargeId, java.math.BigDecimal paidAmount);
    ChargeView createCharge(ChargeCreateCommand cmd);
}


