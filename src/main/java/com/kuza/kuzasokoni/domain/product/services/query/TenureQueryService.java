package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.common.response.PageResponse;
import com.kuza.kuzasokoni.domain.product.dtos.command.TenureCreateCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TenureQueryService {
    TenureCreateCommand getTenure(Long id);
    public PageResponse<TenureCreateCommand> getTenuresByProduct(Long productId, int page, int size);
}
