package com.kuza.kuzasokoni.domain.product.services.query;


import com.kuza.kuzasokoni.common.response.PageResponse;
import com.kuza.kuzasokoni.common.utils.PageUtil;
import com.kuza.kuzasokoni.domain.product.dtos.command.TenureCreateCommand;
import com.kuza.kuzasokoni.domain.product.dtos.query.TenureView;
import com.kuza.kuzasokoni.domain.product.entities.Tenure;
import com.kuza.kuzasokoni.domain.product.mappers.TenureCommandMapper;
import com.kuza.kuzasokoni.domain.product.repositories.TenureRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
public class TenureQueryServiceImpl implements TenureQueryService{


        private final TenureRepository tenureRepository;
        private final TenureCommandMapper tenureCommandMapper;

        public TenureQueryServiceImpl(TenureRepository tenureRepository,
                                      TenureCommandMapper tenureCommandMapper) {
            this.tenureRepository = tenureRepository;
            this.tenureCommandMapper = tenureCommandMapper;
        }

        @Override
        public TenureCreateCommand getTenure(Long id) {
            Tenure tenure = tenureRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tenure not found"));
            return tenureCommandMapper.toDto(tenure);
        }

    @Override
    public PageResponse<TenureCreateCommand> getTenuresByProduct(Long productId, int page, int size) {
        // Create pageable
        Pageable pageable = PageRequest.of(page, size);

        // Fetch Tenure entities directly
        Page<Tenure> result = tenureRepository.findByProductId(productId, pageable);

        // Map Tenure → TenureCreateCommand using mapper
        Page<TenureCreateCommand> mapped = result.map(tenureCommandMapper::toDto);

        // Pagination calculation
        int totalPages = mapped.getTotalPages();
        int current = mapped.getNumber();
        int visible = 5;

        int start = Math.max(0, current - visible / 2);
        int end = Math.min(totalPages - 1, start + visible - 1);
        if (end - start + 1 < visible) {
            start = Math.max(0, end - visible + 1);
        }

        List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                .map(p -> p + 1)
                .boxed()
                .collect(Collectors.toList());

        // Build and return PageResponse
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




}


