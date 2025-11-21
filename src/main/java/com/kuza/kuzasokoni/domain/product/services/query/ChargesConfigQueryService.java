package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.domain.product.dtos.query.ChargesConfigView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChargesConfigQueryService {
    Page<ChargesConfigView> getAllConfigs(Pageable pageable);
    Optional<ChargesConfigView> getConfigById(Long id);
}
