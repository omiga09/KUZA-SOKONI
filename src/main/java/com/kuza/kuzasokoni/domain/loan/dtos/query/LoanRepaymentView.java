package com.kuza.kuzasokoni.domain.loan.dtos.query;

import java.math.BigDecimal;

public interface LoanRepaymentView {
    Long getId();
    BigDecimal getOutstanding();
    BigDecimal getPrincipal();
    BigDecimal getInterest();

}

