package com.kuza.kuzasokoni.domain.product.dtos.query;

import java.math.BigDecimal;

public interface TenureView {

        Long getId();
        BigDecimal getPenaltyGroupOne();
        BigDecimal getPenaltyGroupTwo();
        BigDecimal getPenaltyGroupThree();
        BigDecimal getPenaltyGroupFour();
        Integer getPhase1Days();
        Integer getPhase2Days();
        Integer getPhase3Days();
        Integer getPhase4Days();
        Integer getPenaltyCap();
        Integer getGracePeriodDays();
        BigDecimal getInterest();
        Integer getNumberOfDays();
        Long getProductId();
    }

