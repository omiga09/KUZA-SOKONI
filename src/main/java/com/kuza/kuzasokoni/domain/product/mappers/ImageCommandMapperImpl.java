package com.kuza.kuzasokoni.domain.product.mappers;

import com.kuza.kuzasokoni.common.audit.Images;
import com.kuza.kuzasokoni.domain.product.dtos.command.ImageCreateCommand;
import org.springframework.stereotype.Component;


@Component
public class ImageCommandMapperImpl implements ImageCommandMapper{


    public Images toEntity(ImageCreateCommand cmd) {
        return Images.of(
                cmd.path(),
                cmd.name(),
                cmd.entityType(),
                cmd.clientI()
        );
    }
}
