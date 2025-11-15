package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.common.image.repository.ImagesRepository;
import com.kuza.kuzasokoni.common.utils.EntityType;
import com.kuza.kuzasokoni.domain.product.dtos.query.ImageView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageQueryServiceImpl implements ImageQueryService {

    private final ImagesRepository repository;

    @Override
    public List<ImageView> getAll() {
        return repository.findAll().stream()
                .map(image -> new ImageView() {
                    @Override public String name() { return image.getName(); }
                    @Override public String path() { return image.getPath(); }
                    @Override public EntityType entityType() { return image.getEntityType(); }
                    @Override public Long clientI() { return image.getClientId(); }
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ImageView> getById(Long id) {
        return repository.findById(id)
                .map(image -> new ImageView() {
                    @Override public String name() { return image.getName(); }
                    @Override public String path() { return image.getPath(); }
                    @Override public EntityType entityType() { return image.getEntityType(); }
                    @Override public Long clientI() { return image.getClientId(); }
                });
    }
}
