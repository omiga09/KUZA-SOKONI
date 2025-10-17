package com.kuza.kuzasokoni.domain.product.dtos.query;

import com.kuza.kuzasokoni.domain.product.enums.CollectedOn;
import com.kuza.kuzasokoni.domain.product.enums.RepaymentType;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ChargeView {
    Long getId();
    String getName();
    BigDecimal getAmount();
    RepaymentType getRepaymentType();
    CollectedOn getCollectedOn();
    Boolean getIsPaid();
    BigDecimal getPaidAmount();
    BigDecimal getRemainingAmount();
    LocalDate getDueDate();
    ProductView getProduct();
    }

