package com.kuza.kuzasokoni.domain.product.dtos.query;

public interface RepaymentStrategyView {
    Long getId();
    String getName();
    Boolean getIncludePrincipal();
    Boolean getIncludeInterest();
    Boolean getIncludePenalty();
    Boolean getIncludeFees();
    Boolean getIncludeCharges();
}
