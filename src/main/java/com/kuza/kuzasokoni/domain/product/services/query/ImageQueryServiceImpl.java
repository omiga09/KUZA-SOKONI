package com.kuza.kuzasokoni.domain.product.services.query;

import com.kuza.kuzasokoni.common.audit.Images;
import com.kuza.kuzasokoni.common.repository.ImagesRepository;
import com.kuza.kuzasokoni.common.response.PageResponse;
import com.kuza.kuzasokoni.common.utils.EntityType;
import com.kuza.kuzasokoni.domain.product.dtos.query.ImageView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ImageQueryServiceImpl implements ImageQueryService {

    private final ImagesRepository repository;

    public PageResponse<ImageView> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Images> result = repository.findAll(pageable);

        // Map entity → projection
        Page<ImageView> mapped = result.map(image -> new ImageView() {
            @Override public String name() { return image.getName(); }
            @Override public String path() { return image.getPath(); }
            @Override public EntityType entityType() { return image.getEntityType(); }
            @Override public Long clientI() { return image.getClientId(); }
        });

        // Pagination calculation
        int totalPages = mapped.getTotalPages();
        int current = mapped.getNumber();
        int visible = 5;
        int start = Math.max(0, current - visible / 2);
        int end = Math.min(totalPages - 1, start + visible - 1);
        if (end - start + 1 < visible) start = Math.max(0, end - visible + 1);
        List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                .map(p -> p + 1)
                .boxed()
                .collect(Collectors.toList());

        return new PageResponse<>(
                mapped.getContent(),
                mapped.getNumber(),
                mapped.getSize(),
                mapped.getTotalElements(),
                totalPages,
                mapped.isFirst(),
                mapped.isLast(),
                mapped.hasNext(),
                mapped.hasPrevious(),
                pageNumbers
        );
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
