package com.kuza.kuzasokoni.domain.product.services.command;

import com.kuza.kuzasokoni.domain.product.dtos.command.ChargeCreateCommand;
import com.kuza.kuzasokoni.domain.product.entities.Charge;

public interface ChargeCommandService {
    Charge createCharge(ChargeCreateCommand cmd);
    Charge updatePaymentStatus(Long chargeId, java.math.BigDecimal paidAmount);
}
