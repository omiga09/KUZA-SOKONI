package com.kuza.kuzasokoni.domain.product.dtos.query;


import com.kuza.kuzasokoni.domain.product.enums.Currency;
import com.kuza.kuzasokoni.domain.product.enums.InterestMethod;
import com.kuza.kuzasokoni.domain.product.enums.ProductStatus;
import com.kuza.kuzasokoni.domain.product.enums.RepaymentFrequency;

import java.math.BigDecimal;

public interface ProductView {
    Long getId();
    String getProductName();
    BigDecimal getInterest();
    InterestMethod getInterestMethod();
    String getTenurePlan();
    RepaymentFrequency getRepaidEvery();
    Currency getCurrency();
    ProductStatus getStatus();
}
