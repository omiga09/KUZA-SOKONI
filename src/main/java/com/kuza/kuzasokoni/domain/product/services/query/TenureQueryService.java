package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.domain.product.dtos.command.TenureCreateCommand;

import java.util.List;

public interface TenureQueryService {
    TenureCreateCommand getTenure(Long id);
    List<TenureCreateCommand> getTenuresByProduct(Long productId);
}
