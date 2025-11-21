package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.common.response.PageResponse;
import com.kuza.kuzasokoni.domain.product.dtos.query.ImageView;

import java.util.List;
import java.util.Optional;

public interface ImageQueryService {
    PageResponse<ImageView> getAll(int page, int size);
    Optional<ImageView> getById(Long id);
}
