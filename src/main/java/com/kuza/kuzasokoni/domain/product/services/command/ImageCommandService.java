package com.kuza.kuzasokoni.domain.product.services.command;

import com.kuza.kuzasokoni.domain.product.dtos.command.ImageCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.query.ImageView;

public interface ImageCommandService {
    ImageView create(ImageCreateCommand command);
}
