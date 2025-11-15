package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.domain.product.dtos.query.ImageView;

import java.util.List;
import java.util.Optional;

public interface ImageQueryService {
    List<ImageView> getAll();
    Optional<ImageView> getById(Long id);
}
