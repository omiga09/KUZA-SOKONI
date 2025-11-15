package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.domain.product.dtos.query.RepaymentStrategyView;

import java.util.List;
import java.util.Optional;

public interface RepaymentStrategyQueryService {
    public List<RepaymentStrategyView> getAll();
    Optional<RepaymentStrategyView> getById(Long id);
}
