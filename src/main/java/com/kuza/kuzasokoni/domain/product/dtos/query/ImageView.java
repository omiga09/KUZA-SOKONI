package com.kuza.kuzasokoni.domain.product.dtos.query;

import com.kuza.kuzasokoni.common.utils.EntityType;

public interface ImageView {
    String name();
    String path();
    EntityType entityType();
    Long clientI();
}
