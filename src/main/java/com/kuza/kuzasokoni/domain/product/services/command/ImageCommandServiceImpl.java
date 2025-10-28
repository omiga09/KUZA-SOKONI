package com.kuza.kuzasokoni.domain.product.services.command;

import com.kuza.kuzasokoni.common.audit.Images;
import com.kuza.kuzasokoni.common.image.repository.ImagesRepository;
import com.kuza.kuzasokoni.common.utils.EntityType;
import com.kuza.kuzasokoni.domain.product.dtos.command.ImageCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.query.ImageView;
import com.kuza.kuzasokoni.domain.product.mappers.ImageCommandMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ImageCommandServiceImpl implements ImageCommandService{

    private final ImagesRepository repository;
    private final ImageCommandMapper mapper;

    @Override
    public ImageView create(ImageCreateCommand command) {
        Images image = mapper.toEntity(command);
        Images saved = repository.save(image);
        return new ImageView() {
            @Override public String name() { return saved.getName(); }
            @Override public String path() { return saved.getPath(); }
            @Override public EntityType entityType() { return saved.getEntityType(); }
            @Override public Long clientI() { return saved.getClientId(); }
        };
    }

}
