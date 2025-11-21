package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.common.response.PageResponse;
import com.kuza.kuzasokoni.domain.product.dtos.query.ChargeView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChargeQueryService {
    PageResponse<ChargeView> getAllCharges(int page, int size, String sortBy, String sortDir);
    Optional<ChargeView> getChargeById(Long id);
}
