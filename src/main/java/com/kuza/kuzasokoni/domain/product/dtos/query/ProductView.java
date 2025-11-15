package com.kuza.kuzasokoni.domain.product.dtos.query;


import com.kuza.kuzasokoni.domain.product.enums.Currency;
import com.kuza.kuzasokoni.domain.product.enums.InterestMethod;
import com.kuza.kuzasokoni.domain.product.enums.ProductStatus;
import com.kuza.kuzasokoni.domain.product.enums.RepaymentFrequency;

import java.math.BigDecimal;
import java.util.List;

public interface ProductView {
    Long getId();
    String getProductName();
    String getShortName();
    InterestMethod getInterestMethod();
    RepaymentFrequency getRepaidEvery();
    Currency getCurrency();
    ProductStatus getStatus();
    BigDecimal getInterestMin();
    BigDecimal getInterestMax();
    Integer getOverdue_days();
    Integer getNpa_days();

    List<ChargeView> getCharges();
    List<TenureView> getTenures();

    interface ChargeView {
        Long getId();
        String getName();
        BigDecimal getAmount();
        String getRepaymentType();
        String getCollectedOn();
        String getDeductedOn();
    }

    interface TenureView {
        Long getId();
        Integer getNumberOfDays();
        BigDecimal getInterest();
        Integer getPhase1Days();
        Integer getPhase2Days();
        Integer getPhase3Days();
        Integer getPhase4Days();
        BigDecimal getPenaltyGroupOne();
        BigDecimal getPenaltyGroupTwo();
        BigDecimal getPenaltyGroupThree();
        BigDecimal getPenaltyGroupFour();
        BigDecimal getPenaltyCap();
        Integer getGracePeriodDays();
    }
}
