package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.domain.product.dtos.query.ChargeView;

import java.util.List;
import java.util.Optional;

public interface ChargeQueryService {
    List<ChargeView> getAllCharges();
    Optional<ChargeView> getChargeById(Long id);
}
