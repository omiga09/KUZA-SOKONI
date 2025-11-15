package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.domain.product.dtos.query.ChargesConfigView;

import java.util.List;
import java.util.Optional;

public interface ChargesConfigQueryService {
    List<ChargesConfigView> getAllConfigs();
    Optional<ChargesConfigView> getConfigById(Long id);
}
