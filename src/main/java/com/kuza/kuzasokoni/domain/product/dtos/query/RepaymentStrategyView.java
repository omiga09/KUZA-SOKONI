package com.kuza.kuzasokoni.domain.product.dtos.query;

public interface RepaymentStrategyView {
    Long getId();
    String getName();
    Boolean getPrincipal();
    Boolean getInterest();
    Boolean getPenalty();
    Boolean getFees();
    Boolean getCharges();
}
