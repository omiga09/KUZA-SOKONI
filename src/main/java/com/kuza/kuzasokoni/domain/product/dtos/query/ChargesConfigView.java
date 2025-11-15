package com.kuza.kuzasokoni.domain.product.dtos.query;

import com.kuza.kuzasokoni.domain.product.enums.ChargeDeductionOn;
import com.kuza.kuzasokoni.domain.product.enums.CollectedOn;
import com.kuza.kuzasokoni.domain.product.enums.RepaymentType;

import java.math.BigDecimal;

public interface ChargesConfigView {
    Long getId();
    String getName();
    BigDecimal getAmount();
    RepaymentType getRepaymentType();
    CollectedOn getCollectedOn();
    //ChargeDeductionOn getChargeDeductionOn();
}
