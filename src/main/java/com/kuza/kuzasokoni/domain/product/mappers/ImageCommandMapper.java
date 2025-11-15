package com.kuza.kuzasokoni.domain.product.mappers;

import com.kuza.kuzasokoni.common.audit.Images;
import com.kuza.kuzasokoni.domain.product.dtos.command.ImageCreateCommand;

public interface ImageCommandMapper {
    Images toEntity(ImageCreateCommand cmd);
}
