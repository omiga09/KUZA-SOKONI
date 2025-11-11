package com.kuza.kuzasokoni.domain.product.dtos.query;

public interface RepaymentStrategyView {
    Long getId();
    String getName();
    Integer getPrincipal();
    Integer getInterest();
    Integer getPenalty();
    Integer getFees();
    Integer getCharges();
}
